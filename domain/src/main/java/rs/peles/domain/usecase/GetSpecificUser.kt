package rs.peles.domain.usecase

import rs.peles.domain.model.User
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.util.PResource

class GetSpecificUser(
    private val repository: UserRepository
): BaseUseCase<Flow<PResource<User>>, GetUserRequest>() {

    override suspend fun invoke(request: GetUserRequest): Flow<PResource<User>> = flow {

        try {
            emit(PResource.Loading())

            val response = repository.getSpecificUser(request)

            emit(PResource.Success(response))

        } catch (e: Exception) {
            emit(PResource.Error(e.message ?: ""))
        }

    }


}