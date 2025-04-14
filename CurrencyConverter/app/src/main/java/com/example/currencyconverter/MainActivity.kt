
package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.platform.LocalFocusManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterApp(modifier: Modifier = Modifier) {
    // Sorted alphabetically by currency code with full names
    val currencies = listOf(
        "AUD" to "Australian Dollar",
        "CAD" to "Canadian Dollar",
        "CHF" to "Swiss Franc",
        "CNY" to "Chinese Yuan",
        "EUR" to "Euro",
        "GBP" to "British Pound",
        "IDR" to "Indonesian Rupiah",
        "INR" to "Indian Rupee",
        "JPY" to "Japanese Yen",
        "USD" to "US Dollar"
    )

    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("IDR") }
    var convertedAmount by remember { mutableStateOf("") }
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    // Add focus manager to handle keyboard
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF8F9FA))
            .padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0F2F1)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Currency Converter",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF00897B),
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 24.sp
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Enter Amount",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF546E7A),
                        fontSize = 16.sp
                    )

                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF00897B),
                            unfocusedBorderColor = Color(0xFFBDBDBD),
                            focusedLabelColor = Color(0xFF00897B)
                        )
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Select Currencies",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF546E7A),
                        fontSize = 16.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = fromCurrency,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("From") },
                                trailingIcon = {
                                    IconButton(onClick = { expanded1 = !expanded1 }) {
                                        Text("▼", fontSize = 12.sp)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF00897B),
                                    unfocusedBorderColor = Color(0xFFBDBDBD),
                                    focusedLabelColor = Color(0xFF00897B)
                                )
                            )

                            DropdownMenu(
                                expanded = expanded1,
                                onDismissRequest = { expanded1 = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .heightIn(max = 500.dp)
                            ) {
                                currencies.forEach { (code, name) ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                "$code - $name",
                                                fontSize = 13.sp,
                                                color = Color(0xFF546E7A)
                                            )
                                        },
                                        onClick = {
                                            fromCurrency = code
                                            expanded1 = false
                                            // Auto-convert if amount is already entered
                                            if (amount.isNotEmpty()) {
                                                val amountValue = amount.toDoubleOrNull() ?: 0.0
                                                val result = convertCurrency(amountValue, fromCurrency, toCurrency)
                                                convertedAmount = "%.2f %s = %.2f %s".format(
                                                    amountValue, fromCurrency, result, toCurrency
                                                )
                                            }
                                        },
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 1.dp)
                                    )
                                    Spacer(modifier = Modifier.height(1.dp))
                                }
                            }
                        }

                        // To currency dropdown
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = toCurrency,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("To") },
                                trailingIcon = {
                                    IconButton(onClick = { expanded2 = !expanded2 }) {
                                        Text("▼", fontSize = 12.sp)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color(0xFF00897B),
                                    unfocusedBorderColor = Color(0xFFBDBDBD),
                                    focusedLabelColor = Color(0xFF00897B)
                                )
                            )

                            DropdownMenu(
                                expanded = expanded2,
                                onDismissRequest = { expanded2 = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .heightIn(max = 500.dp)
                            ) {
                                currencies.forEach { (code, name) ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                "$code - $name",
                                                fontSize = 13.sp,
                                                color = Color(0xFF546E7A)
                                            )
                                        },
                                        onClick = {
                                            toCurrency = code
                                            expanded2 = false
                                            // Auto-convert if amount is already entered
                                            if (amount.isNotEmpty()) {
                                                val amountValue = amount.toDoubleOrNull() ?: 0.0
                                                val result = convertCurrency(amountValue, fromCurrency, toCurrency)
                                                convertedAmount = "%.2f %s = %.2f %s".format(
                                                    amountValue, fromCurrency, result, toCurrency
                                                )
                                            }
                                        },
                                        // Make items more compact
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 1.dp)
                                    )
                                    // Add a tiny spacer between items
                                    Spacer(modifier = Modifier.height(1.dp))
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (amount.isNotEmpty()) {
                        val amountValue = amount.toDoubleOrNull() ?: 0.0
                        val result = convertCurrency(amountValue, fromCurrency, toCurrency)
                        convertedAmount = "%.2f %s = %.2f %s".format(
                            amountValue, fromCurrency, result, toCurrency
                        )
                        focusManager.clearFocus()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00897B)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    "Convert",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (convertedAmount.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE0F2F1)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Result",
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF00897B),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = convertedAmount,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = Color(0xFF00695C)
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = {
                    val temp = fromCurrency
                    fromCurrency = toCurrency
                    toCurrency = temp

                    // Auto-convert after swap if amount is entered
                    if (amount.isNotEmpty()) {
                        val amountValue = amount.toDoubleOrNull() ?: 0.0
                        val result = convertCurrency(amountValue, fromCurrency, toCurrency)
                        convertedAmount = "%.2f %s = %.2f %s".format(
                            amountValue, fromCurrency, result, toCurrency
                        )
                    }
                },
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color(0xFF00897B)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF00897B)
                )
            ) {
                Text(
                    "Swap Currencies",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }}

fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
    val ratesInUSD = mapOf(
        "USD" to 1.0,
        "EUR" to 1.08,
        "GBP" to 1.29,
        "JPY" to 0.0067,
        "AUD" to 0.66,
        "CAD" to 0.74,
        "CHF" to 1.12,
        "CNY" to 0.14,
        "INR" to 0.012,
        "IDR" to 0.0000596
    )

    if (fromCurrency == toCurrency) {
        return amount
    }

    val amountInUSD = amount * (ratesInUSD[fromCurrency] ?: 1.0)

    return amountInUSD / (ratesInUSD[toCurrency] ?: 1.0)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverterTheme {
        CurrencyConverterApp()
    }
}
