package rs.peles.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.peles.data.BuildConfig
import rs.peles.data.api.UserApi
import rs.peles.data.util.PInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BaseUrl")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @Named("AccessToken")
    fun provideAccessToken(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @Named("LoggingInterceptor")
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext appContext: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(appContext).collector(ChuckerCollector(appContext))
            .maxContentLength(250_000L).redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true).build()

    @Provides
    @Singleton
    fun provideInterceptor(@ApplicationContext appContext: Context): PInterceptor = PInterceptor(appContext)

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideOkHttpAthenaClient(
        @Named("LoggingInterceptor") httpLoggingInterceptor: HttpLoggingInterceptor,
        interceptor: PInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(
        @Named("OkHttpClient") okHttpClient: OkHttpClient,
        @Named("BaseUrl") baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApi(
        @Named("Retrofit") retrofit: Retrofit
    ): UserApi = retrofit.create(UserApi::class.java)

}