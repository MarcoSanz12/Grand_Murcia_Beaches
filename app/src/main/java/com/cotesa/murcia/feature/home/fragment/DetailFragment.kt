package com.cotesa.murcia.feature.home.fragment

import android.animation.LayoutTransition
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.util.*
import com.cotesa.murcia.BeachApplication
import com.cotesa.murcia.R
import com.cotesa.murcia.databinding.FragmentDetailBinding
import com.cotesa.murcia.di.ApplicationComponent
import com.cotesa.murcia.feature.home.activity.HomeActivity
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import com.cotesa.murcia.navigator.Navigator
import javax.inject.Inject

class DetailFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override var level: Int = 2

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as BeachApplication).appComponent
    }

    private lateinit var homeViewModel: HomeViewModel

        private var _binding: FragmentDetailBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            _binding = FragmentDetailBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            appComponent.inject(this)
            /* (activity as BaseActivity).clearBackstack()*/
            homeViewModel = viewModel(viewModelFactory) {
                observe(selectedBeach,::handleLoaded)
            }

        }

    private fun handleLoaded(beach: Beach?) {
        binding.pbDetailLoading.visible()

        initializeView(beach!!.title as String)
        binding.ivDetailMainImage.loadFromUrl(beach!!.mainImage!!.first().url!!)

        binding.llDetailScrollView.apply {
            generateInfoCard("description",beach!!.description!!.fromHTML().toString(),this)
            generateInfoCard("access",beach!!.access!!.fromHTML().toString(),this)
            generateInfoCard("accessibility",beach!!.accessibility!!.fromHTML().toString(),this )
        }

        binding.pbDetailLoading.invisible()
    }

    /**
     * Creates a View using card_info as base layout
     *
     * @param title Title of the view
     * @param content Description of the view
     * @param root LinearLayout to attach the card info
     *
     */
    private fun generateInfoCard(title:String, content:String, root:LinearLayout){
       layoutInflater.inflate(R.layout.card_info,root).apply {
            findViewById<TextView>(R.id.tv_cardTitle).apply{
                generateId()
                text = title
            }
            findViewById<TextView>(com.cotesa.murcia.R.id.tv_cardDescription).apply {
                text = content
            }

           expandCard(this)
        }
    }

    /**
     * Adds hide / show functionality for the info cards
     *
     * @param view [View] info card
     */
    private fun expandCard (view:View){
        view.apply {
            val card = findViewById<CardView>(R.id.cv_cardCardView).apply { generateId() }
            val symbol = findViewById<ImageView>(R.id.iv_cardExpandIcon).apply { generateId() }
            val content = findViewById<TextView>(R.id.tv_cardDescription).apply { generateId() }
            val layout = findViewById<ConstraintLayout>(R.id.cl_cardConsLay).apply { generateId() }

            if (content.isVisible)
                symbol.setImageResource(R.drawable.expand_less)
            else
                symbol.setImageResource(R.drawable.expand_more)

            TransitionManager.beginDelayedTransition(layout, AutoTransition())
            layout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

            card.setOnClickListener{
                //Ocultar
                if (content.isVisible) {
                    symbol.setImageResource(R.drawable.expand_more)
                    content.invisible()
                }
                //Expandir
                else{
                    symbol.setImageResource(R.drawable.expand_less)
                    content.visible()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }


        private fun initializeView(title:String) {
            with (activity as HomeActivity){
                configureActionBar(DetailActionBar(title,::favoriteFunction))
            }
        }

    private fun favoriteFunction() {
        TODO("Not yet implemented")
    }


    fun goToUrl(url: String) {
            val uriUrl = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            try {
                requireContext().startActivity(launchBrowser)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }


