package com.cotesa.common.repository

import com.cotesa.appcore.exception.Failure
import com.cotesa.appcore.functional.Either
import com.cotesa.appcore.platform.BaseRepository
import com.cotesa.appcore.platform.NetworkHandler
import com.cotesa.common.BuildConfig
import com.cotesa.common.db.BeachDatabase
import com.cotesa.common.entity.aemet.AemetBase
import com.cotesa.common.entity.aemet.AemetInfo
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.services.BeachService
import javax.inject.Inject

interface BeachRepository : BaseRepository {

    suspend fun callAllBeaches():Either<Failure,List<Beach>>
    fun callAllBeachesDB():Either<Failure,List<Beach>>
    suspend fun callAemetInfo(id:Int):Either<Failure,List<AemetInfo>>

    fun callBeachByIdBD(id:Int):Either<Failure,Beach>

    fun saveBeachesBD(beachList:List<Beach>)

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val beachService: BeachService,
        private val beachDatabase: BeachDatabase
    ) : BeachRepository{

        /**
         * Calls all beaches, IF internet use retrofit ELSE use roomDB
         *
         * Returns [Failure.DBEmptyError] if database is empty
         *
         * Returns [Failure.ServerError] if server connection fails
         *
         * @return [List] of beaches if all right
         */
       override suspend fun callAllBeaches(): Either<Failure, List<Beach>> {
           return when(networkHandler.isConnected){
               true ->
                    request(beachService.getAllBeaches()){
                        saveBeachesBD(it)
                        it
                    }
               false, null ->
                   Either.Left(Failure.DBEmptyError)
           }
        }

        /**
         * Gets AemetInfo, if there's internet connection calls to get [AemetBase] from [beachService].
         *
         * If its succesful, tries to getAemetInfo from [beachService]
         *
         * @return [Either.Right] With [List] of [AemetInfo] if all correct
         *  [Either.Left] with [Failure] is any error occurs.
         */
        override suspend fun callAemetInfo(id: Int): Either<Failure, List<AemetInfo>> {
            return if (networkHandler.isConnected) {
                val aemetBaseRequest = request(beachService.getAemetBase(id, BuildConfig.AEMET_BASE_URL)) {it }

                aemetBaseRequest.fold(
                    {
                        Either.Left(it)
                },
                    {
                        request(beachService.getAemetInfo(it.data!!,BuildConfig.AEMET_INFO_URL)){it}
                    }
                ) as Either<Failure, List<AemetInfo>>

            } else
                Either.Left(Failure.NetworkConnection)


        }

        override fun callAllBeachesDB() : Either<Failure,List<Beach>>{

            return try{
                val result = beachDatabase.beachDao().selectBeaches()
                if (result.isNullOrEmpty())
                    Either.Left(Failure.DBEmptyError)
                else
                    Either.Right(result)

            }catch(e : Exception){
                Either.Left(Failure.DBEmptyError)
            }

        }
        override fun callBeachByIdBD(id: Int): Either<Failure, Beach> {
            return try{
                val result = beachDatabase.beachDao().selectBeachById(id)
                if (result == null)
                    Either.Left(Failure.DBEmptyError)
                else
                    Either.Right(result)

            }catch(e : Exception){
                Either.Left(Failure.DBEmptyError)
            }
        }

        override fun saveBeachesBD(beachList: List<Beach>) {
            val dao = beachDatabase.beachDao()

            for (beach in beachList)
               dao.insertBeach(beach)

        }

    }


}