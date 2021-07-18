package com.example.youtubeplayertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.youtubeplayertest.databinding.ActivityMainBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerAndroidxFragment
import splitties.toast.toast

class MainActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    companion object{
        const val TAG = "YouTubePlayer"
        const val YOUTUBE_VIDEO_ID = "CuklIb9d3fI"
    }

    lateinit var mainActivityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        var youtubefragment = supportFragmentManager.findFragmentById(R.id.youtube_fragment)
            as YouTubePlayerAndroidxFragment

        youtubefragment.initialize(javaClass.simpleName, this)

    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: youTubePlayer is ${youTubePlayer?.javaClass}")
        toast("Initialized Youtube Player successfully")

        youTubePlayer?.setPlayerStateChangeListener(object: YouTubePlayer.PlayerStateChangeListener {
            override fun onAdStarted() {
                Toast.makeText(this@MainActivity, "Click Ad now, make the video creator rich!", Toast.LENGTH_SHORT).show()
            }

            override fun onLoading() {
            }

            override fun onVideoStarted() {
                Toast.makeText(this@MainActivity, "Video has started", Toast.LENGTH_SHORT).show()
            }

            override fun onLoaded(p0: String?) {
                youTubePlayer.play() // 동영상을 불러오면 바로 실행
            }

            override fun onVideoEnded() {
                Toast.makeText(this@MainActivity, "Congratulations! You've completed another video.", Toast.LENGTH_SHORT).show()
            }

            override fun onError(p0: YouTubePlayer.ErrorReason?) {
            }
        })
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            youTubePlayer?.cueVideo(YOUTUBE_VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 0

        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@MainActivity, "Good, video is playing ok", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@MainActivity, "Video has stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@MainActivity, "Video has paused", Toast.LENGTH_SHORT).show()
        }
    }
}