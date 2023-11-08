package com.example.appalertaenchentes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appalertaenchentes.database.DatabaseHelper
import com.example.appalertaenchentes.databinding.ActivityReportFloodBinding

class ReportFloodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportFloodBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportFloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.reportFloodButton.setOnClickListener {
            reportFlood()
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