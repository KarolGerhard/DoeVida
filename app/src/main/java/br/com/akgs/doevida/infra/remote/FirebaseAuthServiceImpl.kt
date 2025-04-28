package br.com.akgs.doevida.infra.remote

import br.com.akgs.doevida.infra.remote.entities.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseAuthServiceImpl : FirebaseAuthService {

    private var auth = FirebaseAuth.getInstance()

    override fun createUser(user: User, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    user.id = userId ?: ""
                    onComplete(true, userId)
                    //task.result?.user?.uid
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    override fun currentUser(): User {
        val user = auth.currentUser
        return if (user != null) {
            User(
                id = user.uid,
                email = user.email ?: "",
                password = "",
                name = user.displayName ?: ""
            )
        } else {
            User("", "", "")
        }
    }

    override fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }


    override fun firebaseAuthWithGoogle(
        account: GoogleSignInAccount,
        onComplete: (Boolean) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }

    }

    override fun signUpWithEmailAndPassword(
        email: String,
        password: String, onComplete: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    onComplete(true, userId)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    override fun signOut() {
        auth.signOut()
    }

//    override suspend fun verifyGoogleSignIn(): Boolean {
//        auth.currentUser?.let { user ->
//            if (user.providerData.map { it.providerId }.contains("google.com")) {
//                return try {
//                    googleSignInClient.silentSignIn().await()
//                    true
//                } catch (e: ApiException) {
//                    Log.e(TAG, "Error: ${e.message}")
//                    signOut()
//                    false
//                }
//            }
//        }
//        return false
//    }

//    override suspend fun onTapSignIn(): OneTapSignInResponse {
//        return try {
//            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
//            Response.Success(signInResult)
//        } catch (e: Exception) {
//            try {
//                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
//                Response.Success(signUpResult)
//            } catch(e: Exception) {
//                Response.Failure(e)
//            }
//        }
//    }

//    override suspend fun signInWithGoogle(credential: SignInCredential): FirebaseSignInResponse {
//        val googleCredential = GoogleAuthProvider
//            .getCredential(credential.googleIdToken, null)
//        return authenticateUser(googleCredential)
//    }


//    override suspend fun signOut(): SignOutResponse {
//        return try {
//            oneTapClient.signOut().await()
//            auth.signOut()
//            Response.Success(true)
//        }
//        catch (e: java.lang.Exception) {
//            Response.Failure(e)
//        }
//    }
//
//    override suspend fun authorizeGoogleSignIn(): String? {
//        auth.currentUser?.let { user ->
//            if (user.providerData.map { it.providerId }.contains("google.com")) {
//                try {
//                    val account = googleSignInClient.silentSignIn().await()
//                    return account.idToken
//                } catch (e: ApiException) {
//                    Log.e(TAG, "Error: ${e.message}")
//                }
//            }
//        }
//        return null
//    }

}