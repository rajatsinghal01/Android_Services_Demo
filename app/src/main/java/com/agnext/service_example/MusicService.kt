package com.agnext.service_example

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.widget.SeekBar
import java.util.*


class MusicService : Service() {
   lateinit var seekBar: SeekBar
    lateinit var runnable: Runnable
  lateinit var mediaPlayer: MediaPlayer


    override fun onCreate() {

        mediaPlayer = MediaPlayer.create(this,R.raw.music)
        mediaPlayer.isLooping=true
        seekBar= MainActivity.seekBar
        seekBar.progress=0

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mediaPlayer.start()
        updateSeekBar(mediaPlayer)
        return START_NOT_STICKY
    }

    override fun onDestroy() {

        mediaPlayer.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
     fun updateSeekBar(mediaPlayer: MediaPlayer) {

        seekBar.max=mediaPlayer.duration
         Timer().scheduleAtFixedRate(object : TimerTask() {
             override fun run() {
                 seekBar.progress=mediaPlayer.currentPosition
             }
         }, 0, 900)
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