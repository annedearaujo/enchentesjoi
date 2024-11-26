package com.example.appalertaenchentes

import com.example.appalertaenchentes.database.DatabaseHelper
import com.example.appalertaenchentes.databinding.ActivityReportFloodBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class ReportFloodActivityTest {

    // Mock da classe DatabaseHelper
    @Mock
    private lateinit var mockDatabaseHelper: DatabaseHelper

    // Classe a ser testada
    private lateinit var reportFloodActivity: ReportFloodActivity

    @Before
    fun setUp() {
        // Inicializa a instância da classe a ser testada e configura o mock do DatabaseHelper
        reportFloodActivity = ReportFloodActivity()
       // reportFloodActivity.databaseHelper = mockDatabaseHelper
    }

    @Test
    fun testGetFormData() {
        // Configuração
        val mockBinding = createMockBinding()

        // Configurar valores esperados
        mockBinding.locationAutoComplete.setText("Location")
        mockBinding.severitySpinner.setSelection(1)
        mockBinding.descriptionEditText.setText("Description")

        reportFloodActivity.binding = mockBinding

        // Execução
        val formData = reportFloodActivity.getFormData()

        // Verificação
        assertEquals("Location", formData.first)
        assertEquals("Baixa", formData.second)
        assertEquals("Description", formData.third)
    }

    @Test
    fun testReportFlood_Successful() {
        // Configuração
        val mockBinding = createMockBinding()
        reportFloodActivity.binding = mockBinding

        // Mock do método showToast
        val mockReportFloodActivity = Mockito.spy(reportFloodActivity)
        Mockito.doNothing().`when`(mockReportFloodActivity).showToast(Mockito.anyString())

        // Execução
        mockReportFloodActivity.reportFlood()

        // Verificação
        Mockito.verify(mockReportFloodActivity, Mockito.times(1)).showToast("Relatório enviado com sucesso!")
        assertFalse(mockBinding.reportFloodButton.isEnabled)
        assertEquals("Relatório enviado", mockBinding.reportFloodButton.text)
    }

    @Test
    fun testReportFlood_InvalidData() {
        // Configuração
        val mockBinding = createMockBinding()
        reportFloodActivity.binding = mockBinding

        // Mock do método showToast
        val mockReportFloodActivity = Mockito.spy(reportFloodActivity)
        Mockito.doNothing().`when`(mockReportFloodActivity).showToast(Mockito.anyString())

        // Execução
        mockReportFloodActivity.reportFlood()

        // Verificação
        Mockito.verify(mockReportFloodActivity, Mockito.never()).showToast(Mockito.anyString())
        assertTrue(mockBinding.reportFloodButton.isEnabled)
        assertEquals("Enviar alerta", mockBinding.reportFloodButton.text)
    }

    private fun createMockBinding(): ActivityReportFloodBinding {
        // Método auxiliar para criar um mock de ActivityReportFloodBinding
        return Mockito.mock(ActivityReportFloodBinding::class.java)
    }
}