package com.example.appalertaenchentes

import android.content.Context
import android.content.SharedPreferences

class AlertasPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("alertasPreferences", Context.MODE_PRIVATE)

    // Salvar o estado do bairro (se está monitorado ou não)
    fun salvarBairro(bairro: String, monitorado: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(bairro, monitorado)
        editor.apply()
    }

    // Recuperar o estado de monitoramento do bairro
    fun isBairroMonitorado(bairro: String): Boolean {
        return sharedPreferences.getBoolean(bairro, false) // Valor padrão: false
    }
}