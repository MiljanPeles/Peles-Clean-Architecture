package rs.peles.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rs.peles.data.mapper.UserDtoMapper
import rs.peles.data.mapper.UserEntityMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideUserDtoMapper(): UserDtoMapper {
        return UserDtoMapper()
    }

    @Singleton
    @Provides
    fun provideUserEntityMapper(): UserEntityMapper {
        return UserEntityMapper()
    }

}