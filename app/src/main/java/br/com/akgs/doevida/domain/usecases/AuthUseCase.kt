package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.entities.User

interface AuthUseCase {
    suspend fun register(user: User, onComplete: (Boolean, String?) -> Unit)
}