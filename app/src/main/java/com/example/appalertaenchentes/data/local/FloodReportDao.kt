package com.example.appalertaenchentes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FloodReportDao {
    @Insert
    suspend fun insert(floodReport: FloodReport)

    @Query("SELECT * FROM flood_reports WHERE bairro = :bairro")
    suspend fun getReportsByBairro(bairro: String): List<FloodReport>

    @Query("SELECT * FROM flood_reports")
    suspend fun getAllReports(): List<FloodReport>
}
