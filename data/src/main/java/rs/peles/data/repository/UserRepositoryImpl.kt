package rs.peles.data.repository

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.util.PResult

class UserRepositoryImpl(
    private val remote: UserDataSource.Remote
): UserRepository {

    override suspend fun getUsers(): List<User> = remote.getUsers()

    override suspend fun getSpecificUser(userRequest: GetUserRequest): User = remote.getSpecificUser(userRequest)

}