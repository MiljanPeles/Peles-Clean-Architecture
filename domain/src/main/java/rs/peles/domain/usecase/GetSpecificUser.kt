package rs.peles.domain.usecase

import kotlinx.coroutines.Dispatchers
import rs.peles.domain.model.User
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.util.PResource

/**
 * Get Specific user use case which emits [PResource] events
 */
class GetSpecificUser(
    private val repository: UserRepository
): BaseUseCase<User, GetUserRequest>() {

    override suspend fun invoke(request: GetUserRequest): Flow<PResource<User>> = flow {

        try {
            emit(PResource.Loading())

            val response = repository.getSpecificUser(request)

            emit(PResource.Success(response))

        } catch (e: Exception) {
            emit(PResource.Error(e))
        }

    }.flowOn(Dispatchers.IO)


}