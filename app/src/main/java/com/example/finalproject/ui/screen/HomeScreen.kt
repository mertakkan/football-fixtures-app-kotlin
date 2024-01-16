package com.example.finalproject.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.finalproject.Fixture
import com.example.finalproject.MainViewModel
import com.example.finalproject.PhonebookTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentUsername: String,
    viewModel: MainViewModel = viewModel(),
    navigateProfile : (Int) -> Unit
) {
    val matches by viewModel.fixtures.observeAsState(emptyList())
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    LaunchedEffect(key1 = Unit) {
        viewModel.getTodayMatches(203, 2023)
    }

    println("matches: $matches")

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PhonebookTopAppBar(
                title = "Home",
                isProfilePicVisible = true,
                scrollBehavior = scrollBehavior,
                userId = 1,
                navigateProfile = navigateProfile,
                navigateBack = {}
            )
        }
    ) {innerPadding ->

        println("currentUserId: $currentUsername")
        Text(
            text = "viewModel.userId.toString(): $currentUsername",
            modifier = Modifier
                .padding(innerPadding)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(innerPadding),
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