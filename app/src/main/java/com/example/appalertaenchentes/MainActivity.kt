package com.example.appalertaenchentes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appalertaenchentes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reportFloodButton.setOnClickListener {
            try {
                startActivity(Intent(this, ReportFloodActivity::class.java))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("MainActivity", "Erro ao iniciar a ReportFloodActivity: ${e.message}")
                Toast.makeText(this, "Erro ao iniciar a atividade", Toast.LENGTH_SHORT).show()
            }
        }
    }
}