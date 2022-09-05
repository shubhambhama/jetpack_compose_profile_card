package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.profilecardlayout.ui.theme.LightGreen
import com.example.profilecardlayout.ui.theme.ProfileCardLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCardLayoutTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(userProfiles: List<UserProfile> = userProfileList) {
    Scaffold(topBar = { AppBar() }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(userProfiles) { userProfile ->
                    ProfileCard(userProfile = userProfile)
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(navigationIcon = { Icon(Icons.Default.Home, "Content Description", modifier = Modifier.padding(12.dp)) },
            title = { Text(text = "Messaging Application Users") }
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile) {
    Card(modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
            elevation = 8.dp,
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium) {
        Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
            ProfilePicture(userProfile.pictureUrl, userProfile.status)
            ProfileContent(userProfile.name, userProfile.status)
        }
    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean) {
    Card(shape = CircleShape, border = BorderStroke(width = 2.dp,
            color = if (onlineStatus) MaterialTheme.colors.LightGreen else Color.Red),
            modifier = Modifier.padding(16.dp),
            elevation = 4.dp) {
        Image(painter = rememberImagePainter(data = pictureUrl, builder = {
            transformations(CircleCropTransformation())
        }), contentDescription = "Profile Picture Description", modifier = Modifier.size(72.dp))
    }
}

@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean) {
    Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start) {
        CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides if (onlineStatus) 1f else ContentAlpha.medium)) {
            Text(text = userName, style = MaterialTheme.typography.h5)
        }
        CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides ContentAlpha.medium)) {
            Text(text = if (onlineStatus) "Active Now" else "Offline", style = MaterialTheme.typography.body2)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfileCardLayoutTheme {
        MainScreen()
    }
}