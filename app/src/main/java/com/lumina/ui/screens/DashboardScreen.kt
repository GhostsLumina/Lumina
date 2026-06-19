package com.lumina.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumina.ui.theme.CyberBlack
import com.lumina.ui.theme.CyberPurple
import com.lumina.ui.theme.DarkGray
import com.lumina.ui.theme.NeonCyan

@Composable
fun DashboardScreen() {
    var currentUniverse by remember { mutableStateOf("SAFE") }
    
    Scaffold(
        topBar = { TopHeader(currentUniverse) },
        bottomBar = { BottomNavBar() },
        containerColor = CyberBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UniverseToggle(
                currentUniverse = currentUniverse,
                onUniverseSelected = { currentUniverse = it }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LiraStatusCard(currentUniverse)
        }
    }
}

@Composable
fun TopHeader(universe: String) {
    val glowColor = if (universe == "SAFE") NeonCyan else CyberPurple
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("LUMINA", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 2.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("LIRA v1.0", fontSize = 12.sp, color = glowColor, modifier = Modifier.padding(end = 8.dp))
            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(glowColor))
        }
    }
}

@Composable
fun UniverseToggle(currentUniverse: String, onUniverseSelected: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(25.dp)).background(DarkGray).padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val universes = listOf("SAFE", "DEGEN")
        universes.forEach { universe ->
            val isSelected = currentUniverse == universe
            val targetColor = if (universe == "SAFE") NeonCyan else CyberPurple
            Button(
                onClick = { onUniverseSelected(universe) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) targetColor.copy(alpha = 0.2f) else Color.Transparent,
                    contentColor = if (isSelected) targetColor else Color.Gray
                ),
                modifier = Modifier.weight(1f).fillMaxHeight().border(if (isSelected) 1.dp else 0.dp, if (isSelected) targetColor else Color.Transparent, RoundedCornerShape(21.dp)),
                shape = RoundedCornerShape(21.dp)
            ) {
                Text(text = universe, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LiraStatusCard(universe: String) {
    val borderColor = if (universe == "SAFE") NeonCyan else CyberPurple
    val aiMessage = if (universe == "SAFE") "Analyzing structural yields. Market steady." else "High volatility detected. RugShield is listening to raw mempools."
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(DarkGray).border(1.dp, Brush.horizontalGradient(listOf(borderColor, Color.Transparent)), RoundedCornerShape(16.dp)).padding(20.dp)) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = borderColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text("LIRA INTELLIGENCE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = aiMessage, color = Color.LightGray, fontSize = 14.sp, lineHeight = 20.dp)
        }
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = DarkGray, tonalElevation = 0.dp) {
        NavigationBarItem(icon = { Icon(Icons.Default.Wallet, "Wallet") }, label = { Text("Core") }, selected = true, onClick = {})
        NavigationBarItem(icon = { Icon(Icons.Default.Shield, "Shield") }, label = { Text("Shield") }, selected = false, onClick = {})
        NavigationBarItem(icon = { Icon(Icons.Default.Language, "Web3") }, label = { Text("Web3") }, selected = false, onClick = {})
    }
}
