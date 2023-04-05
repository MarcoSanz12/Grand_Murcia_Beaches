package com.cotesa.murcia.feature.home.usecase

import android.content.Context
import com.cotesa.appcore.exception.Failure
import com.cotesa.appcore.functional.Either
import com.cotesa.appcore.interactor.UseCase
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.repository.BeachRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBeachesDB
@Inject constructor(private val repository: BeachRepository.Network) : UseCase<List<Beach>, GetBeachesDB.Params>() {

    data class Params(var context: Context)

    override suspend fun run(params: Params): Either<Failure, List<Beach>> {
        return repository.callAllBeachesDB()
    }


}