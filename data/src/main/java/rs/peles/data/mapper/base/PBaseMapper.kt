package rs.peles.data.mapper.base

/**
 * For mapping entity model to domain model & optionally we can add one more function for mapping domain model to entity model
 */
interface PBaseMapper<T, DomainModel> {
    fun mapToDomainModel(model: T): DomainModel
}