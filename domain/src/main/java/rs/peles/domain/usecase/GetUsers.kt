package rs.peles.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rs.peles.domain.model.User
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase
import rs.peles.domain.usecase.base.BaseUseCaseWithOnlyOutput
import rs.peles.domain.util.PResource

class GetUsers(
    private val repository: UserRepository
): BaseUseCaseWithOnlyOutput<List<User>>() {

    override suspend fun invoke(): Flow<PResource<List<User>>> = flow {

        try {
            emit(PResource.Loading())

            val response = repository.getUsers()

            emit(PResource.Success(response))

        } catch (e: Exception) {
            emit(PResource.Error(e))
        }

    }.flowOn(Dispatchers.IO)
}