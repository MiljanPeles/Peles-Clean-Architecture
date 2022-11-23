package rs.peles.data.mapper

import rs.peles.data.db.model.UserEntity
import rs.peles.data.model.UserDto
import rs.peles.data.mapper.base.PBaseMapper
import rs.peles.domain.model.User

class UserEntityMapper: PBaseMapper<UserEntity, User> {

    override fun mapToDomainModel(model: UserEntity): User {
        return User(
            name = model.name,
            lastname = model.lastname,
            age = model.age
        )
    }

}