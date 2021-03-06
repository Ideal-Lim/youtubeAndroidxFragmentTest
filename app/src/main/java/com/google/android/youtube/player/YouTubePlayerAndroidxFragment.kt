package com.google.android.youtube.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.internal.ab
// source url: https://gist.github.com/medyo/f226b967213c3b8ec6f6bebb5338a492
class YouTubePlayerAndroidxFragment : Fragment(), YouTubePlayer.Provider {
    private var bundle: Bundle? = null
    private var youTubePlayerView: YouTubePlayerView? = null
    private var developerKey: String? = null
    private var onInitializedListener: YouTubePlayer.OnInitializedListener? = null

    // 실행 시 콜백함수
    override fun initialize(developerKey: String, onInitializedListener: YouTubePlayer.OnInitializedListener?) {
        //nullable check
        this.developerKey = ab.a(developerKey, "Developer key cannot be null or empty")
        this.onInitializedListener = onInitializedListener
        // YoutubePlayerView()를 실행
        initYoutubePlayerView()
    }

    private fun initYoutubePlayerView() {
        if (youTubePlayerView != null && onInitializedListener != null) {
            youTubePlayerView?.a(false)
            youTubePlayerView?.a(activity, this, developerKey, onInitializedListener, bundle)
            bundle = null
            onInitializedListener = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = savedInstanceState?.getBundle(KEY_PLAYER_VIEW_STATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        youTubePlayerView = YouTubePlayerView(activity, null, 0, OnYoutubeInitializedListener())
        initYoutubePlayerView()
        return youTubePlayerView
    }

    override fun onStart() {
        super.onStart()
        youTubePlayerView?.a()
    }

    override fun onResume() {
        super.onResume()
        youTubePlayerView?.b()
    }

    override fun onPause() {
        youTubePlayerView?.c()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = youTubePlayerView?.e() ?: bundle
        outState.putBundle(KEY_PLAYER_VIEW_STATE, bundle)
    }

    override fun onStop() {
        youTubePlayerView?.d()
        super.onStop()
    }

    override fun onDestroyView() {
        youTubePlayerView?.c(activity?.isFinishing ?: false)
        youTubePlayerView = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        youTubePlayerView?.b(activity?.isFinishing ?: true)
        super.onDestroy()
    }

    inner class OnYoutubeInitializedListener : YouTubePlayerView.b {

        override fun a(var1: YouTubePlayerView, developerKey: String, var3: YouTubePlayer.OnInitializedListener) {
            initialize(developerKey, onInitializedListener)
        }

        override fun a(var1: YouTubePlayerView) {}
    }

    companion object {
        private const val KEY_PLAYER_VIEW_STATE =
            "YouTubePlayerAndroidxFragment.KEY_PLAYER_VIEW_STATE"
    }
}