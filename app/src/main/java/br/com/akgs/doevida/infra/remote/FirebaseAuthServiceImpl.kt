package br.com.akgs.doevida.infra.remote

import android.content.ContentValues.TAG
import android.provider.SyncStateContract
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.facebook.AccessToken
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Response
import com.google.android.gms.common.internal.Constants
import kotlinx.coroutines.tasks.await
import javax.inject.Named


class FirebaseAuthServiceImpl(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var googleSignInClient: GoogleSignInClient,
    @Named(Constants.SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SyncStateContract.Constants.SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
): FirebaseAuthService {
    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onComplete: (Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    override suspend fun verifyGoogleSignIn(): Boolean {
        auth.currentUser?.let { user ->
            if (user.providerData.map { it.providerId }.contains("google.com")) {
                return try {
                    googleSignInClient.silentSignIn().await()
                    true
                } catch (e: ApiException) {
                    Log.e(TAG, "Error: ${e.message}")
                    signOut()
                    false
                }
            }
        }
        return false
    }

    override suspend fun onTapSignIn(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Response.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch(e: Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse {
        val googleCredential = GoogleAuthProvider
            .getCredential(credential.googleIdToken, null)
        return authenticateUser(googleCredential)
    }

    override fun firebaseAuthWithFacebook(token: AccessToken, onComplete: (Boolean) -> Unit) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Response.Success(true)
        }
        catch (e: java.lang.Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun authorizeGoogleSignIn(): String? {
        auth.currentUser?.let { user ->
            if (user.providerData.map { it.providerId }.contains("google.com")) {
                try {
                    val account = googleSignInClient.silentSignIn().await()
                    return account.idToken
                } catch (e: ApiException) {
                    Log.e(TAG, "Error: ${e.message}")
                }
            }
        }
        return null
    }

}