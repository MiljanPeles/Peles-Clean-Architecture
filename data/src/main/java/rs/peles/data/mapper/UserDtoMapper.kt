package rs.peles.data.mapper

import rs.peles.data.model.UserDto
import rs.peles.data.mapper.base.PBaseMapper
import rs.peles.domain.model.User

class UserDtoMapper: PBaseMapper<UserDto, User> {

    override fun mapToDomainModel(model: UserDto): User {
        return User(
            name = model.name ?: "",
            lastname = model.lastname ?: "",
            age = model.age ?: -1
        )
    }

}