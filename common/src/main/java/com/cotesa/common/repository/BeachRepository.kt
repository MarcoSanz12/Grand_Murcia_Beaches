package com.cotesa.common.repository

import com.cotesa.appcore.exception.Failure
import com.cotesa.appcore.functional.Either
import com.cotesa.appcore.platform.BaseRepository
import com.cotesa.appcore.platform.NetworkHandler
import com.cotesa.common.BuildConfig
import com.cotesa.common.entity.aemet.AemetBase
import com.cotesa.common.entity.aemet.AemetInfo
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.services.BeachService
import javax.inject.Inject
import javax.inject.Singleton

interface BeachRepository : BaseRepository {

    suspend fun callAllBeaches():Either<Failure,List<Beach>>
    suspend fun callAemetInfo(id:Int):Either<Failure,List<AemetInfo>>

    suspend fun callBeach(id:Int):Either<Failure,Beach>

    suspend fun saveBeaches(beachList:List<Beach>)

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val beachService: BeachService
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
                        it
                        // TODO: Implement saving in DB
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

            }
            else {
                Either.Left(Failure.NetworkConnection)
            }

        }


        override suspend fun callBeach(id: Int): Either<Failure, Beach> {
            TODO("Not yet implemented, waiting for ROOM")
        }

        override suspend fun saveBeaches(beachList: List<Beach>) {
            TODO("Not yet implemented, waiting for ROOM")
        }
    }


}