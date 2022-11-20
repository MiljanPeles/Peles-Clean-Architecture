package rs.peles.domain.repository

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest

interface UserRepository {

    suspend fun getUsers(): List<User>

    suspend fun getSpecificUser(userRequest: GetUserRequest): User

}