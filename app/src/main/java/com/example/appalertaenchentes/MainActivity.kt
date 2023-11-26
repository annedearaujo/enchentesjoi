package com.example.appalertaenchentes

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appalertaenchentes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Declaração de variáveis
    private lateinit var binding: ActivityMainBinding

    // Método chamado quando a atividade é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate MainActivity")
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            // Uso de apply para eliminar a necessidade de chamar binding várias vezes para realizar configurações adicionais no objeto.
            setContentView(root)
        }
        setupViews()
        setupListeners()
        setupCrashButton()
    }

    // Configuração das views
    private fun setupViews() {
        // Configuração do menu
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Se já estiver na tela inicial, não faz nada
                    if (this is MainActivity) {
                        return@setOnItemSelectedListener true
                    }
                    // Navegar para a tela inicial
                    startActivity(newIntent(this))
                    true
                }
                R.id.menu_reports -> {
                    // Navegar para a tela de relatório
                    startActivity(ReportFloodActivity.newIntent(this))
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
                // Criar um Intent para a ReportFloodActivity
                val intent = ReportFloodActivity.newIntent(this)
                // Definir as flags para limpar a pilha de atividades e iniciar uma nova tarefa
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                // Iniciar a atividade ReportFloodActivity
                startActivity(Intent(this, ReportFloodActivity::class.java))
            } catch (e: ActivityNotFoundException) {
                handleActivityNotFoundException(e)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun setupCrashButton() {
        // Creates a button that mimics a crash when pressed (Firebase Crashlytics)
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(
            crashButton, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun handleActivityNotFoundException(e: ActivityNotFoundException) {
        // Tratamento de erro de não encontrada a atividade
        Log.e("MainActivity", "Atividade não encontrada: ${e.message}")
        Toast.makeText(this, "Atividade não encontrada", Toast.LENGTH_SHORT).show()
    }

    private fun handleException(e: Exception) {
        // Em caso de erro não tratado, imprima o stack trace, faça log e exiba uma mensagem de erro
        e.printStackTrace()
        Log.e("MainActivity", "Erro ao iniciar a ReportFloodActivity: ${e.message}")
        Toast.makeText(this, "Erro ao iniciar a atividade", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReportFloodActivity::class.java)
        }
    }
}