package com.example.finalproject.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.AppViewModelProvider
import com.example.finalproject.LoginViewModel
import com.example.finalproject.PhonebookTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateHome: (String) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val maxCharName = 15
    val context = LocalContext.current

    var currentName by remember { mutableStateOf(viewModel.userUiState.userDetails.name) }
    var currentSurname by remember { mutableStateOf(viewModel.userUiState.userDetails.surname) }
    var currentUsername by remember { mutableStateOf(viewModel.userUiState.userDetails.username) }
    var currentPassword by remember { mutableStateOf(viewModel.userUiState.userDetails.password) }

    var isLoginVisible by remember { mutableStateOf(false) }
    var titleText = ""
    var accountInfoText = ""
    var mainButtonText = ""

    when(isLoginVisible) {
        true -> {
            titleText = "Login"
            accountInfoText = "Don't have an account?"
            mainButtonText = "Login"
        }
        false -> {
            titleText = "Sign Up"
            accountInfoText = "Already have an account?"
            mainButtonText = "Create user"
        }
    }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PhonebookTopAppBar(
                title = titleText,
                isProfilePicVisible = false,
                userId = -1,
                canNavigateBack = false,
                navigateProfile = {},
                scrollBehavior = scrollBehavior,
                navigateBack = {}
            )
        },
    ) {innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(15.dp)

        ){
            if (!isLoginVisible) {
                OutlinedTextField(
                    value = viewModel.userUiState.userDetails.name,
                    onValueChange = {
                        viewModel.updateUiState(
                            viewModel.userUiState.userDetails.copy(
                                name = it
                            )
                        )
                    },
                    label = { Text("Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = viewModel.userUiState.userDetails.surname,
                    onValueChange = {
                        viewModel.updateUiState(
                            viewModel.userUiState.userDetails.copy(
                                surname = it
                            )
                        )
                    },
                    label = { Text("Surname") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            OutlinedTextField(
                value = viewModel.userUiState.userDetails.username,
                onValueChange = { viewModel.updateUiState(viewModel.userUiState.userDetails.copy(username = it)) },
                label = { Text("Username") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = viewModel.userUiState.userDetails.password,
                onValueChange = { viewModel.updateUiState(viewModel.userUiState.userDetails.copy(password = it)) },
                label = { Text("Password") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedButton(
                onClick = {
                    if (!isLoginVisible) {
                        coroutineScope.launch {
                            if (
                                viewModel.userUiState.userDetails.name != "" &&
                                viewModel.userUiState.userDetails.surname != "" &&
                                viewModel.userUiState.userDetails.username != "" &&
                                viewModel.userUiState.userDetails.password != ""
                            ) {
                                println("viewModel.userUiState.userDetails.id: ${viewModel.userUiState.userDetails.id}")
                                viewModel.saveUser()
                                println("viewModel.userUiState.userDetails.id: ${viewModel.userUiState.userDetails.id}")
                                navigateHome(viewModel.userUiState.userDetails.username)
                            } else {
                                Toast.makeText(context, "Textfields should not be empty!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            if (
                                viewModel.userUiState.userDetails.username != "" &&
                                viewModel.userUiState.userDetails.password != ""
                                ) {
                                if (viewModel.isUserExist(
                                        viewModel.userUiState.userDetails.username,
                                        viewModel.userUiState.userDetails.password
                                )) {
                                    println("viewModel.userUiState.userDetails.id: ${viewModel.userUiState.userDetails.id}")
                                    navigateHome(viewModel.userUiState.userDetails.username)
                                } else {
                                    Toast.makeText(context, "Your credentials are incorrect!", Toast.LENGTH_SHORT).show()
                                    println("viewModel.userUiState: ${viewModel.userUiState.userDetails}")

                                }
                            }
                        }
                    }
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(mainButtonText)
            }

            Text(
                text = accountInfoText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable { isLoginVisible = !isLoginVisible }
                    .align(Alignment.CenterHorizontally)
            )

        }
    }


}