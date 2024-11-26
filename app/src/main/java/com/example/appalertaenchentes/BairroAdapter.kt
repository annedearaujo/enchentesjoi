package com.example.appalertaenchentes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appalertaenchentes.databinding.ItemBairroBinding

class BairroAdapter(
    private val bairros: List<String>,
    private val alertasPreferences: AlertasPreferences
) : RecyclerView.Adapter<BairroAdapter.BairroViewHolder>() {

    // Mapa para armazenar o estado dos bairros
    private val bairroEstados = mutableMapOf<String, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BairroViewHolder {
        val binding = ItemBairroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BairroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BairroViewHolder, position: Int) {
        val bairro = bairros[position]

        // Recuperar o estado salvo do bairro
        val isBairroMonitorado = bairroEstados[bairro] ?: alertasPreferences.isBairroMonitorado(bairro)
        holder.bind(bairro, isBairroMonitorado)
    }

    override fun getItemCount(): Int = bairros.size

    inner class BairroViewHolder(private val binding: ItemBairroBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bairro: String, isBairroMonitorado: Boolean) {
            // Definir o texto do Switch
            binding.textBairro.text = bairro

            // Remove qualquer listener anterior antes de configurar o novo estado
            binding.switchBairro.setOnCheckedChangeListener(null)

            // Configura o estado inicial do Switch
            binding.switchBairro.isChecked = isBairroMonitorado

            // Configura o novo listener do Switch
            binding.switchBairro.setOnCheckedChangeListener { _, isChecked ->
                // Atualiza o mapa de estados e as preferências
                bairroEstados[bairro] = isChecked
                alertasPreferences.salvarBairro(bairro, isChecked)
                val status = if (isChecked) "ativado" else "desativado"
                Toast.makeText(
                    binding.root.context,
                    "$bairro $status",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}