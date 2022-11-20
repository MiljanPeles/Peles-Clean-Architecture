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
import rs.peles.data.repository.UserRepositoryImpl
import rs.peles.data.util.PInterceptor
import rs.peles.domain.repository.UserRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideUserDtoMapper(): UserDtoMapper {
        return UserDtoMapper()
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(api: UserApi, mapper: UserDtoMapper): UserDataSource.Remote =
        UserRemoteDataSourceImpl(api, mapper)

    @Provides
    @Singleton
    fun provideUserRepository(remote: UserDataSource.Remote): UserRepository =
        UserRepositoryImpl(remote)

}