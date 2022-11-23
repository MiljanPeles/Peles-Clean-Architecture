package rs.peles.data.repository

import rs.peles.data.source.UserDataSource
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetLocalUserRequest
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.repository.UserRepository

class UserRepositoryImpl(
    private val remote: UserDataSource.Remote,
    private val local: UserDataSource.Local
): UserRepository {

    override suspend fun getUsers(): List<User> = remote.getUsers()

    override suspend fun getSpecificUser(userRequest: GetUserRequest): User = remote.getSpecificUser(userRequest)

    override suspend fun getLocalUser(userRequest: GetLocalUserRequest): User = local.getSpecificUser(userRequest)

}