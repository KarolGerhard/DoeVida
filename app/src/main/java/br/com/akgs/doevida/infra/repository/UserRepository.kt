package br.com.akgs.doevida.infra.repository

import br.com.akgs.doevida.infra.remote.entities.User


interface UserRepository {
//    fun getUserById(id: String): User?
    fun addUser(user: User)
    fun updateUser(user: User)
}