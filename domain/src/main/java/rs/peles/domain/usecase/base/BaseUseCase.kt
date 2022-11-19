package rs.peles.domain.usecase.base

abstract class BaseUseCase<Response, Request> {

    abstract suspend fun execute(
        request: Request
    ) : Response

}