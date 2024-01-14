package com.example.finalproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.finalproject.ui.theme.FinalProjectTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(
        viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {

        val coroutineScope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val maxCharName = 15

        var currentName by remember { mutableStateOf(viewModel.userUiState.userDetails.name) }
        var currentSurname by remember { mutableStateOf(viewModel.userUiState.userDetails.surname) }
        var currentUsername by remember { mutableStateOf(viewModel.userUiState.userDetails.username) }
        var currentPassword by remember { mutableStateOf(viewModel.userUiState.userDetails.password) }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                PhonebookTopAppBar(
                    title = "Login/Sign In",
                    canNavigateBack = false,
                    scrollBehavior = scrollBehavior
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
                OutlinedTextField(
                    value = viewModel.userUiState.userDetails.name,
                    onValueChange = { viewModel.updateUiState(viewModel.userUiState.userDetails.copy(name = it)) },
                    label = { Text("Name")},
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
                    onValueChange = { viewModel.updateUiState(viewModel.userUiState.userDetails.copy(surname = it)) },
                    label = { Text("Surname")},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = viewModel.userUiState.userDetails.username,
                    onValueChange = { viewModel.updateUiState(viewModel.userUiState.userDetails.copy(username = it)) },
                    label = { Text("Username")},
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
                    label = { Text("Password")},
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
                        coroutineScope.launch {
                            viewModel.saveUser()
                            //navigateBack()
                        }
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create User")
                }
            }
        }


    }

    @Composable
    fun MatchesList(viewModel: MainViewModel = viewModel()) {
        val matches by viewModel.fixtures.observeAsState(emptyList())

        LaunchedEffect(key1 = Unit) {
            viewModel.getTodayMatches(203, 2023)
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()

        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "dummy_pic",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .padding(10.dp)
                    .border(width = 2.dp, color = Color.Blue, shape = CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopStart
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .border(width = 2.dp, color = Color.Blue, shape = RectangleShape),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {

                items(matches) { match ->
                    MatchItem(match)
                }
            }
        }

    }

    @Composable
    fun MatchItem(match: Fixture) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Team Home Logo
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = match.teams.home.logo).apply(block = fun ImageRequest.Builder.() {
                                scale(Scale.FILL)
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Home Team Logo",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop
                )

                // Team Home Name
                Text(text = match.teams.home.name, style = MaterialTheme.typography.bodySmall)

                // Versus Text
                Text(text = "vs", style = MaterialTheme.typography.bodySmall)

                // Team Away Name
                Text(text = match.teams.away.name, style = MaterialTheme.typography.bodySmall)

                // Team Away Logo
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = match.teams.away.logo).apply(block = fun ImageRequest.Builder.() {
                                scale(Scale.FILL)
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Away Team Logo",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

