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
            }
        }
    }

    // Lista de opções de localização
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

        // Configuração do AutoCompleteTextView
        val locationAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locationOptions)
        binding.locationAutoComplete.setAdapter(locationAdapter)
    }

    // Método para relatar enchente
    private fun reportFlood() {
        // Obtenção de dados do formulário
        val location = binding.locationAutoComplete.text.toString()
        val severity = binding.severitySpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()

        // Antes de validar os dados, limpe a mensagem de erro
        binding.severityInputLayout.error = null

        // Validação dos campos obrigatórios
        if (location.isEmpty()) {
            // Exibir mensagem de erro e definir erro no EditText
            Toast.makeText(this, "O campo localização é obrigatório.", Toast.LENGTH_SHORT).show()
            binding.locationAutoComplete.error = "O campo localização é obrigatório."
            return
        }

        if (severity == "Selecione a gravidade") {
            // Mensagem de erro para o severityInputLayout
            binding.severityInputLayout.error = "Selecione a gravidade válida."
            return
        }

        // Fornecer feedback ao usuário
        showToast("Relatório enviado com sucesso!")

        // Desabilitar o botão após enviar o relatório
        binding.reportFloodButton.text = "Relatório enviado"
        binding.reportFloodButton.isEnabled = false
    }
    // Método para exibir Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReportFloodActivity::class.java)
        }
    }
}