package rs.peles.data.repository

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.util.PResult

class UserRepositoryImpl(
    private val remote: UserDataSource.Remote
): UserRepository {

    override suspend fun getUsers(): PResult<List<User>> = remote.getUsers()

    override suspend fun getSpecificUser(userRequest: GetUserRequest): PResult<User> = remote.getSpecificUser(userRequest)

}