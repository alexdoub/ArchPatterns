package com.doobie.archpatterns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.doobie.archpatterns.paramsandsavedstate.ParamsAndSavedStateActivity
import com.doobie.archpatterns.savedstate.SavedStateActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.lifecycle_activity).setOnClickListener {
            startActivity(Intent(this, ParamsAndSavedStateActivity::class.java))
        }

        findViewById<Button>(R.id.simple_activity).setOnClickListener {
            startActivity(Intent(this, SavedStateActivity::class.java))
        }
    }
}
