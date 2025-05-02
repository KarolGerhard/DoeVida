package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.User

interface UserUseCase {
    fun createUser(user: User)
    fun updateUser(user: User)
}