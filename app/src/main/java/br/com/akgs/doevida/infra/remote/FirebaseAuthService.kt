package br.com.akgs.doevida.infra.remote

import br.com.akgs.doevida.infra.remote.entities.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface FirebaseAuthService {
    fun signUpWithEmailAndPassword(email: String, password: String, onComplete: (Boolean, String?) -> Unit)
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onComplete: (Boolean) -> Unit)
    fun createUser(user: User, onComplete: (Boolean, String?) -> Unit)
    fun currentUser(): User
    fun getUserId(): String
    fun signOut()
    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    )
}