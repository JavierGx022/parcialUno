package com.corteUno.Parcial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.corteUno.Parcial.Logica.Cancion
import com.corteUno.Parcial.Logica.SharedPreferencesManager

class SaveSong : AppCompatActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_save_song)


        // Inicializar SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(this)

        // Obtener referencias de EditText
        var editTextTitulo = findViewById<EditText>(R.id.txtNombre)
        var editTextLetra = findViewById<EditText>(R.id.txtLetra)

        val botonGuardar= findViewById<Button>(R.id.btnGuardar)

        botonGuardar.setOnClickListener {
            // Obtener el texto de los EditText
            val titulo = editTextTitulo.text.toString()
            val letra = editTextLetra.text.toString()

            // Crear una instancia de Cancion
            val cancion = Cancion(titulo, letra)

            // Guardar la canción en SharedPreferences
            sharedPreferencesManager.guardarCancion(cancion)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}