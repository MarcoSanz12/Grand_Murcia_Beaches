package com.cotesa.appcore.platform

import com.cotesa.appcore.exception.Failure
import com.cotesa.appcore.functional.Either
import retrofit2.Call
import retrofit2.Callback

interface BaseRepository {
    /**
     * Performs a network request using the given Retrofit [Call] object and applies the provided
     * transformation function to the response data before returning it.
     *
     * If the request is successful,returns an [Either] object with the transformed response data wrapped in [Either.Right].
     *
     * If the request fails, returns an [Either] object with an error message wrapped in [Either.Left].
     *
     * @param call The Retrofit call object used to perform the network request.
     * @param transform The function used to transform the response data before returning it.
     * @return An [Either] object with either the transformed response data or an error message.
     *
     */
    fun <T> request(
        call: Call<T>,
        transform: (T) -> T
    ): Either<Failure, T> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform(response.body()!!))
                false -> {
                    val message = response.errorBody()?.string() ?: ""
                    val fail: Failure = Failure.ServerError
                    fail.message = message
                    Either.Left(fail)
                }
            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            Either.Left(Failure.ServerError)
        }
    }

    /**
     *
     * Performs a network request with Retrofit and handles errors using a custom function.
     *
     * @param call the network request represented by a Retrofit [Call] object.
     * @param transform a function that transforms the response object of the network request into another object.
     * @param errorFunction a function that is executed if the network request fails and takes an error message as a parameter.
     * This function returns an [Either] object representing the error.
     *
     * @return an [Either] object that contains the transformed result of the network request if the request is successful,
     * or an [Either] object representing the error if the request fails.
     */
    fun <T> requestWithError (
        call: Call<T>,
        transform: (T) -> T,
        errorFunction: (message: String) -> Either<Failure, T>
    ): Either<Failure, T> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform(response.body()!!))
                false ->{
                    val message = response.errorBody()?.string() ?: ""
                    val fail: Failure = Failure.LoginError
                    fail.message = message
                    Either.Left(fail)
                    errorFunction.invoke(message)
                }
            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            errorFunction.invoke(exception.message?:"")
        }
    }

    /**
     *
     * Performs a network request with Retrofit and transforms the response object into a new type.
     *
     * @param call the network request represented by a Retrofit [Call] object.
     * @param transform a function that takes the response object of the network request
     * as a parameter and transforms it into an object of a different type.
     * @return an [Either] object that contains the transformed result
     * of the network request if the request is successful,
     * or an [Either] object representing the error if the request fails.
     */
    fun <T, K> requestTransform(
        call: Call<T>,
        transform: (T) -> K
    ): Either<Failure, K> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform(response.body()!!))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            Either.Left(Failure.ServerError)
        }
    }

    /**
     * Performs a network request with Retrofit and returns if response.isSuccesful
     *
     * @param call the network request represented by a Retrofit [Call] object.
     * @return [Either] object that contains:
     * [true] if response is succesful
     *
     * [Failure.ServerError] if response isn't succesful
     */
    fun <T> requestBody(
        call: Call<T>
    ): Either<Failure, Boolean> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(true)
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            Either.Left(Failure.ServerError)
        }
    }

    /**
     * Executes a Retrofit network request asynchronously by implementing a Retrofit callback.
     *
     * @param call The Retrofit [Call] object representing the network request to be executed.
     * @param callback The Retrofit [Callback] object defining the methods to handle the network request response.
     */
    fun <T> requestEnqueue(
        call: Call<T>,
        callback: Callback<T>
    ) {
        return try {
            call.enqueue(callback)
        } catch (exception: Throwable) {
            exception.printStackTrace()
            callback.onFailure(call, exception)
        }
    }


}