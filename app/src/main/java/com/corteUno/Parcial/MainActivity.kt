package com.corteUno.Parcial

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.corteUno.Parcial.Logica.Cancion
import com.corteUno.Parcial.Logica.SharedPreferencesManager

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var cancionesFiltradas: List<String> // Agregar una variable para almacenar las canciones filtradas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inicializar SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(this)

        // Obtener todas las canciones guardadas
        val todasLasCanciones = sharedPreferencesManager.obtenerNombresCancionesGuardadas()

        // Inicializar ListView y Adapter con todas las canciones
        val list = findViewById<ListView>(R.id.listCanciones)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todasLasCanciones)
        list.adapter = adapter

        // Definir acción de clic en la lista
        list.setOnItemClickListener { _, _, position, _ ->
            val nombreCancion = cancionesFiltradas[position] // Utilizar la posición en la lista filtrada
            mostrarDetalleCancion(nombreCancion)
        }

        // Configurar el botón de búsqueda
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            val txtBuscar = findViewById<EditText>(R.id.txtBuscar)
            val nC = txtBuscar.text.toString()
            buscarCancionPorNombre(nC, todasLasCanciones)
        }
    }

    private fun mostrarDetalleCancion(nombreCancion: String) {
        val letraCancion = sharedPreferencesManager.obtenerLetraCancion(nombreCancion)

        if (letraCancion != null) {
            val intent = Intent(this, DetalleCancionActivity::class.java).apply {
                putExtra("titulo", nombreCancion)
                putExtra("letra", letraCancion)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Letra de la canción no encontrada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscarCancionPorNombre(nombreCancion: String, todasLasCanciones: List<String>) {
        val letra = sharedPreferencesManager.obtenerLetraCancion(nombreCancion)
        cancionesFiltradas = todasLasCanciones.filter { it.contains(nombreCancion, ignoreCase = true) }

        // Actualizar el adaptador del ListView con las canciones filtradas
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cancionesFiltradas)
        val list = findViewById<ListView>(R.id.listCanciones)
        list.adapter = adapter
    }
}


