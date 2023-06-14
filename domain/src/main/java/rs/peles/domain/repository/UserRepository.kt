package rs.peles.domain.repository

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetLocalUserRequest
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.model.request.RegisterUserRequest

interface UserRepository {

    suspend fun getUsers(): List<User>

    suspend fun getSpecificUser(userRequest: GetUserRequest): User

    suspend fun registerUser(registerUserRequest: RegisterUserRequest): User

    suspend fun getLocalUser(userRequest: GetLocalUserRequest): User

}