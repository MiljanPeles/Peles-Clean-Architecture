package rs.peles.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("age") val age: Int?,
    @SerializedName("category") val category: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("image_url") val imageUrl: String?
)
