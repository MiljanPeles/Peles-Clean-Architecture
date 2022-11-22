package rs.peles.domain.usecase.base

import rs.peles.domain.util.PResource
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<Response, Request> {

    abstract suspend operator fun invoke(
        request: Request
    ) : Flow<PResource<Response>>

}

abstract class BaseUseCaseWithOnlyInput<in Input> {

    abstract suspend operator fun invoke(
        request: Input
    )

}

abstract class BaseUseCaseWithOnlyOutput<Output> {

    abstract suspend operator fun invoke() : Flow<PResource<Output>>

}