package rs.peles.data.source

import rs.peles.data.db.dao.UserDao
import rs.peles.data.mapper.UserEntityMapper
import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetLocalUserRequest
import javax.inject.Inject

/**
 * Local data source implementation class
 */
class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val mapper: UserEntityMapper
): UserDataSource.Local {

    override suspend fun getSpecificUser(userRequest: GetLocalUserRequest): User {
        return mapper.mapToDomainModel(userDao.getUser(userRequest.name))
    }

}