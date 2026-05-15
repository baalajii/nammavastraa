package com.example.nammavastra.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nammavastra.data.local.GalleryItem
import com.example.nammavastra.ui.theme.Maroon
import com.example.nammavastra.ui.theme.Cream
import com.example.nammavastra.ui.theme.Gold
import com.example.nammavastra.ui.components.PremiumCard
import com.example.nammavastra.ui.components.PremiumButton
import com.example.nammavastra.viewmodel.GalleryViewModel
import com.example.nammavastra.utils.DateUtils

@Composable
fun GalleryScreen(viewModel: GalleryViewModel = viewModel()) {
    val context = LocalContext.current
    val galleryItems by viewModel.allItems.collectAsState(initial = emptyList())

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    
    var editingItem by remember { mutableStateOf<GalleryItem?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (e: Exception) {}
            imageUri = it
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text("Loom Gallery", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Maroon)
            Text("Showcase your handloom masterpieces", fontSize = 14.sp, color = Color.Gray)
            
            Spacer(Modifier.height(24.dp))

            PremiumCard {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(if (editingItem == null) "Add New Masterpiece" else "Edit Masterpiece", 
                        fontWeight = FontWeight.Bold, color = Maroon)
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        val currentModel = imageUri ?: editingItem?.imageUri?.let { Uri.parse(it) }
                        if (currentModel != null) {
                            AsyncImage(
                                model = currentModel,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        IconButton(onClick = { launcher.launch("image/*") }) {
                            Icon(Icons.Default.AddAPhoto, null, tint = Maroon)
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Price (₹)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("WhatsApp Number") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    PremiumButton(
                        text = if (editingItem == null) "Upload to Gallery" else "Update Item",
                        onClick = {
                            if (title.isNotEmpty() && price.isNotEmpty() && phone.isNotEmpty()) {
                                val item = GalleryItem(
                                    id = editingItem?.id ?: 0,
                                    imageUri = imageUri?.toString() ?: editingItem?.imageUri,
                                    title = title,
                                    price = price,
                                    phone = phone
                                )
                                if (editingItem == null) viewModel.addItem(item) else viewModel.updateItem(item)
                                
                                // Reset
                                title = ""; price = ""; phone = ""; imageUri = null; editingItem = null
                                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    if (editingItem != null) {
                        TextButton(onClick = { 
                            editingItem = null; title = ""; price = ""; phone = ""; imageUri = null 
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Cancel Edit", color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Your Collection", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Maroon)
            Spacer(Modifier.height(16.dp))
        }

        items(galleryItems) { item ->
            GalleryItemCard(
                item = item,
                onDelete = { viewModel.deleteItem(item) },
                onEdit = {
                    editingItem = item
                    title = item.title
                    price = item.price
                    phone = item.phone
                }
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun GalleryItemCard(item: GalleryItem, onDelete: () -> Unit, onEdit: () -> Unit) {
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete Item?") },
            text = { Text("Are you sure you want to remove this masterpiece from your gallery?") },
            confirmButton = {
                TextButton(onClick = { onDelete(); showDeleteConfirm = false }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    PremiumCard {
        Column {
            Box {
                AsyncImage(
                    model = item.imageUri?.let { Uri.parse(it) },
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Row(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)) {
                    IconButton(onClick = onEdit, modifier = Modifier.background(Color.White.copy(0.7f), CircleShape)) {
                        Icon(Icons.Default.Edit, null, tint = Maroon)
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = { showDeleteConfirm = true }, modifier = Modifier.background(Color.White.copy(0.7f), CircleShape)) {
                        Icon(Icons.Default.Delete, null, tint = Color.Red)
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(item.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Maroon)
                Text("₹ ${item.price}", fontSize = 16.sp, color = Gold, fontWeight = FontWeight.Bold)
                
                Spacer(Modifier.height(8.dp))
                
                Text(
                    text = DateUtils.formatTimestamp(item.timestamp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(16.dp))

                Row {
                    Button(
                        onClick = {
                            val url = "https://wa.me/${item.phone}?text=Interested in ${item.title}"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Chat, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("WhatsApp", fontSize = 12.sp)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${item.phone}")))
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Maroon),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Call, null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Call", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
