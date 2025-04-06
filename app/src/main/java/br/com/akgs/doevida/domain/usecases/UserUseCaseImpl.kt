package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.User
import br.com.akgs.doevida.infra.repository.UserRepository

class UserUseCaseImpl(
    private val userRepository: UserRepository
) : UserUseCase {

    override fun getUser(userId: String): User? {
        return userRepository.getUserById(userId)
    }

    override fun createUser(user: User) {
        userRepository.addUser(user)
    }

    override fun updateUser(user: User) {
        userRepository.updateUser(user)
    }
}