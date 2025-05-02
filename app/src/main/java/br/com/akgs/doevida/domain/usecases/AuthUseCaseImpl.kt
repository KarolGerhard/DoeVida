package br.com.akgs.doevida.domain.usecases

import br.com.akgs.doevida.infra.remote.FirebaseAuthService
import br.com.akgs.doevida.infra.remote.entities.User

class AuthUseCaseImpl(private val authService: FirebaseAuthService): AuthUseCase {


    override suspend fun register(user: User, onComplete: (Boolean, String?) -> Unit) {
        authService.createUser(user, onComplete)
    }


}