package org.pandatech.bookpedia.core.data

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.pandatech.bookpedia.core.domain.DataErrorToStringResource
import org.pandatech.bookpedia.core.domain.Result
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataErrorToStringResource.Remote> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return Result.Error(DataErrorToStringResource.Remote.REQUEST_TIMEOUT)
    } catch(e: UnresolvedAddressException) {
        return Result.Error(DataErrorToStringResource.Remote.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataErrorToStringResource.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataErrorToStringResource.Remote> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(DataErrorToStringResource.Remote.SERIALIZATION)
            }
        }
        408 -> Result.Error(DataErrorToStringResource.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataErrorToStringResource.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataErrorToStringResource.Remote.SERVER)
        else -> Result.Error(DataErrorToStringResource.Remote.UNKNOWN)
    }
}