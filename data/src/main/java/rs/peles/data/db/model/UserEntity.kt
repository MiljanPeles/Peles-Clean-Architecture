package rs.peles.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey
    val id: Long? = null,
    val name: String? = null,
    val website: String? = null,
    val email: String,
    val password: String,
    val photoUrl: String? = null
)
