package com.example.appalertaenchentes

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appalertaenchentes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declaração de variáveis
    // binding público para efetuar testes
    lateinit var binding: ActivityMainBinding

    // Intent para ReportFloodActivity reutilizavel
    private val reportFloodIntent: Intent by lazy {
        val intent = Intent(this, ReportFloodActivity::class.java)
        intent
    }

    // Método chamado quando a atividade é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "[LOG] onCreate MainActivity")

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            // Uso de apply para eliminar a necessidade de chamar binding várias vezes para realizar configurações adicionais no objeto.
            setContentView(root)
        }
        setupViews()
        setupListeners()
    }

    // Configuração das views
    private fun setupViews() {
        // Configuração do menu
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {// Navegar para a tela inicial
                    Log.d("MainActivity", "[LOG] Clicado em Início")
                    true
                }
                R.id.menu_reports -> {
                    Log.d("MainActivity", "[LOG] Clicado em Reportar")
                    // Navegar para a tela de relatório
                    startActivity(reportFloodIntent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        // Configuração dos listeners
        // Configuração do listener para o botão de relatar enchente
        binding.reportFloodButton.setOnClickListener {
            try {
                Log.d("MainActivity", "[LOG] Clicado no botão de Informe")
                // Iniciar a atividade ReportFloodActivity
                startActivity(reportFloodIntent)
            } catch (e: ActivityNotFoundException) {
                handleActivityNotFoundException(e)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    internal fun handleActivityNotFoundException(e: ActivityNotFoundException) {
        // Tratamento de erro de não encontrada a atividade
        Log.e("MainActivity", "[LOG] Atividade não encontrada: ${e.message}")
        Toast.makeText(this, "Atividade não encontrada", Toast.LENGTH_SHORT).show()
    }

    internal fun handleException(e: Exception) {
        // Em caso de erro não tratado, imprima o stack trace, faça log e exiba uma mensagem de erro
        e.printStackTrace()
        Log.e("MainActivity", "[LOG] Erro ao iniciar a ReportFloodActivity: ${e.message}")
        Toast.makeText(this, "Erro ao iniciar a atividade", Toast.LENGTH_SHORT).show()
    }

    companion object {
        // Intent que direciona para ReportFloodActivity
        fun newIntent(context: Context): Intent {
            Log.d("MainActivity", "[LOG] newIntent ReportFloodActivity")

            val intent = Intent(context, ReportFloodActivity::class.java)

            return intent
        }
    }
}