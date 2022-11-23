package rs.peles.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rs.peles.data.db.model.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: List<UserEntity>)

    @Query("SELECT * FROM user WHERE name = :name AND lastname = :lastname AND age = :age")
    fun getUser(name: String, lastname: String, age: Int): UserEntity

}