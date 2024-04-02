package com.corteUno.Parcial
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.corteUno.Parcial.Logica.SharedPreferencesManager

class DetalleCancionActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_cancion)
        var nombre= findViewById<TextView>(R.id.txtNombreCancion)
        var letra= findViewById<TextView>(R.id.txtLetraCancion)
        var btnDelete= findViewById<Button>(R.id.btnDelete)

        var nombreC= intent.getStringExtra("titulo")
        var letraC= intent.getStringExtra("letra")
        sharedPreferencesManager = SharedPreferencesManager(this)


        nombre.setText(nombreC)
        letra.setText(letraC)

        btnDelete.setOnClickListener {
            sharedPreferencesManager.eliminarCancion(nombreC)
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}