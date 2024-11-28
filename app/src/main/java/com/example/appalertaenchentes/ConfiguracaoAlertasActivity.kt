package com.example.appalertaenchentes

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appalertaenchentes.databinding.ActivityConfiguracaoAlertasBinding
import com.example.appalertaenchentes.utils.LocationUtils

class ConfiguracaoAlertasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracaoAlertasBinding
    private lateinit var alertasPreferences: AlertasPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracaoAlertasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando o AlertasPreferences
        alertasPreferences = AlertasPreferences(this)

        // Recuperando a lista de bairros
        val bairros = LocationUtils.locationOptions.toList()

        // Configurando o RecyclerView
        binding.recyclerViewBairros.layoutManager = LinearLayoutManager(this)
        val adapter = BairroAdapter(bairros, alertasPreferences)
        binding.recyclerViewBairros.adapter = adapter

        // Configurando o botão de voltar no layout
        binding.backButton.setOnClickListener {
            Log.d("ConfiguracaoAlertasActivity", "Botão de voltar pressionado")
            finish() // Finaliza a atividade
        }

        // Configurando o comportamento do botão voltar do sistema
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("ConfiguracaoAlertasActivity", "Botão de voltar do sistema pressionado")
                finish() // Finaliza a atividade
            }
        })
    }
}