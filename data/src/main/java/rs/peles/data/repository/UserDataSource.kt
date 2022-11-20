package rs.peles.data.repository

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.util.PResult

interface UserDataSource {

    interface Remote {

        suspend fun getUsers(): List<User>

        suspend fun getSpecificUser(userRequest: GetUserRequest): User

    }

    interface Local {
        // Local business
    }

    interface Cache : Local

}