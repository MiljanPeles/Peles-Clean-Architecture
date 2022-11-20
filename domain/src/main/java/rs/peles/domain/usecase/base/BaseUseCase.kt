package rs.peles.domain.usecase.base

abstract class BaseUseCase<Response, Request> {

    abstract suspend operator fun invoke(
        request: Request
    ) : Response

}