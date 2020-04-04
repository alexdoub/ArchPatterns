package com.doobie.archpatterns

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.doobie.archpatterns.imagelistanddetails.CardListActivity
import com.doobie.archpatterns.imagelistanddetails.withdb.CardListWithDBActivity
import com.doobie.archpatterns.paramsandsavedstate.ParamsAndSavedStateActivity
import com.doobie.archpatterns.savedstate.SavedStateActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.params_and_saved_state_btn).setOnClickListener {
            startActivity(Intent(this, ParamsAndSavedStateActivity::class.java))
        }

        findViewById<Button>(R.id.saved_state_btn).setOnClickListener {
            startActivity(Intent(this, SavedStateActivity::class.java))
        }

        findViewById<Button>(R.id.list_view_btn).setOnClickListener {
            startActivity(Intent(this, CardListActivity::class.java))
        }

        findViewById<Button>(R.id.list_view_with_db_btn).setOnClickListener {
            startActivity(Intent(this, CardListWithDBActivity::class.java))
        }
    }
}
