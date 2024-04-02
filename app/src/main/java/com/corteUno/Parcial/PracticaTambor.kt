package com.corteUno.Parcial
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.media.AudioAttributes
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.graphics.Color


class PracticaTambor : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var tamborSoundId: Int = 0
    private val coloresDeFondo = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF")
    fun animateTitleColor(titleTextView: TextView) {
        val colorAnimation = ObjectAnimator.ofInt(titleTextView, "textColor", ContextCompat.getColor(this, R.color.color1), ContextCompat.getColor(this, R.color.color2),ContextCompat.getColor(this, R.color.color3) )
        colorAnimation.duration = 1500
        colorAnimation.setEvaluator(ArgbEvaluator())
        colorAnimation.repeatCount = ObjectAnimator.INFINITE
        colorAnimation.repeatMode = ObjectAnimator.REVERSE
        colorAnimation.start()

    }



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
            val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
            imageView.startAnimation(scaleAnimation)

            soundPool.play(tamborSoundId, 1.0f, 1.0f, 0, 0, 1.0f)
            val titleTextView = findViewById<TextView>(R.id.textView2)
            animateTitleColor(titleTextView)
            val subtitleTextView = findViewById<TextView>(R.id.textView4)
            animateTitleColor(subtitleTextView)


        }

    fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
        }}