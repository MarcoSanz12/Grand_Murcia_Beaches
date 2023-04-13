package com.cotesa.murcia.feature.home.fragment

import android.animation.LayoutTransition
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cotesa.appcore.extension.*
import com.cotesa.appcore.platform.BaseFragment
import com.cotesa.common.adapter.ImageGalleryAdapter
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.entity.beach.Image
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
            generateInfoCard("description",beach.description!!.fromHTML().toString(),this)
            generateInfoCard("access",beach.access!!.fromHTML().toString(),this)
            generateInfoCard("accessibility",beach.accessibility!!.fromHTML().toString(),this )
            generateGalleryCard("gallery",beach.imageGallery!!,this)
        }

        binding.flDetailImageGalleryBackground.setOnClickListener {
            it.invisible()
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
            assignCardTitle(title)

           findViewById<TextView>(R.id.view_cardContent).apply{
               text = content
           }

           makeCardExpandable(this)
           }
        }

    private fun View.assignCardTitle(title: String) {
        findViewById<TextView>(R.id.tv_cardTitle).apply {
            Log.d("Type",this::class.java.name)
            generateId()
            text = title
        }
    }

    private fun generateGalleryCard(title:String,content:List<Image>, root:LinearLayout){
        layoutInflater.inflate(R.layout.card_gallery,root).apply {
            assignCardTitle(title)

            findViewById<RecyclerView>(R.id.view_cardContent).apply {
                adapter = ImageGalleryAdapter(requireContext(),content,OnImageOnGalleryClickListener())
            }

            makeCardExpandable(this)
        }
    }

    inner class OnImageOnGalleryClickListener():ImageGalleryAdapter.RecyclerViewOnItemClickListener<Image>{
        override fun onItemClick(v: View?, model: Image) {
            displayFullScreenImage(model)
        }

    }

    fun displayFullScreenImage(image:Image){
        binding.ivDetailGalleryImage.loadFromUrl(image.url!!)
        binding.flDetailImageGalleryBackground.visible()
    }
    /*
     * Adds hide / show functionality for the info cards
     *
     * @param view [View] info card
     */
    private fun makeCardExpandable (view:View){
        view.apply {
            val card = findViewById<CardView>(R.id.cv_cardCardView).apply { generateId() }
            val symbol = findViewById<ImageView>(R.id.iv_cardExpandIcon).apply { generateId() }
            val layout = findViewById<ConstraintLayout>(R.id.cl_cardConsLay).apply { generateId() }
            var content = findViewById<View>(R.id.view_cardContent).apply { generateId() }

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



