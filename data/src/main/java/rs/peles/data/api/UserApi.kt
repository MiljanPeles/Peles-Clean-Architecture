package rs.peles.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import rs.peles.data.model.UserDto

interface UserApi {

    @GET("/users")
    suspend fun getUsers(): List<UserDto>

    @GET("/user")
    suspend fun getSpecificUser(
        @Query("id") id: String
    ): UserDto

}