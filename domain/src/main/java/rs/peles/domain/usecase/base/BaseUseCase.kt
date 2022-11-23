package rs.peles.domain.usecase.base

import rs.peles.domain.util.PResource
import kotlinx.coroutines.flow.Flow

/**
 * BaseUseCase class for all use case instances that have both response and request.
 */
abstract class BaseUseCase<Response, Request> {

    abstract suspend operator fun invoke(
        request: Request
    ) : Flow<PResource<Response>>

}

/**
 * BaseUseCaseWithOnlyInput class for all use case instances that have only request.
 */
abstract class BaseUseCaseWithOnlyInput<in Request> {

    abstract suspend operator fun invoke(
        request: Request
    )

}

/**
 * BaseUseCaseWithOnlyOutput class for all use case instances that have only response.
 */
abstract class BaseUseCaseWithOnlyOutput<Response> {

    abstract suspend operator fun invoke() : Flow<PResource<Response>>

}