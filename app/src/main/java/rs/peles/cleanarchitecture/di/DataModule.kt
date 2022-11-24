package rs.peles.cleanarchitecture.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.peles.data.api.UserApi
import rs.peles.data.db.dao.UserDao
import rs.peles.data.mapper.UserDtoMapper
import rs.peles.data.mapper.UserEntityMapper
import rs.peles.data.source.UserDataSource
import rs.peles.data.source.UserLocalDataSourceImpl
import rs.peles.data.source.UserRemoteDataSourceImpl
import rs.peles.data.repository.UserRepositoryImpl
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.GetSpecificUser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(api: UserApi, mapper: UserDtoMapper): UserDataSource.Remote =
        UserRemoteDataSourceImpl(api, mapper)

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userDao: UserDao, mapper: UserEntityMapper): UserDataSource.Local =
        UserLocalDataSourceImpl(userDao, mapper)

    @Provides
    @Singleton
    fun provideUserRepository(remote: UserDataSource.Remote, local: UserDataSource.Local): UserRepository =
        UserRepositoryImpl(remote, local)

}