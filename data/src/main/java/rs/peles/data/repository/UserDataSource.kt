package rs.peles.data.repository

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.util.PResult

interface UserDataSource {

    interface Remote {

        suspend fun getUsers(): PResult<List<User>>

        suspend fun getSpecificUser(userRequest: GetUserRequest): PResult<User>

    }

    interface Local {
        // Local business
    }

    interface Cache : Local

}