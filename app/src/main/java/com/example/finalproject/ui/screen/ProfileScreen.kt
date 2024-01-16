package com.example.finalproject.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.AppViewModelProvider
import com.example.finalproject.PhonebookTopAppBar
import com.example.finalproject.ProfileViewModel
import com.example.finalproject.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    logout: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    viewModel.showUiState()

    val uiState = viewModel.uiState.collectAsState()
    println("uiState.value: ${uiState.value}")
    var userName by remember { mutableStateOf(uiState.value.userDetails.name) }
    var userSurname by remember { mutableStateOf(uiState.value.userDetails.surname) }
    var userFavTeam by remember { mutableStateOf(uiState.value.userDetails.favTeam) }

    if (uiState.value.userDetails.id != 0) {
        LaunchedEffect(Unit) {
            userName = uiState.value.userDetails.name
            userSurname = uiState.value.userDetails.surname
            userFavTeam = uiState.value.userDetails.favTeam
        }
    }

    var isEditable by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            PhonebookTopAppBar(
                title = "Profile",
                isProfilePicVisible = false,
                userId = -1,
                canNavigateBack = true,
                navigateProfile = {},
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {

            if (!isEditable) {

                FloatingActionButton(
                    onClick = { isEditable = !isEditable },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(10.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit_ind",
                    )
                }
            } else {
                FloatingActionButton(
                    onClick = {
                        isEditable = !isEditable
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(10.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Save",
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "dummy_pic",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Name") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                enabled = isEditable,
                singleLine = true
            )

            OutlinedTextField(
                value = userSurname,
                onValueChange = { userSurname = it },
                label = { Text("Surname") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                enabled = isEditable,
                singleLine = true
            )

            OutlinedButton(
                onClick = {
                    logout()
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .padding(horizontal = 25.dp)
            ) {
                Text("Logout")
            }

        }

    }
}

@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(
        navigateBack = {},
        logout = {}
    )
}