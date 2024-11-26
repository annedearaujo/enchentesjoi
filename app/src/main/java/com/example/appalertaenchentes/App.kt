package com.example.appalertaenchentes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Verifica se a versão do Android é Oreo ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "flood_alert_channel"
            val channelName = "Alertas de Enchente"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Canal para notificações de alerta de enchente"
            }

            // Obtém o NotificationManager do sistema
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
