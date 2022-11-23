package rs.peles.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.peles.data.db.dao.UserDao
import rs.peles.data.db.model.UserEntity

@Database(
    version = 1,
    entities = [UserEntity::class],
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}