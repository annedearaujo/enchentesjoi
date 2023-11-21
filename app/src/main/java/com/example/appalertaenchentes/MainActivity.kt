package com.example.appalertaenchentes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appalertaenchentes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declaração de variáveis
    private lateinit var binding: ActivityMainBinding

    // Método chamado quando a atividade é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do listener para o botão de relatar enchente
        binding.reportFloodButton.setOnClickListener {
            try {
                // Tentativa de iniciar a atividade ReportFloodActivity
                startActivity(Intent(this, ReportFloodActivity::class.java))
            } catch (e: Exception) {
                // Em caso de erro, imprima o stack trace, faça log e exiba uma mensagem de erro
                e.printStackTrace()
                Log.e("MainActivity", "Erro ao iniciar a ReportFloodActivity: ${e.message}")
                Toast.makeText(this, "Erro ao iniciar a atividade", Toast.LENGTH_SHORT).show()
            }
        }
    }
}