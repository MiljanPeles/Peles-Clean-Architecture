package rs.peles.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.peles.data.db.UserDatabase
import rs.peles.data.db.dao.UserDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "user_db"

    @Provides
    fun provideUserRoomDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun providePrayDao(myRoomDatabase: UserDatabase): UserDao {
        return myRoomDatabase.userDao()
    }

}