package com.corteUno.Parcial
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.media.AudioAttributes
import android.view.View
import android.widget.ImageView

class PracticaTambor : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var tamborSoundId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practica_tambor)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()
        tamborSoundId = soundPool.load(this, R.raw.tambor_sound, 1)
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setOnClickListener {
            soundPool.play(tamborSoundId, 1.0f, 1.0f, 0, 0, 1.0f)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}