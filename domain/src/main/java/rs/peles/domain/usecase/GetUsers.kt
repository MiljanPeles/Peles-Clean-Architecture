package rs.peles.domain.usecase

import rs.peles.domain.model.User
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase

class GetUsers(
    private val repository: UserRepository
): BaseUseCase<User, GetUserRequest>() {

    override suspend fun execute(request: GetUserRequest): User {
        return repository.getSpecificUser(request)
    }
}