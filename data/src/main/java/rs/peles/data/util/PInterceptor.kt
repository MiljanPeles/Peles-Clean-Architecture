package rs.peles.data.util

import okhttp3.Interceptor
import okhttp3.Response

class PInterceptor: Interceptor {

    /**
     * This is customer interceptor if we have to provide customer token through header
     */
    override fun intercept(chain: Interceptor.Chain): Response {
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