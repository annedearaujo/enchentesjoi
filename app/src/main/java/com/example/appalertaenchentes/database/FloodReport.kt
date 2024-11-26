package com.example.appalertaenchentes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flood_reports")
data class FloodReport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bairro: String,
    val gravidade: String,
    val descricao: String,
    val data: Long,
)