package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var tipPercent by remember { mutableStateOf("0") }
    var roundUp by remember { mutableStateOf(false) }
    val tip = calculateTip(billInput.toDoubleOrNull() ?: 0.0, tipPercent.toIntOrNull(), roundUp)
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
        NumberInputField(
            value = billInput,
            onValueChange = { billInput = it },
            label = R.string.bill_amount_label,
            leadingIcon = R.drawable.money_icon,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        NumberInputField(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent_icon,
            imeAction = ImeAction.Done,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )
        RoundUpToggle(checked = roundUp, onCheckedChange = { roundUp = it })
        TotalTipText(tip = NumberFormat.getCurrencyInstance().format(tip))
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun UpperTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
fun NumberInputField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int = R.string.unknown_field,
    @DrawableRes leadingIcon: Int,
    imeAction: ImeAction = ImeAction.Default,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null, tint = Color.DarkGray, modifier = Modifier.size(20.dp)) },
        singleLine = true,
        label = { (Text(text = stringResource(id = label))) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}


@Composable
fun RoundUpToggle(
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
fun TotalTipText(tip: String) {
    Text(
        text = stringResource(id = R.string.tip_amount, tip),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp),
    )
}


fun calculateTip(amount: Double, percentage: Int?, roundUp: Boolean = false): Double {
    var tip = (percentage ?: 0) / 100.0 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return tip
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipTimePreview() {
    TipCalculatorTheme {
        TipTimeApp()
    }
}