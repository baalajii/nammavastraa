package com.example.nammavastra.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nammavastra.ui.theme.Maroon
import com.example.nammavastra.ui.theme.Cream
import com.example.nammavastra.ui.theme.Gold
import com.example.nammavastra.ui.components.PremiumCard

@Composable
fun StoryScreen() {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Our Story",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Maroon
        )
        Text(
            text = "Preserving the Heritage of Handlooms",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        PremiumCard {
            AsyncImage(
                model = "https://rmkv.com/cdn/shop/articles/hanloom_machine_1756b386-b824-4135-afc8-b262c782504c.jpg?v=1775195989",
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(32.dp))

        StorySection(
            title = "The Legacy of Ilkal",
            content = "Originating from the Bagalkot district of Karnataka, Ilkal sarees represent a centuries-old tradition. The uniqueness lies in the 'Tope Teni' technique, where the body and the pallu are joined with skillful loops."
        )

        Spacer(Modifier.height(24.dp))

        StorySection(
            title = "Molakalmuru Craftsmanship",
            content = "Known as the 'Kashi of the South', Molakalmuru is famous for its rich zari work and nature-inspired motifs. Recognized with a GI tag, each saree is a testament to meticulous weaving."
        )

        Spacer(Modifier.height(24.dp))

        PremiumCard(containerColor = Maroon) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Digital Revival Through Namma-Vastra",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "We bridge tradition and technology, empowering weavers to reach global markets and receive the prosperity they deserve.",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(Modifier.height(48.dp))
    }
}

@Composable
fun StorySection(title: String, content: String) {
    Column {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Maroon)
        Box(modifier = Modifier.padding(vertical = 4.dp).width(40.dp).height(2.dp).background(Gold))
        Text(content, fontSize = 15.sp, color = Color.DarkGray, lineHeight = 22.sp)
    }
}
