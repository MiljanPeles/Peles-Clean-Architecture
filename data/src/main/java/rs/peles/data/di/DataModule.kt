package rs.peles.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.peles.data.api.UserApi
import rs.peles.data.mapper.UserDtoMapper
import rs.peles.data.repository.UserDataSource
import rs.peles.data.repository.UserRemoteDataSourceImpl
import rs.peles.data.repository.UserRepositoryImpl
import rs.peles.domain.repository.UserRepository
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