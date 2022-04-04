package com.example.android.accessibility.runapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlinx.android.synthetic.main.activity_timer.*


class TimerActivity : AppCompatActivity() {
    var time = 0
    var started = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.accessibility.runapp.R.layout.activity_timer)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            timer_label.text = getString(R.string.timer_label_long)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_timer)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            timer_label.text = getString(R.string.timer_label_long)
        }
    }
    fun View.goHome() {
        val intent = Intent(this@TimerActivity, MainActivity::class.java)
        startActivity(intent)
    }
    fun View.startTimer() {
        runTimer()
    }
    fun View.reset() {
        time = 0
        started = false
        updateText()
    }
    private fun pause() {
        started = false
        start_label.text = resources.getString(R.string.timer_start)
        Start_Icon.setImageResource(R.drawable.ic_start_button)
    }
    fun updateText() {
        val minutes: Int = time / 360000
        val secs: Int = time % 6000 / 100
        val milli: Int = time % 100
        var timeString: String = java.lang.String
            .format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                minutes, secs, milli
            )
        timer_text.text = timeString
    }
    private fun runTimer() {
        if(started)   {
            return pause()
        }
        start_label.text = resources.getString(R.string.timer_pause)
        Start_Icon.setImageResource(R.drawable.ic_pause)
        started = true
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                updateText()
                if (started) {
                    time+=1
                    handler.postDelayed(this, 10)
                }
            }
        })
    }
}