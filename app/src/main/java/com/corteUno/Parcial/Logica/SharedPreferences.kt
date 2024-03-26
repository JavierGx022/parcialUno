package com.corteUno.Parcial.Logica

import android.content.Context
import android.widget.Toast


class SharedPreferencesManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)

    fun guardarCancion(cancion: Cancion) {
        val numCanciones = sharedPreferences.getInt("num_canciones", 0)

        val editor = sharedPreferences.edit()
        editor.putString("titulo_$numCanciones", cancion.titulo)
        editor.putString("letra_$numCanciones", cancion.letra)
        editor.putInt("num_canciones", numCanciones + 1)
        editor.apply()

        // Mostrar un Toast para confirmar que la canción se ha guardado
        Toast.makeText(context, "Canción guardada correctamente", Toast.LENGTH_SHORT).show()
    }

    fun obtenerNombresCancionesGuardadas(): List<String> {
        val nombresCanciones = mutableListOf<String>()
        val numCanciones = sharedPreferences.getInt("num_canciones", 0)

        for (i in 0 until numCanciones) {
            val titulo = sharedPreferences.getString("titulo_$i", null)
            titulo?.let {
                nombresCanciones.add(it)
            }
        }

        return nombresCanciones
    }

    fun obtenerLetraCancion(nombreCancion: String): String? {
        val numCanciones = sharedPreferences.getInt("num_canciones", 0)

        for (i in 0 until numCanciones) {
            val titulo = sharedPreferences.getString("titulo_$i", null)
            val letra = sharedPreferences.getString("letra_$i", null)
            if (titulo == nombreCancion) {
                return letra
            }
        }

        return null  // Devolver null si la canción no se encuentra
    }

    fun eliminarCancion(nombreCancion: String?) {
        val numCanciones = sharedPreferences.getInt("num_canciones", 0)
        val editor = sharedPreferences.edit()

        for (i in 0 until numCanciones) {
            val titulo = sharedPreferences.getString("titulo_$i", null)
            if (titulo == nombreCancion) {
                // Eliminar la canción de SharedPreferences
                editor.remove("titulo_$i")
                editor.remove("letra_$i")
                // Decrementar el contador de canciones
                editor.putInt("num_canciones", numCanciones - 1)
                editor.apply()
                // Mostrar un Toast para confirmar que la canción se ha eliminado
                Toast.makeText(context, "Canción eliminada correctamente", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Mostrar un Toast si la canción no se encontró
        Toast.makeText(context, "La canción no se encontró: $nombreCancion", Toast.LENGTH_SHORT).show()
    }


}
