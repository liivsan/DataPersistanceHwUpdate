package edu.farmingdale.alrajab.bcs421

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SharedPrefActivity : AppCompatActivity() {
    // TODO 02:SP Make an activity that accept the User's first and last name and save/read/update
    //  the shared preference

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var FirstName: EditText
    private lateinit var LastName: EditText
    private lateinit var save_btn: Button
    private lateinit var go_back_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharedpref)

        sharedPreferences = getSharedPreferences("myFile", Context.MODE_PRIVATE)

        FirstName = findViewById(R.id.FirstName)
        LastName = findViewById(R.id.LastName)
        save_btn = findViewById(R.id.save_btn)
        go_back_btn = findViewById(R.id.go_back_btn)

        save_btn.setOnClickListener {
            saveUserData() // update fun unnecessary
        }

        loadUserData()

        go_back_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    } // end OnCreate

    private fun loadUserData() {
        val firstName = sharedPreferences.getString("first_name", "")
        val lastName = sharedPreferences.getString("last_name", "")

        FirstName.setText(firstName)
        LastName.setText(lastName)
    }

    private fun saveUserData() {
        val firstName = FirstName.text.toString()
        val lastName = LastName.text.toString()

        val editor = sharedPreferences.edit()
        editor.putString("first_name", firstName)
        editor.putString("last_name", lastName)
        editor.apply()
    }

}// end class