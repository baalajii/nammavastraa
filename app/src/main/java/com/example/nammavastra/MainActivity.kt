package com.example.nammavastra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nammavastra.navigation.NavGraph
import com.example.nammavastra.ui.theme.NammaVastraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NammaVastraTheme {
                NavGraph()
            }
        }
    }
}
