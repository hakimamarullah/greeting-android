package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.ui.theme.BirthdayCardTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CardContainer()
                }
            }
        }
    }
}


@Composable
private fun CardContainer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(rgb(210, 232, 212)),
        verticalArrangement = Arrangement.SpaceBetween, // Adjusts content to be spread out
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User info in the center
        Column(
            modifier = Modifier
                .weight(1f) // Takes up available space, pushing the content to the center
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserInfo(fullName = "John Doe", title = "Senior Backend Developer")
        }

        // Spacer to fill space and push ContactInfo to the bottom
        Spacer(modifier = Modifier.height(16.dp)) // Add space if needed

        // Contact info at the bottom

        ContactInfo(
            phone = "+62 852-9622-3972",
            email = "android@gmail.com",
            userId = "@android_dev"
        )

    }
}

@Composable
private fun UserInfo(fullName: String, title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidIcon(backgroundColor = rgb(7, 48, 66))
        Text(
            text = fullName,
            fontSize = 48.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        Text(text = title, color = rgb(2, 110, 59), textAlign = TextAlign.Center)
    }
}

@Composable
private fun ContactInfo(
    phone: String = "+62",
    email: String = "example@gmail.com",
    userId: String = "@Android",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            ContactItem(iconImage = painterResource(id = R.drawable.call), value = phone)
            ContactItem(iconImage = painterResource(id = R.drawable.share_icon), value = userId)
            ContactItem(iconImage = painterResource(id = R.drawable.mail_icon), value = email)
        }
    }
}

@Composable
private fun AndroidIcon(
    backgroundColor: Color = Color(red = 0f, green = 0f, blue = 0.251f),
    size: Dp = 128.dp,
    modifier: Modifier = Modifier
) {
    Box(Modifier.size(size)) {
        Image(
            painter = painterResource(id = R.drawable.android_logo),
            contentDescription = "android_icon",
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
private fun ContactItem(iconImage: Painter, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = iconImage,
            contentDescription = "contact_icon",
            tint = rgb(2, 110, 59)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = value)
    }
}

private fun rgb(red: Int = 0, green: Int = 0, blue: Int = 0, alpha: Float = 1.0f): Color {
    return Color(red = red / 255f, green = green / 255f, blue = blue / 255f, alpha = alpha)
}
