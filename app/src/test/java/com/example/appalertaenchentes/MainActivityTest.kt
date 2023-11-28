package com.example.appalertaenchentes

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appalertaenchentes.databinding.ActivityMainBinding
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class MainActivityTest {

    @Test
    fun testActivityCreation() {
        // Testa a criação da atividade usando o Robolectric
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        assertNotNull(activity)
    }

    @Test
    fun testViewsInitialized() {
        // Testa se as views foram inicializadas corretamente
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        activity.binding = ActivityMainBinding.inflate(activity.layoutInflater)

        assertNotNull(activity.binding.bottomNavigation)
        assertNotNull(activity.binding.reportFloodButton)
        // Adicione mais verificações específicas para outras views, se necessário
    }

    @Test
    fun testReportFloodButtonClick() {
        // Testa o clique no botão de relatar enchente
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        activity.binding = ActivityMainBinding.inflate(activity.layoutInflater)

        activity.binding.reportFloodButton.performClick()
        // Certifica-se de que o clique no botão de relatar enchente não resulta em exceções
    }

    @Test
    fun testHandleActivityNotFoundException() {
        // Testa o tratamento de ActivityNotFoundException
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        activity.binding = ActivityMainBinding.inflate(activity.layoutInflater)

        val exception = mock(ActivityNotFoundException::class.java)
        activity.handleActivityNotFoundException(exception)
        // Verifica se não há exceção ao lidar com a ActivityNotFoundException
    }

    @Test
    fun testHandleException() {
        // Testa o tratamento genérico de exceções
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        activity.binding = ActivityMainBinding.inflate(activity.layoutInflater)

        val exception = mock(Exception::class.java)
        activity.handleException(exception)
        // Verifica se não há exceção ao lidar com a Exception
    }

    @Test
    fun testNewIntent() {
        // Testa o método companion newIntent
        val context = mock(Context::class.java)
        val intent = MainActivity.newIntent(context)

        assertNotNull(intent)
    }
}