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
import com.corteUno.Parcial.ui.theme.ParcialletrasInstrumentoTheme

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Accion de boton guardar letra
        var btnGuardarLetra= findViewById<Button>(R.id.btnLetras)
        btnGuardarLetra.setOnClickListener {
            val intent = Intent(this, SaveSong::class.java)
            startActivity(intent)
        }

        //Lista de canciones
        sharedPreferencesManager = SharedPreferencesManager(this)
        var list= findViewById<ListView>(R.id.listCanciones)
        val cancionesGuardadas = sharedPreferencesManager.obtenerNombresCancionesGuardadas()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cancionesGuardadas)
        list.adapter = adapter
        list.setOnItemClickListener { _, _, position, _ ->
            val cancion = cancionesGuardadas[position]
            mostrarDetalleCancion(cancion)
        }

        //Boton buscar
        var btnBuscar= findViewById<Button>(R.id.btnBuscar)
        btnBuscar.setOnClickListener {
            var txtBuscar= findViewById<EditText>(R.id.txtBuscar)
            var nC= txtBuscar.text.toString()
              buscarCancionPorNombre(nC)
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

    private fun buscarCancionPorNombre(nombreCancion: String) {
        val todasLasCanciones = sharedPreferencesManager.obtenerNombresCancionesGuardadas()
        val cancionesFiltradas = todasLasCanciones.filter { it.contains(nombreCancion, ignoreCase = true) }

        // Actualizar el adaptador del ListView con las canciones filtradas
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cancionesFiltradas)
        val list= findViewById<ListView>(R.id.listCanciones)
        list.adapter = adapter
    }


}

