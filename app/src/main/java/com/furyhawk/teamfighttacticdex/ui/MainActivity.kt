package com.furyhawk.teamfighttacticdex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.furyhawk.teamfighttacticdex.TftApplication

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as TftApplication).container
        setContent {
            TftApp(appContainer = appContainer)
        }
    }
}
