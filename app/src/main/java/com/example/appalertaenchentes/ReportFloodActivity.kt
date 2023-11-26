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
import com.example.appalertaenchentes.database.DatabaseHelper
import com.example.appalertaenchentes.databinding.ActivityReportFloodBinding

class ReportFloodActivity : AppCompatActivity() {

    // Declaração de variáveis
    private lateinit var binding: ActivityReportFloodBinding
    private lateinit var databaseHelper: DatabaseHelper

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
                Log.d("ReportFloodActivity", "Seleção de imagem cancelada")
            }
        }
    }

    // Lista de opções de localização disponíveis para o AutoCompleteTextView.
    private val locationOptions = arrayOf(
        "Adhemar Garcia",
        "America",
        "Anita Garibaldi",
        "Atiradores",
        "Aventureiro",
        "Boa Vista",
        "Boehmerwald",
        "Bom Retiro",
        "Bucarein",
        "Centro",
        "Comasa",
        "Costa e Silva",
        "Espinheiros",
        "Fatima",
        "Floresta",
        "Glória",
        "Guanabara",
        "Iririú",
        "Itaum",
        "Itinga",
        "Parque Guarani",
        "Jardim Iririú",
        "Jardim Paraíso",
        "Jardim Sophia",
        "Jarivatuba",
        "Jativoca",
        "João Costa",
        "Morro do Meio",
        "Nova Brasília",
        "Paranaguamirim",
        "Petrópolis",
        "Bom Retiro",
        "Pirabeiraba",
        "Profipo",
        "Saguaçu",
        "Santa Catarina",
        "Santo Antônio",
        "São Marcos",
        "Ulysses Guimarães",
        "Vila Cubatão",
        "Vila Nova",
        "Zona Industrial Norte",
        "Zona Industrial Tupy"
    )

    // Método chamado quando a atividade é criada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ReportFloodActivity", "onCreate ReportFloodActivity")

        // Configurar visualizações e ouvintes ao criar a atividade.
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding = ActivityReportFloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do botão de voltar
        binding.backButton.setOnClickListener {
            Log.d("ReportFloodActivity", "Botão de voltar pressionado")

            // Encerrar a ReportFloodActivity
            finish()
        }

        // Inicialização do banco de dados
        databaseHelper = DatabaseHelper(this)

        // Configuração das opções do Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gravityOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.severitySpinner.adapter = adapter

        // Definir a opção padrão no Spinner
        binding.severitySpinner.setSelection(0)  // 0 representa a primeira posição no array, que é "Selecione uma gravidade"

        // Verificar se a inicialização do Spinner está ocorrendo conforme o esperado e se as opções de gravidade são carregadas corretamente.
        Log.d("ReportFloodActivity", "Opções de gravidade: ${gravityOptions.joinToString(", ")}")

        // Configuração do AutoCompleteTextView
        val locationAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locationOptions)
        binding.locationAutoComplete.setAdapter(locationAdapter)
    }

    private fun setupListeners() {
        // Configuração de listeners para botões
        binding.reportFloodButton.setOnClickListener {
            reportFlood()
        }

        binding.selectImageButton.setOnClickListener {
            Log.d("ReportFloodActivity", "Botão de seleção de imagem pressionado")
            // Intent para abrir a galeria
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getContent.launch(intent)
        }
    }

    private fun getFormData(): Triple<String, String, String> {
        // Obtém os dados do formulário (localização, gravidade e descrição).
        val location = binding.locationAutoComplete.text.toString()
        val severity = binding.severitySpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()
        return Triple(location, severity, description)
    }

    private fun reportFlood() {
        val (location, severity, description) = getFormData()

        // Antes de validar os dados, limpe a mensagem de erro
        binding.severityInputLayout.error = null

        // Dados antes da validação
        Log.d("ReportFloodActivity", "Localidade antes da validação: $location")
        Log.d("ReportFloodActivity", "Gravidade antes da validação: $severity")
        Log.d("ReportFloodActivity", "Descrição antes da validação: $description")

        // Validação dos campos obrigatórios antes de prosseguir com o relatório de enchente.
        if (location.isEmpty()) {
            Log.d("ReportFloodActivity", "Localidade não informada")
            // Exibir mensagem de erro e definir erro no EditText
            Toast.makeText(this, "O campo localidade é obrigatório.", Toast.LENGTH_SHORT).show()
            binding.locationAutoComplete.error = "Informe uma localidade."
            return
        }

        if (severity == "Selecione uma gravidade") {
            Log.d("ReportFloodActivity", "Gravidade não selecionada")
            // Mensagem de erro para o severityInputLayout
            Toast.makeText(this, "O campo gravidade é obrigatório.", Toast.LENGTH_SHORT).show()
            binding.severityInputLayout.error = "Selecione uma gravidade."
            return
        }

        // Dados após inserção do usuário
        Log.d("ReportFloodActivity", "Localidade: $location, Gravidade: $severity, Descrição: $description")

        // Fornecer feedback ao usuário
        showToast("Relatório enviado com sucesso!")
        // Verificar se o feedback ao usuário está sendo exibido e se o botão está sendo desabilitado corretamente.
        Log.d("ReportFloodActivity", "Relatório enviado com sucesso!")

        // Desabilitar o botão após enviar o relatório
        binding.reportFloodButton.text = "Relatório enviado"
        binding.reportFloodButton.isEnabled = false
    }

    private fun showToast(message: String) {
        // Método para exibir Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReportFloodActivity::class.java)
        }
    }
}