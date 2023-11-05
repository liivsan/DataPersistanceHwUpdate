package edu.farmingdale.alrajab.bcs421.database

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import edu.farmingdale.alrajab.bcs421.R
import edu.farmingdale.alrajab.bcs421.databinding.ActivityDatabaseBinding
import kotlin.random.Random

class DatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabaseBinding

    private lateinit var dbHelper: NameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Bind first/Last name
        binding.dbFirstName.text
        binding.dbLastName.text

        //Bind old first/Last name
        binding.dbOldFirstName.text
        binding.dbOldLastName.text

        dbHelper= NameRepository.getInstance(this)

        binding.readData.setOnClickListener { readData() }
        binding.writeData.setOnClickListener { writeData() }
        binding.updateData.setOnClickListener { updateUser() }
        binding.syncData.setOnClickListener {syncFileToDb()}
    }

    private fun writeData() {
        // Check if user already exists
        val existingUser = dbHelper.getUserByName(binding.dbFirstName.text.toString(), binding.dbLastName.text.toString())

        // If does not exist, add user
        if (existingUser == null) {
            dbHelper.addUser(User(binding.dbFirstName.text.toString(), binding.dbLastName.text.toString()))
        }
    }

    private fun readData() {
        dbHelper.getAll().forEach { Log.d("Data",it.firstName+" , "+ it.lastName) }

    }
    // TODO 04:DB Update an existing name with a new one
    private fun updateUser() {
        // Check if user already exists
        val existingUser = dbHelper.getUserByName(binding.dbOldFirstName.text.toString(), binding.dbOldLastName.text.toString())

        // If user we need to replace exist -> replace (delete and add)
        if (existingUser != null) {
            dbHelper.deleteUser(existingUser)
            dbHelper.addUser(User(binding.dbFirstName.text.toString(), binding.dbLastName.text.toString()))
        }

    }

    fun syncFileToDb() {
        // TODO 05:DB Read from the files and write to the DB
        // 1. Read from the file (look at readFromInternalFile from FileActivity)
        val inputStream = openFileInput("myfile")
        val reader = inputStream.bufferedReader()
        val stringBuilder = StringBuilder()
        val lineSeparator = System.getProperty("line.separator")

        // Append each task to stringBuilder
        reader.forEachLine { stringBuilder.append(it).append(lineSeparator) }

    // 2. For each first name and last name, write to db with dbHelper.addUser()
        dbHelper.addUser(User(binding.dbFirstName.text.toString(), binding.dbLastName.text.toString()))
        Log.d("Sync", "Added user: ${binding.dbFirstName.text.toString()}, ${binding.dbLastName.text.toString()} to the database")
    }
} //end class