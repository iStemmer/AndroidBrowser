package com.example.androidbrowser

import android.annotation.SuppressLint
import android.content.Intent.*
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val URL = "https://elitmedplace.ru/"

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)

        val context = this
        val webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return try {
                    val intent = parseUri(url, URI_INTENT_SCHEME)
                    startActivity(intent)
                    true
                } catch (ex: android.content.ActivityNotFoundException) {
                    webview.stopLoading()
                    webview.goBack()
                    Toast.makeText(
                        context,
                        "Ошибка! Не удалось открыть приложение",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    false
                }
            }
        }
        webView.webViewClient = webViewClient
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        supportActionBar!!.hide()
        webView.loadUrl(URL)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.ACTION_DOWN == event.action) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}