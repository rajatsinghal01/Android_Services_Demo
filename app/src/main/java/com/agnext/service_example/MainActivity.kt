package com.agnext.service_example

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SeekBar_Interface{
    lateinit var btnstart : Button
    lateinit var btnstop : Button
    companion object{
        @JvmStatic
        lateinit var seekBar: SeekBar
    }
    lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnstart = findViewById(R.id.btn_start)
        btnstop = findViewById(R.id.btn_stop)
        seekBar = findViewById(R.id.seekbar)


        btnstart.setOnClickListener(View.OnClickListener {
            seekBar.progress=0
            startService(Intent(applicationContext,MusicService::class.java))

        })


        btnstop.setOnClickListener(View.OnClickListener {
            stopService(Intent(applicationContext,MusicService::class.java))
            seekBar.progress=0;

        })

    }
    override fun updateSeekBar(mediaPlayer: MediaPlayer) {
        seekBar.progress=0
        seekBar.max=mediaPlayer.duration
        var handler: Handler = Handler()
        runnable = Runnable{
            seekBar.progress=mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

}