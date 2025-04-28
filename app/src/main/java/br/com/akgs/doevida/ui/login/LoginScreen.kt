package br.com.akgs.doevida.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.compose.koinViewModel

@Suppress("DEPRECATION")
@Composable
fun LoginScreen(
    onAction: (LoginAction) -> Unit,
) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    val viewModel = koinViewModel<LoginViewModel>()
    val state by viewModel.uiState.collectAsState()

    val actions = viewModel.actions

    LaunchedEffect(Unit) {
        actions.collect { action ->
            onAction(action)
        }
    }

//    val launcher = rememberFirebaseAuthLauncher(
//        onAuthComplete = { result ->
//            user = result.user
//        },
//        onAuthError = {
//            user = null
//        }
//    )

    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current


//    var password by remember { mutableStateOf(state.value.password) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_title),
                contentDescription = "Doe Vida Logo",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    onAction(LoginAction.OnEmailChange(it))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = {onAction(LoginAction.OnPasswordChange(it))},
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        modifier = Modifier.clickable {
                            isPasswordVisible = !isPasswordVisible
                        }
                    )
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.onAction(LoginAction.OnLoginClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF690714),
                    contentColor = Color(0xFFFFFFFF)
                ),
            ) {
                Text(
                    "Entre",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                "ou", style = TextStyle(
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Medium
//                )
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    val gso =
//                        Builder(DEFAULT_SIGN_IN)
//                            .requestIdToken(token)
//                            .requestEmail()
//                            .build()
//                    val googleSignInClient = GoogleSignIn
//                        .getClient(context, gso)
//                    launcher
//                        .launch(googleSignInClient.signInIntent)
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                shape = RoundedCornerShape(24.dp),
//                colors = ButtonDefaults.buttonColors(
//                    Color(0xFFFFDEE1),
//                    contentColor = Color(0xFF000000)
//                ),
//                border = BorderStroke(2.dp, Color(0xFF95313B))
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(36.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.google_logo),
//                        contentDescription = "Google Logo",
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "Entre com sua conta Google",
//                        style = TextStyle(
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                    )
//                }
//            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = {
                    viewModel.onAction(LoginAction.OnNewRegisterClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFFFFFFF),
                    contentColor = Color(0xFF95313B)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Ainda não tem uma conta? Cadastre-se",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
    }
}

//@Composable
//fun rememberFirebaseAuthLauncher(
//    onAuthComplete: (AuthResult) -> Unit,
//    onAuthError: (ApiException) -> Unit
//): ManagedActivityResultLauncher<Intent, ActivityResult> {
//    val scope = rememberCoroutineScope()
//    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(ApiException::class.java)!!
//            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
//            scope.launch {
//                val authResult = Firebase.auth.signInWithCredential(credential).await()
//                onAuthComplete(authResult)
//            }
//        } catch (e: ApiException) {
//            onAuthError(e)
//        }
//    }
//}

//@Composable
//@Preview(showBackground = true)
//fun LoginScreenPreview() {
//    LoginScreen {
//
//    }
//}
