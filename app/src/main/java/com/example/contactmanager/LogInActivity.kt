package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivityLogInBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LogInActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivityLogInBinding

    private lateinit var editTextUsername : EditText
    private lateinit var editTextPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextUsername = binding.editTextUsername
        editTextPassword = binding.editTextPassword

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(applicationContext , SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogIn.setOnClickListener {
            if(binding.editTextUsername.text.isNullOrEmpty()||binding.editTextPassword.text.isNullOrEmpty()){
                Toast.makeText(applicationContext , "fill empty field" , Toast.LENGTH_SHORT).show()
            }else{
                authenticateCredentials(binding.editTextUsername.text.toString() , binding.editTextPassword.text.toString())
            }
        }

        binding.txtForgotPassword.setOnClickListener {
            val intent = Intent(applicationContext , Forgot_Password_Activity::class.java)
            startActivity(intent)
        }


    }

    private fun authenticateCredentials(username: String, password: String) {
        database = FirebaseDatabase.getInstance().getReference("User")

        database.child(username).get().addOnSuccessListener {
            if(it.exists()){
                val passwordFD = it.child("password").value.toString()
                if(passwordFD==password){
                    val intent = Intent(applicationContext , WelcomeActivity::class.java)
                    intent.putExtra("Username", username)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext , "Password Not match" , Toast.LENGTH_SHORT).show()
                    editTextPassword.text.clear()
                }
            }else {
                Toast.makeText(applicationContext , "Username not Exist" , Toast.LENGTH_SHORT).show()
                editTextUsername.text.clear()
            }
        }
    }


}

