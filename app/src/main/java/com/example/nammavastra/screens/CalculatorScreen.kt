package com.example.nammavastra.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammavastra.ui.theme.Maroon
import com.example.nammavastra.ui.theme.Cream
import com.example.nammavastra.ui.theme.Gold
import com.example.nammavastra.ui.components.PremiumCard
import com.example.nammavastra.ui.components.PremiumButton
import com.example.nammavastra.utils.PdfExportUtility

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val context = LocalContext.current
    var materialCost by remember { mutableStateOf("") }
    var laborCost by remember { mutableStateOf("") }
    var transportCost by remember { mutableStateOf("") }
    var profitMargin by remember { mutableFloatStateOf(40f) }
    var includeGST by remember { mutableStateOf(true) }
    
    var fabricType by remember { mutableStateOf("Silk") }
    var expanded by remember { mutableStateOf(false) }
    val fabrics = listOf("Silk", "Cotton", "Linen", "Khadi", "Handloom")

    var finalPrice by remember { mutableDoubleStateOf(0.0) }
    var gstAmount by remember { mutableDoubleStateOf(0.0) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text("Price Calculator", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Maroon)
        Text("Professional pricing for handloom weavers", fontSize = 14.sp, color = Color.Gray)

        Spacer(Modifier.height(24.dp))

        PremiumCard {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Input Production Costs", fontWeight = FontWeight.Bold, color = Maroon)
                Spacer(Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = fabricType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Fabric Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        fabrics.forEach { fabric ->
                            DropdownMenuItem(text = { Text(fabric) }, onClick = { fabricType = fabric; expanded = false })
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(value = materialCost, onValueChange = { materialCost = it }, label = { Text("Material Cost (₹)") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(value = laborCost, onValueChange = { laborCost = it }, label = { Text("Labor Cost (₹)") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(value = transportCost, onValueChange = { transportCost = it }, label = { Text("Transport Cost (₹)") }, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(16.dp))

                Text("Profit Margin: ${profitMargin.toInt()}%", fontWeight = FontWeight.Bold, color = Maroon)
                Slider(
                    value = profitMargin,
                    onValueChange = { profitMargin = it },
                    valueRange = 10f..100f,
                    colors = SliderDefaults.colors(thumbColor = Maroon, activeTrackColor = Maroon)
                )

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Include GST (5%)", fontWeight = FontWeight.Medium)
                    Switch(checked = includeGST, onCheckedChange = { includeGST = it }, colors = SwitchDefaults.colors(checkedTrackColor = Maroon))
                }

                Spacer(Modifier.height(24.dp))

                PremiumButton(text = "Calculate Price", onClick = {
                    val m = materialCost.toDoubleOrNull() ?: 0.0
                    val l = laborCost.toDoubleOrNull() ?: 0.0
                    val t = transportCost.toDoubleOrNull() ?: 0.0
                    val base = m + l + t
                    val priceWithProfit = base * (1 + profitMargin / 100.0)
                    if (includeGST) {
                        gstAmount = priceWithProfit * 0.05
                        finalPrice = priceWithProfit + gstAmount
                    } else {
                        gstAmount = 0.0
                        finalPrice = priceWithProfit
                    }
                })
            }
        }

        if (finalPrice > 0) {
            Spacer(Modifier.height(24.dp))
            PremiumCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Breakdown ($fabricType)", fontWeight = FontWeight.Bold, color = Maroon)
                    Divider(Modifier.padding(vertical = 12.dp))
                    
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Base Production Cost")
                        Text("₹${(materialCost.toDoubleOrNull() ?: 0.0) + (laborCost.toDoubleOrNull() ?: 0.0) + (transportCost.toDoubleOrNull() ?: 0.0)}")
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Profit (${profitMargin.toInt()}%)")
                        val base = (materialCost.toDoubleOrNull() ?: 0.0) + (laborCost.toDoubleOrNull() ?: 0.0) + (transportCost.toDoubleOrNull() ?: 0.0)
                        Text("₹%.2f".format(base * (profitMargin/100.0)))
                    }
                    if (includeGST) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("GST (5%)")
                            Text("₹%.2f".format(gstAmount))
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    Text("Total Selling Price", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("₹%.2f".format(finalPrice), fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Maroon)

                    Spacer(Modifier.height(24.dp))
                    
                    OutlinedButton(
                        onClick = { 
                            PdfExportUtility.exportEstimatePdf(
                                context, fabricType, materialCost, laborCost, transportCost, 
                                profitMargin.toInt(), gstAmount, finalPrice
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Maroon)
                    ) {
                        Icon(Icons.Default.PictureAsPdf, null, tint = Maroon)
                        Spacer(Modifier.width(8.dp))
                        Text("Export Estimate PDF", color = Maroon)
                    }
                }
            }
        }
        Spacer(Modifier.height(40.dp))
    }
}
