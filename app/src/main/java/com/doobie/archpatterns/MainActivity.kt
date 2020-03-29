package com.doobie.archpatterns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.doobie.archpatterns.lifecycleactivity.LifecycleActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.lifecycle_activity).setOnClickListener {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }
    }
}
