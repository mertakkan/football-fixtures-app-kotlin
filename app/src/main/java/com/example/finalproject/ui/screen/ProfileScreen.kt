package com.example.finalproject.ui.screen

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.finalproject.AppViewModelProvider
import com.example.finalproject.PhonebookTopAppBar
import com.example.finalproject.ProfileViewModel
import com.example.finalproject.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    logout: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    viewModel.showUiState()

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var userProfilePicUri by remember { mutableStateOf<Uri>(Uri.parse(viewModel.uiState.value.userDetails.profilePicId)) }
    val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()){
        userProfilePicUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val uiState = viewModel.uiState.collectAsState()
    println("uiState.value: ${uiState.value}")
    var userName by remember { mutableStateOf(uiState.value.userDetails.name) }
    var userSurname by remember { mutableStateOf(uiState.value.userDetails.surname) }
    var userFavTeam by remember { mutableStateOf(uiState.value.userDetails.favTeam) }

    if (uiState.value.userDetails.id != 0) {
        LaunchedEffect(Unit) {
            userName = uiState.value.userDetails.name
            userSurname = uiState.value.userDetails.surname
            userProfilePicUri = Uri.parse(uiState.value.userDetails.profilePicId)
        }
    }

    var isEditable by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            PhonebookTopAppBar(
                title = "Profile",
                isProfilePicVisible = false,
                canNavigateBack = true,
                userId = -1,
                navigateProfile = {},
                navigateBack = navigateBack,
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
                        viewModel.updateInd(
                            userName,
                            userSurname,
                            userProfilePicUri.toString()
                        )
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

            if (userProfilePicUri.path?.isNotEmpty() == true) {

                println("capturedImageUri.path: ${userProfilePicUri.path}")
                println("capturedImageUri: $userProfilePicUri")

                Image(
                    painter = rememberAsyncImagePainter(model = userProfilePicUri),
                    contentDescription = "profile_pic",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(width = 2.dp, color = Color.Black, shape = CircleShape),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            } else {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "dummy_pic",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(width = 2.dp, color = Color.Black, shape = CircleShape),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }

            if (isEditable) {

                Text(
                    text = "Edit Profile Pic",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    android.Manifest.permission.CAMERA
                                )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                        }
                )
            }

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

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"

    return createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(
        navigateBack = {},
        logout = {}
    )
}