package br.com.akgs.doevida.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.akgs.doevida.ui.navigation.TopAppBar
import br.com.akgs.doevida.ui.util.PhoneMaskVisualTransformation
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    onAction: (ProfileAction) -> Unit = {},
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.uiState.collectAsState()
    val user = state.user

    val actions = viewModel.actions

    LaunchedEffect(Unit) {
        actions.collect { action ->
            onAction(action)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar("Meus dados") {
                viewModel.onAction(ProfileAction.OnBackClick)
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.error?.let { error ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = null,
                                tint = Color(0xFF95313B)
                            )
                            Text(
                                text = error,
                                style = TextStyle(
                                    color = Color(0xFF95313B),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            )
                        }
                    }
                }
                if (!state.isEditMode) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            text = "Suas informações",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        InfoField("Nome", user?.name ?: "")
                        InfoField("Email", user?.email ?: "")
                        HorizontalDivider(
                            color = Color(0xFF95313B),
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        InfoField("Tipo Sanguineo", user?.bloodType ?: "")
                        InfoField("Telefone", user?.phone ?: "")
                        InfoField("Endereço", "${user?.city} - ${user?.state}")

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.onAction(ProfileAction.OnEditMode) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF95313B),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(text = "Editar")
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        OutlinedTextField(
                            value = state.editBloodType.ifEmpty { user?.bloodType ?: "" },
                            onValueChange = { viewModel.onAction(ProfileAction.OnUpdateBloodType(it)) },
                            label = { Text("Tipo Sanguineo") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        OutlinedTextField(
                            value = state.editPhone.ifEmpty { user?.phone ?: "" },
                            onValueChange = { viewModel.onAction(ProfileAction.OnUpdatePhone(it)) },
                            label = { Text("Telefone") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PhoneMaskVisualTransformation(),
                        )
                        ExposedDropdownMenuBox(
                            expanded = state.isEstadoDropdownExpanded,
                            onExpandedChange = { viewModel.onAction(ProfileAction.OnToggleEstadoDropdown) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedTextField(
                                value = state.editState.ifEmpty { user?.state ?: "" },
                                onValueChange = {},
                                label = { Text("Estado") },
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = state.isEstadoDropdownExpanded
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            )
                            ExposedDropdownMenu(
                                expanded = state.isEstadoDropdownExpanded,
                                onDismissRequest = { viewModel.onAction(ProfileAction.OnToggleEstadoDropdown) },
                            ) {
                                state.states.forEach { estado ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(estado)
                                        },
                                        onClick = {
                                            viewModel.onAction(ProfileAction.OnUpdateState(estado))
                                        }
                                    )
                                }
                            }
                        }
                        ExposedDropdownMenuBox(
                            expanded = state.isCidadeDropdownExpanded,
                            onExpandedChange = { viewModel.onAction(ProfileAction.OnToggleCidadeDropdown) },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedTextField(
                                value = state.editCity.ifEmpty { user?.city ?: "" },
                                onValueChange = {},
                                label = { Text("Cidade") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = state.isCidadeDropdownExpanded
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                            )
                            ExposedDropdownMenu(
                                expanded = state.isCidadeDropdownExpanded,
                                onDismissRequest = { viewModel.onAction(ProfileAction.OnToggleCidadeDropdown) },
                            ) {
                                state.cities.forEach { cidade ->
                                    DropdownMenuItem(
                                        text = { Text(cidade) },
                                        onClick = {
                                            viewModel.onAction(ProfileAction.OnUpdateCity(cidade))
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Button(
                                onClick = { viewModel.onAction(ProfileAction.OnDismiss) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE0E0E0),
                                    contentColor = Color(0xFF95313B)
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Text(text = "Cancelar")
                            }
                            Button(
                                onClick = { viewModel.onAction(ProfileAction.OnSaveUserInfo) },
                                modifier = Modifier
                                    .height(52.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF95313B),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                if (state.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(4.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(text = "Salvar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.showDiscardChangesDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onAction(ProfileAction.OnDismiss) },
            title = { Text("Descartar alterações?") },
            text = { Text("Você tem alterações não salvas. Deseja descarta-las?") },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.onAction(ProfileAction.OnDismiss)
                        viewModel.onAction(ProfileAction.OnEditMode)
                    }
                ) {
                    Text("Não, continuar editando")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onAction(ProfileAction.OnDismiss)
                    }
                ) {
                    Text("Sim, descartar")
                }
            }
        )
    }

}


@Composable
fun InfoField(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = TextStyle(
                color = Color(0xFF6A343A),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Text(
            modifier = Modifier
                .padding(end = 8.dp),
            text = value,
            style = TextStyle(
                color = Color(0xFF95313B),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }

}

@Composable
@Preview(showBackground = true)
fun InformationScreePreview() {
    InformationScreen()
}