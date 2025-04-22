package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.User

interface AuthUseCase {
    fun login(email: String, password: String)
//    fun logout()
//    fun currentUser(): User
    suspend fun register(user: User, onComplete: (Boolean, String?) -> Unit)
}