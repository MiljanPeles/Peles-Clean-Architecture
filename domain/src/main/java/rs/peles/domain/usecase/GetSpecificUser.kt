package rs.peles.domain.usecase

import rs.peles.domain.model.User
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase
import rs.peles.domain.util.PResult

class GetSpecificUser(
    private val repository: UserRepository
): BaseUseCase<PResult<List<User>>, Void>() {

    override suspend fun execute(request: Void): PResult<List<User>> = repository.getUsers()


}