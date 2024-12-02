package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.TipCalculatorTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipTimeApp()
                }
            }
        }
    }
}

@Composable
fun TipTimeApp() {
    var billInput by remember { mutableStateOf("0.0") }
    val tip = calculateTip(billInput.toDoubleOrNull() ?: 0.0)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UpperTitle()
        BillInputField(value = billInput, onValueChange = { billInput = it })
        TotalTipText(tip = NumberFormat.getCurrencyInstance().format(tip))
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun UpperTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.upper_title),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
        )
    }
}

@Composable
fun BillInputField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { (Text(text = stringResource(id = R.string.bill_amount_label))) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Composable
fun TotalTipText(tip: String) {
    Text(
        text = stringResource(id = R.string.tip_amount, tip),
        modifier = Modifier.padding(top = 16.dp),
        fontWeight = FontWeight.Medium
    )
}

fun calculateTip(amount: Double): Double {
    return 15 / 100.0 * amount;
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipTimePreview() {
    TipCalculatorTheme {
        TipTimeApp()
    }
}