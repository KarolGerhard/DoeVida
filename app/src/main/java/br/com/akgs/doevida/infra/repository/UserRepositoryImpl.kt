package br.com.akgs.doevida.infra.repository

import br.com.akgs.doevida.infra.remote.FirebaseDatabaseService
import br.com.akgs.doevida.infra.remote.entities.User

class UserRepositoryImpl(private val firebaseDatabaseService: FirebaseDatabaseService): UserRepository {

   override fun getUserById(id: String): User? {
        return firebaseDatabaseService.getUserById(id)
    }

    override fun addUser(user: User) {
        firebaseDatabaseService.addUser(user)
    }

    override fun updateUser(user: User) {
        firebaseDatabaseService.updateUser(user)
    }
}