package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.model.ArtPhoto
import com.example.artspace.ui.theme.ArtSpaceTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    val artData: MutableMap<Int, ArtPhoto> = mutableMapOf()
    artData[2] = ArtPhoto(
        1,
        2024,
        "Nikolai Lehmann",
        "Lucerna Building",
        R.drawable.nikolai_lehmann_lucernabuilding_2024
    )
    artData[1] = ArtPhoto(
        2,
        2019,
        "Nikolai Lehmann 2",
        "Building 2",
        R.drawable.nikolai_lehmann_lucernabuilding_2024
    )
    var currentPhoto by remember { mutableStateOf(artData[1]) }

    Box(
        modifier = Modifier
            .fillMaxSize() // Ensure the Box takes up the entire screen
            .padding(16.dp) // Padding for general spacing, you can adjust as per design
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Center the column in the screen
                .fillMaxWidth()
                .wrapContentHeight(), // Allow the column to wrap content vertically
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // PhotoFrame (Top part)
            currentPhoto?.painterResource?.let {
                PhotoFrame(
                    painter = it,
                    alt = currentPhoto?.description
                )
            }
            Spacer(modifier = Modifier.height(20.dp)) // Adjust space between PhotoFrame and PhotoInfo

            // PhotoInfo (Middle part)
            currentPhoto?.let { PhotoInfo(artPhoto = it) }

            Spacer(modifier = Modifier.height(20.dp)) // Adjust space between PhotoInfo and NavigatorButton

            // NavigatorButton (Bottom part)
            NavigatorButton(
                idx = 1,
                upperBound = artData.size,
                onValueChange = { currentPhoto = artData[it] }
            )
        }
    }
}



@Composable
fun PhotoFrame(@DrawableRes painter: Int, alt: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add a subtle shadow that gives a seamless, framed look without an explicit border
        Box(
            modifier = Modifier
                .shadow(
                    10.dp,
                    shape = RectangleShape,
                    ambientColor = Color.Black.copy(alpha = 0.15f)
                ) // Soft, seamless shadow
                .padding(40.dp) // Padding inside the "frame" // Shadow offset towards the bottom-right (light source from left)
        ) {
            Image(
                painter = painterResource(id = painter),
                contentScale = ContentScale.Fit,  // Fit image inside the frame without distortion
                contentDescription = alt
            )
        }
    }
}





@Composable
fun PhotoInfo(artPhoto: ArtPhoto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray.copy(alpha = 0.6f))
                .padding(35.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = artPhoto.description ?: "unknown", fontSize = 25.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = artPhoto.artist, fontWeight = FontWeight.Bold)
                Text(text = "(${artPhoto.year})", modifier = Modifier.padding(start = 5.dp))
            }
        }
    }

}

@Composable
fun NavigatorButton(
    idx: Int,
    upperBound: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIdx by remember { mutableStateOf(idx) }
    var prev = {
        currentIdx = kotlin.math.max(idx, currentIdx - 1)
        onValueChange(currentIdx)
    }
    var next = {
        currentIdx += 1
        if (currentIdx > upperBound) {
            currentIdx = idx
        }
        onValueChange(currentIdx)
    }
    Row(
        modifier = modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = prev, shape = RoundedCornerShape(25.dp), modifier = Modifier.width(100.dp)) {
            Text(text = stringResource(id = R.string.previous_btn))
        }
        Button(onClick = next, shape = RoundedCornerShape(25.dp), modifier = Modifier.width(100.dp)) {
            Text(text = stringResource(id = R.string.next_btn))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}