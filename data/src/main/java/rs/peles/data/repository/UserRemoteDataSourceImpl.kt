package rs.peles.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rs.peles.data.api.UserApi
import rs.peles.data.model.mapper.UserDtoMapper
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.util.PResult
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
    private val mapper: UserDtoMapper
): UserDataSource.Remote {

    override suspend fun getUsers(): PResult<List<User>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = userApi.getUsers()
            PResult.Success(result.map {
                mapper.mapToDomainModel(it)
            })
        } catch (e: Exception) {
            PResult.Error(e)
        }
    }

    override suspend fun getSpecificUser(userRequest: GetUserRequest): PResult<User> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = mapper.mapToDomainModel(userApi.getSpecificUser(userRequest.userId))
            PResult.Success(result)
        } catch (e: Exception) {
            PResult.Error(e)
        }
    }


}