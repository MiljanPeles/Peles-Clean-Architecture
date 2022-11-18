package rs.peles.domain.usecase

import rs.peles.domain.model.User
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase

class GetSpecificUser(
    private val repository: UserRepository
): BaseUseCase<List<User>, Void>() {

    override suspend fun execute(request: Void): List<User> = repository.getUsers()


}