package rs.peles.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("website") val website: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("image_url") val imageUrl: String?
)
