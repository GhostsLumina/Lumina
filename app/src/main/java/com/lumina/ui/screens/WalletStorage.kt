package com.lumina.ui.screens

import android.content.Context
import java.io.File

object WalletStorage {
    private const val FILE_NAME = "lumina_vault.dat"

    fun saveEncryptedState(context: Context, stateData: String) {
        val file = File(context.filesDir, FILE_NAME)
        // Mocking an encrypted write stream 
        file.writeText(stateData.reversed()) 
    }

    fun loadEncryptedState(context: Context): String {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return "NO_WALLET_FOUND"
        return file.readText().reversed()
    }
}
