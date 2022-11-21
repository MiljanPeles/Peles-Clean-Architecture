package rs.peles.data.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import rs.peles.data.exception.NoConnectivityException

class PInterceptor(private val context: Context): Interceptor {

    /**
     * This is customer interceptor if we have to provide customer token through header
     */
    override fun intercept(chain: Interceptor.Chain): Response {

        // Checking internet connectivity and throwing NoConnectivityException if it is not available
        if(!Connectivity.isConnected(context)) {
            throw NoConnectivityException()
        }

        var request = chain.request()

        request =
            request.newBuilder()
                .addHeader("Accept", "Application/JSON")
                .addHeader(
                    "Authorization",
                    "Bearer XXX"
                )
                .build()

        return chain.proceed(request)
    }
}