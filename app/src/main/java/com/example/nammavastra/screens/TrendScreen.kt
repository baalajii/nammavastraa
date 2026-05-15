package com.example.nammavastra.screens

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import coil.compose.AsyncImage
import com.example.nammavastra.model.Trend
import com.example.nammavastra.ui.theme.Maroon
import com.example.nammavastra.ui.theme.Cream
import com.example.nammavastra.ui.theme.Gold
import com.example.nammavastra.ui.components.PremiumCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendScreen() {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("nammavastra_prefs", Context.MODE_PRIVATE) }

    val allTrends = remember {
        listOf(
            Trend("1", "Pastel Pink Silk", "Trending in Bengaluru boutiques", "https://medias.utsavfashion.com/media/catalog/product/cache/1/image/1000x/040ec09b1e35df139433887a97daa66f/w/o/woven-bangalore-silk-saree-in-pink-v1-snga4617.jpg", "Silk"),
            Trend("2", "Elegant Grey Saree", "Minimal modern handloom trend", "https://sutisancha.com/cdn/shop/files/Untitled_1587_x_2280_px_58.jpg?v=1731994433&width=2048", "Handloom"),
            Trend("3", "Royal Blue Wedding Saree", "Popular festive collection", "https://www.karagiri.com/cdn/shop/files/zamdani-silk-5011_3.jpg?v=1695629461", "Bridal"),
            Trend("4", "Mint Green Cotton", "Summer fashion favorite", "https://mayinindia.com/cdn/shop/files/2_b250f1ae-c0b0-418d-b17f-a3f0731d6ee5.jpg?v=1778176440&width=1946", "Cotton"),
            Trend("5", "Lavender Designer Saree", "Youth-inspired urban trend", "https://houseofhind.com/cdn/shop/files/1-1_2160x.jpg?v=1742581015", "Silk"),
            Trend("6", "Golden Border Silk", "Luxury boutique collection", "https://ik.imagekit.io/4sjmoqtje/tr:c-at_max/cdn/shop/files/SG291475_2.jpg?v=1763549138&w=1000", "Silk"),
            Trend("7", "Traditional Ilkal Weave", "Heritage-inspired modern styling", "https://www.mystore.in/s/62ea2c599d1398fa16dbae0a/6773d3ba32d4893068c7fe92/img_4047.jpg", "Handloom"),
            Trend("8", "Maroon Bridal Saree", "Classic South Indian bridal fashion", "https://assets0.mirraw.com/images/11436531/WhatsApp_Image_2023-02-03_at_11.05.39_AM_(1)_zoom.jpeg?1682313133", "Bridal"),
            Trend("9", "Soft Beige Handloom", "Minimal elegant office wear", "https://cdn.shopify.com/s/files/1/0671/5562/4254/files/stone-mist-cotton-indidha-saree-7151828-_1.png?v=1764826523", "Handloom"),
            Trend("10", "Contemporary Cotton Fusion", "Fusion styling for young buyers", "https://assets0.mirraw.com/images/13640318/image_original_long_webp.webp?1765549402", "Cotton")
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Silk", "Cotton", "Bridal", "Handloom")

    val filteredTrends = remember(searchQuery, selectedCategory) {
        allTrends.filter { 
            (selectedCategory == "All" || it.category == selectedCategory) &&
            (it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Header
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TrendingUp, null, tint = Maroon, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(8.dp))
                Text("Trend Board", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Maroon)
            }
            Text("Premium Saree Inspirations", fontSize = 14.sp, color = Color.Gray)
            
            Spacer(Modifier.height(16.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search styles...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Maroon,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))

            // Categories
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Maroon,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Maroon
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = Maroon,
                            enabled = true,
                            selected = selectedCategory == category
                        )
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredTrends) { trend ->
                TrendCard(trend, sharedPrefs)
            }
        }
    }
}

@Composable
fun TrendCard(trend: Trend, sharedPrefs: android.content.SharedPreferences) {
    var isLiked by remember { mutableStateOf(sharedPrefs.getBoolean("liked_${trend.id}", false)) }
    val baseLikes = remember { (100..500).random() }
    val displayLikes = if (isLiked) baseLikes + 1 else baseLikes

    PremiumCard {
        Column {
            Box {
                AsyncImage(
                    model = trend.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(240.dp),
                    contentScale = ContentScale.Crop
                )
                
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                    color = Gold,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = trend.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Maroon
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(trend.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Maroon)
                Text(trend.description, fontSize = 14.sp, color = Color.Gray)
                
                Spacer(Modifier.height(16.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        isLiked = !isLiked
                        sharedPrefs.edit().putBoolean("liked_${trend.id}", isLiked).apply()
                    }) {
                        Icon(
                            if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            null,
                            tint = if (isLiked) Maroon else Color.Gray
                        )
                    }
                    Text("$displayLikes Likes", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
