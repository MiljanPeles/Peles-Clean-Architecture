package rs.peles.data.repository

import rs.peles.data.api.UserApi
import rs.peles.data.mapper.UserDtoMapper
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val mapper: UserDtoMapper
): UserDataSource.Remote {

    override suspend fun getUsers(): List<User> {
        val result = userApi.getUsers().map {
            mapper.mapToDomainModel(it)
        }

        return result
    }

    override suspend fun getSpecificUser(userRequest: GetUserRequest): User {
        return mapper.mapToDomainModel(userApi.getSpecificUser(userRequest.userId))
    }


}