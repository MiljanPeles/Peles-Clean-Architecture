package rs.peles.domain.usecase

import kotlinx.coroutines.Dispatchers
import rs.peles.domain.model.User
import rs.peles.domain.repository.UserRepository
import rs.peles.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rs.peles.domain.model.request.GetUserRequest
import rs.peles.domain.model.request.RegisterUserRequest
import rs.peles.domain.util.PResource
import javax.inject.Inject

/**
 * Register user use case which emits [PResource] events
 */
class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
): BaseUseCase<User, RegisterUserRequest>() {

    override suspend fun invoke(request: RegisterUserRequest): Flow<PResource<User>> = flow {

        try {
            emit(PResource.Loading())

            val response = repository.registerUser(request)

            emit(PResource.Success(response))

        } catch (e: Exception) {
            emit(PResource.Error(e))
        }

    }.flowOn(Dispatchers.IO)


}