package com.example.nammavastra.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nammavastra.ui.theme.Maroon
import com.example.nammavastra.ui.theme.Cream
import com.example.nammavastra.ui.theme.Gold
import com.example.nammavastra.ui.components.PremiumCard
import com.example.nammavastra.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Welcome to",
            fontSize = 16.sp,
            color = Maroon.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Namma-Vastra",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Maroon
        )
        Text(
            text = "Digitizing the Art of Handloom",
            fontSize = 14.sp,
            color = Gold,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        PremiumCard(modifier = Modifier.fillMaxWidth()) {
            Box {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1610030469983-98e550d6193c",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
                    color = Maroon.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Heritage Collection", color = Color.White, modifier = Modifier.padding(8.dp), fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            "Quick Actions",
            modifier = Modifier.align(Alignment.Start),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            QuickActionCard(
                title = "Trends",
                icon = Icons.Default.TrendingUp,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Trends.route) }
            )
            Spacer(Modifier.width(16.dp))
            QuickActionCard(
                title = "Gallery",
                icon = Icons.Default.PhotoLibrary,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Gallery.route) }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            QuickActionCard(
                title = "Calculator",
                icon = Icons.Default.Calculate,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Calculator.route) }
            )
            Spacer(Modifier.width(16.dp))
            QuickActionCard(
                title = "Story",
                icon = Icons.Default.HistoryEdu,
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Story.route) }
            )
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun QuickActionCard(title: String, icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    PremiumCard(
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = Maroon, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = Maroon)
        }
    }
}
