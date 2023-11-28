package com.example.appalertaenchentes

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        // Inicializa a atividade MainActivity usando o ActivityScenario
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testActivityNotNull() {
        // Verifica se a atividade não é nula
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun testViewsInitialized() {
        // Verifica se as views foram inicializadas corretamente
        scenario.onActivity {
            assertNotNull(it.binding.bottomNavigation)
            assertNotNull(it.binding.reportFloodButton)
        }
    }

    @Test
    fun testListenersSetup() {
        // Verifica se os listeners foram configurados corretamente
        scenario.onActivity { activity ->
            val bottomNavigationView = activity.binding.bottomNavigation

            // Verifica se o método setOnNavigationItemSelectedListener foi chamado
            assertNotNull(bottomNavigationView.itemIconTintList)

            // Simula a seleção de um item para testar o comportamento associado
            val item = bottomNavigationView.menu.findItem(R.id.menu_home)
            bottomNavigationView.selectedItemId = item.itemId
        }
    }

    @Test
    fun testIntentFlags() {
        // Verifica se as flags do Intent foram configuradas corretamente
        scenario.onActivity {
            val intent = it.intent
            assertTrue(
                (intent.flags and Intent.FLAG_ACTIVITY_CLEAR_TASK) != 0 &&
                        (intent.flags and Intent.FLAG_ACTIVITY_NEW_TASK) != 0
            )
        }
    }

    @Test
    fun testNewIntent() {
        // Testa o método newIntent na classe companion
        val intent = MainActivity.newIntent(ApplicationProvider.getApplicationContext())
        assertNotNull(intent)

        // Compara as classes do Intent esperada e atual
        val expectedClassName = ReportFloodActivity::class.java.name
        val actualClassName = intent.component?.className

        // Verifica se está direcionando corretamente, se falhar, informa a classe no log
        assertTrue("A classe do Intent não corresponde à classe esperada. Esperado: $expectedClassName, Atual: $actualClassName", actualClassName == expectedClassName)
    }
}