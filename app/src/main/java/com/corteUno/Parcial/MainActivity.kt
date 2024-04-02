package com.corteUno.Parcial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

import com.corteUno.Parcial.Logica.SharedPreferencesManager


class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var cancionesFiltradas: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Accion de boton guardar letra
        var btnGuardarLetra= findViewById<Button>(R.id.btnLetras)
        btnGuardarLetra.setOnClickListener {
            val intent = Intent(this, SaveSong::class.java)
            startActivity(intent)
        }
        //Accion de boton practica tambor
        var btnPracticaTambor= findViewById<Button>(R.id.btnPTambor)
        btnPracticaTambor.setOnClickListener {
            val intent = Intent(this, PracticaTambor::class.java)
            startActivity(intent)
        }

        //Accion de boton practica maraca
        var btnPracticaMaraca= findViewById<Button>(R.id.btnPMaraca)
        btnPracticaMaraca.setOnClickListener {
            val intent = Intent(this, PracticaTambor::class.java)
            startActivity(intent)
        }

        sharedPreferencesManager = SharedPreferencesManager(this)
        cancionesFiltradas = sharedPreferencesManager.obtenerNombresCancionesGuardadas()
        //Lista de canciones
        var list = findViewById<ListView>(R.id.listCanciones)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cancionesFiltradas)
        list.adapter = adapter
        list.setOnItemClickListener { _, _, position, _ ->
            val cancion = cancionesFiltradas[position]
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
        cancionesFiltradas = todasLasCanciones.filter { it.contains(nombreCancion, ignoreCase = true) }

        // Actualizar el adaptador del ListView con las canciones filtradas
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cancionesFiltradas)
        val list = findViewById<ListView>(R.id.listCanciones)
        list.adapter = adapter
    }
}

