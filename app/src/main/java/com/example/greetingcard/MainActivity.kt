package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                    ComposableApp()
                }
            }
        }
    }
}


@Composable
fun GreetingImage(name: String, from: String = "System", modifier: Modifier) {
    val image = painterResource(id = R.drawable.androidparty)
    Box(modifier = modifier) {
        Image(
            painter = image,
            contentDescription = "party background",
            contentScale = ContentScale.Crop,
            alpha = 0.5F
        )
        Greeting(
            name = name,
            from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Composable
fun Greeting(name: String, from: String = "System", modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = name,
            fontSize = 100.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(36.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                )
        )
    }
}


@Composable
fun ArticleCard(
    title: String,
    shortDesc: String,
    longDesc: String,
    headerImage: Painter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(painter = headerImage, contentDescription = "header image")
        Text(text = title, fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        Text(
            text = shortDesc,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(text = longDesc, modifier = Modifier.padding(16.dp), textAlign = TextAlign.Justify)
    }
}

@Composable
fun InfoCard(
    title: String,
    description: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold

        )
        Text(text = description, textAlign = TextAlign.Justify)
    }
}

@Composable
fun ComposableApp() {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            InfoCard(
                title = stringResource(id = R.string.text_compose_title),
                description = stringResource(id = R.string.text_compose_desc),
                backgroundColor = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = stringResource(id = R.string.image_compose_title),
                description = stringResource(id = R.string.image_compose_desc),
                backgroundColor = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }

        Row(Modifier.weight(1f)) {
            InfoCard(
                title = stringResource(id = R.string.row_compose_title),
                description = stringResource(id = R.string.row_compose_desc),
                backgroundColor = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = stringResource(id = R.string.col_compose_title),
                description = stringResource(id = R.string.col_compose_desc),
                backgroundColor = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ComposeQuadrantApp() {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            ComposableInfoCard(
                title = stringResource(R.string.text_compose_title),
                description = stringResource(R.string.text_compose_desc),
                backgroundColor = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            ComposableInfoCard(
                title = stringResource(R.string.image_compose_title),
                description = stringResource(R.string.image_compose_desc),
                backgroundColor = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)) {
            ComposableInfoCard(
                title = stringResource(R.string.row_compose_title),
                description = stringResource(R.string.row_compose_desc),
                backgroundColor = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            ComposableInfoCard(
                title = stringResource(R.string.col_compose_title),
                description = stringResource(R.string.col_compose_desc),
                backgroundColor = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ComposableInfoCard(
    title: String,
    description: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify
        )
    }
}