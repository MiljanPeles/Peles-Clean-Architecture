package rs.peles.data.source

import kotlinx.coroutines.delay
import rs.peles.data.api.UserApi
import rs.peles.data.mapper.UserDtoMapper
import rs.peles.data.model.UserDto
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.model.request.RegisterUserRequest
import javax.inject.Inject

/**
 * Remote data source implementation class
 */
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

    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): User {
        // In real scenario we would do API call (like in example above) but here we will delay 3 seconds and return the same user
        val fakeDtoModel = UserDto(1, registerUserRequest.name, registerUserRequest.website, registerUserRequest.email, "")
        // delay 3 seconds
        delay(3000)
        return mapper.mapToDomainModel(fakeDtoModel)
    }


}