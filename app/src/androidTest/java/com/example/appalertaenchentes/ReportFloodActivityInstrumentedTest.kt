package com.example.appalertaenchentes

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReportFloodActivityInstrumentedTest {

    // Regra para inicializar automaticamente a atividade antes de cada teste
    @get:Rule
    val activityRule = ActivityScenarioRule(ReportFloodActivity::class.java)

    @Test
    fun testSelectImage() {
        // Testa a seleção de imagem
        // Executa a ação de clique no botão de seleção de imagem
        Espresso.onView(withId(R.id.selectImageButton)).perform(click())
    }
}