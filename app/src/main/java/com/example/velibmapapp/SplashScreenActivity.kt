package com.example.velibmapapp

    import android.content.Intent
    import android.os.Bundle
    import android.os.Handler
    import android.view.WindowManager
    import androidx.appcompat.app.AppCompatActivity

    @Suppress("DEPRECATION")
    class SplashScreenActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash_screen)
            supportActionBar?.hide()
            // This is used to hide the status bar and make
            // the splash screen as a full screen activity.
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            // we used the postDelayed(Runnable, time) method
            // to send a message with a delayed time.
            Handler().postDelayed({
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000) // 2000 is the delayed time in milliseconds.
        }
    }