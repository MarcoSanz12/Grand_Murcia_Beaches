package com.cotesa.murcia.feature.home.usecase

import com.cotesa.appcore.exception.Failure
import com.cotesa.appcore.functional.Either
import com.cotesa.appcore.interactor.UseCase
import com.cotesa.common.entity.aemet.AemetInfo
import com.cotesa.common.repository.BeachRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAemetInfo
@Inject constructor(private val repository: BeachRepository.Network) : UseCase<List<AemetInfo>, GetAemetInfo.Params>() {

    data class Params(var idAemet:Int)

    override suspend fun run(params:Params): Either<Failure, List<AemetInfo>> = repository.callAemetInfo(params.idAemet)
}





