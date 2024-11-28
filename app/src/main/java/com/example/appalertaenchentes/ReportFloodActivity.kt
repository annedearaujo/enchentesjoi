package com.example.appalertaenchentes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appalertaenchentes.data.local.AppDatabase
import com.example.appalertaenchentes.data.local.FloodReport
import com.example.appalertaenchentes.databinding.ActivityReportFloodBinding
import com.example.appalertaenchentes.utils.LocationUtils
import kotlinx.coroutines.launch


class ReportFloodActivity : AppCompatActivity() {

    // Declaração de variáveis
    // binding público para efetuar testes
    lateinit var binding: ActivityReportFloodBinding

    // DAO para interagir com o banco de dados
    private val floodReportDao by lazy {
        AppDatabase.getDatabase(this).floodReportDao()
    }

    // Declaração de gravityOptions como uma propriedade da classe
    private val gravityOptions: Array<String> by lazy {
        resources.getStringArray(R.array.gravity_options)
    }

    // Inicialização do contrato para obter conteúdo da atividade
    // Este contrato é usado para obter a URI da imagem selecionada da galeria.
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Obtenha a URI da imagem selecionada
            val imageUri = result.data?.data

            if (imageUri != null) {
                // Configure a imagem no ImageView
                binding.photosImageView.setImageURI(imageUri)
            } else {
                // Tratar o caso em que o usuário pressionou "Cancelar" na galeria
                Toast.makeText(this, "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show()
                Log.d("ReportFloodActivity", "[LOG] Seleção de imagem cancelada")
            }
        }
    }

    // Método chamado quando a atividade é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ReportFloodActivity", "[LOG] onCreate ReportFloodActivity")

        // Configurar visualizações e ouvintes ao criar a atividade.
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding = ActivityReportFloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do botão de voltar
        binding.backButton.setOnClickListener {
            Log.d("ReportFloodActivity", "[LOG] Botão de voltar pressionado")

            // Encerrar a ReportFloodActivity
            finish()
        }

        // Configuração das opções do Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gravityOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.severitySpinner.adapter = adapter

        // Definir a opção padrão no Spinner
        binding.severitySpinner.setSelection(0)  // 0 representa a primeira posição no array, que é "Selecione uma gravidade"

        // Configuração do AutoCompleteTextView com opções de localização do locationutils
        val locationAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, LocationUtils.locationOptions)
        binding.locationAutoComplete.setAdapter(locationAdapter)
    }

    private fun setupListeners() {
        // Configuração de listeners para botões
        binding.reportFloodButton.setOnClickListener {
            reportFlood()
        }

        binding.selectImageButton.setOnClickListener {
            Log.d("ReportFloodActivity", "[LOG] Botão de seleção de imagem pressionado")
            // Intent para abrir a galeria
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getContent.launch(intent)
        }
    }

    internal fun getFormData(): Triple<String, String, String> {
        // Obtém os dados do formulário (localização, gravidade e descrição).
        val location = binding.locationAutoComplete.text.toString()
        val severity = binding.severitySpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()
        return Triple(location, severity, description)
    }

    internal fun reportFlood() {
        val (location, severity, description) = getFormData()

        // Antes de validar os dados, limpe a mensagem de erro
        binding.severityInputLayout.error = null
        binding.locationAutoComplete.error = null

        // Dados antes da validação
        Log.d("ReportFloodActivity", "[LOG] Localidade antes da validação: $location")
        Log.d("ReportFloodActivity", "[LOG] Gravidade antes da validação: $severity")
        Log.d("ReportFloodActivity", "[LOG] Descrição antes da validação: $description")

        // Validação dos campos obrigatórios antes de prosseguir com o relatório de enchente.
        if (location.isEmpty()) {
            Log.d("ReportFloodActivity", "[LOG] Localidade não informada")
            // Exibir mensagem de erro e definir erro no EditText
            Toast.makeText(this, "O campo localidade é obrigatório.", Toast.LENGTH_SHORT).show()
            binding.locationAutoComplete.error = "Informe uma localidade."
            return
        }

        if (severity == "Selecione uma gravidade") {
            Log.d("ReportFloodActivity", "[LOG] Gravidade não selecionada")
            // Mensagem de erro para o severityInputLayout
            Toast.makeText(this, "O campo gravidade é obrigatório.", Toast.LENGTH_SHORT).show()
            binding.severityInputLayout.error = "Selecione uma gravidade."
            return
        }

        // Criação de uma nova instância de FloodReport
        val floodReport = FloodReport(
            bairro = location,
            gravidade = severity,
            descricao = description,
            data = System.currentTimeMillis()
        )

        // Inserção no banco de dados usando coroutine
        lifecycleScope.launch {
            try {
                floodReportDao.insert(floodReport)
                runOnUiThread {
                    Toast.makeText(this@ReportFloodActivity, "Relatório salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    binding.reportFloodButton.text = "Relatório enviado"
                    binding.reportFloodButton.isEnabled = false
                }
            } catch (e: Exception) {
                Log.e("ReportFloodActivity", "Erro ao salvar relatório: ${e.message}", e)
                runOnUiThread {
                }
            }
        }

        // Dados após inserção do usuário
        Log.d("ReportFloodActivity", "[LOG] Localidade: $location, Gravidade: $severity, Descrição: $description")

        // Fornecer feedback ao usuário
        showToast("Relatório enviado com sucesso!")
        // Verificar se o feedback ao usuário está sendo exibido e se o botão está sendo desabilitado corretamente.
        Log.d("ReportFloodActivity", "[LOG] Relatório enviado com sucesso!")

        // Desabilitar o botão após enviar o relatório
        binding.reportFloodButton.text = "Relatório enviado"
        binding.reportFloodButton.isEnabled = false
    }

    internal fun showToast(message: String) {
        // Método para exibir Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReportFloodActivity::class.java)
        }
    }
}