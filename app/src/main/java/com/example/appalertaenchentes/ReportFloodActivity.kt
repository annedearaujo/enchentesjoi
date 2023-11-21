package com.example.appalertaenchentes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.appalertaenchentes.database.DatabaseHelper
import com.example.appalertaenchentes.databinding.ActivityReportFloodBinding


class ReportFloodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportFloodBinding
    private lateinit var databaseHelper: DatabaseHelper
    private val pickImageRequest = 1

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Obtenha a URI da imagem selecionada
            val imageUri = result.data?.data

            // Configure a imagem no ImageView
            binding.photosImageView.setImageURI(imageUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportFloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.reportFloodButton.setOnClickListener {
            reportFlood()
        }

        binding.selectImageButton.setOnClickListener {
            // Adicione um Intent para abrir a galeria
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getContent.launch(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK) {
            // Obtenha a URI da imagem selecionada
            val imageUri = data?.data

            // Configure a imagem no ImageView
            binding.photosImageView.setImageURI(imageUri)
        }
    }

    private fun reportFlood() {
        val location = binding.locationEditText.text.toString()
        val severity = binding.severitySpinner.selectedItem.toString()
        val description = binding.descriptionEditText.text.toString()
        val photos = binding.photosImageView.drawable

        // Antes de validar os dados, limpe a mensagem de erro
        binding.severityInputLayout.error = null

        if (location.isEmpty()) {
            Toast.makeText(this, "O campo localização é obrigatório.", Toast.LENGTH_SHORT).show()
            binding.locationEditText.error = "O campo localização é obrigatório."
            return
        }

        if (severity.isEmpty()) {
            // Defina a mensagem de erro para o severityInputLayout
            binding.severityInputLayout.error = "O campo gravidade é obrigatório."
            return
        }

        // Fornecer feedback
        binding.reportFloodButton.text = "Relatório enviado"
        binding.reportFloodButton.isEnabled = false
    }
}