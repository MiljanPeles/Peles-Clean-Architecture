package rs.peles.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey
    val id: Long? = null,
    val name: String,
    val lastname: String,
    val age: Int
)
