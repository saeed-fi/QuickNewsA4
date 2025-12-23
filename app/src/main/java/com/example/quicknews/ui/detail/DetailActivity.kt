package com.example.quicknews.ui.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.quicknews.data.model.Post
import com.example.quicknews.databinding.ActivityDetailBinding
import com.example.quicknews.utils.PreferencesManager
import com.example.quicknews.utils.ThemeManager

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefsManager = PreferencesManager(this)
        ThemeManager.applyTheme(this, prefsManager.theme)

        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        post = intent.getParcelableExtra("POST")

        post?.let { displayPost(it) }

        setupWebView()
    }

    private fun displayPost(post: Post) {
        binding.apply {
            tvTitle.text = post.title
            tvBody.text = post.body
            toolbar.title = "Article #${post.id}"
        }
    }

    private fun setupWebView() {
        binding.btnWebView.setOnClickListener {
            binding.webView.visibility = View.VISIBLE
            binding.webProgress.visibility = View.VISIBLE

            binding.webView.apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                webViewClient = WebViewClient()

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        binding.webProgress.progress = newProgress
                        if (newProgress == 100) {
                            binding.webProgress.visibility = View.GONE
                        }
                    }
                }

                val url = "https://jsonplaceholder.typicode.com/posts/${post?.id}"
                loadUrl(url)
            }

            binding.btnWebView.isEnabled = false
            binding.btnWebView.text = "Loading..."
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        post?.let {
            outState.putParcelable("POST", it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        post = savedInstanceState.getParcelable("POST")
        post?.let { displayPost(it) }
    }
}