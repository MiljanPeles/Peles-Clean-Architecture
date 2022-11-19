package rs.peles.data.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.peles.data.BuildConfig
import rs.peles.data.api.UserApi
import rs.peles.data.model.mapper.UserDtoMapper
import rs.peles.data.repository.UserDataSource
import rs.peles.data.repository.UserRemoteDataSourceImpl
import rs.peles.data.util.PInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

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
    fun provideAthenaRetrofit(
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


    @Singleton
    @Provides
    fun provideUserDtoMapper(): UserDtoMapper {
        return UserDtoMapper()
    }

}