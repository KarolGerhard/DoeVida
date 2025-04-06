package br.com.akgs.doevida.infra.remote

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface FirebaseAuthService {
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onComplete: (Boolean) -> Unit)
    fun firebaseAuthWithFacebook(token: AccessToken, onComplete: (Boolean) -> Unit)
}