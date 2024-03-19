package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var database: DatabaseReference

    private lateinit var editTextUsername : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var editTextEmail : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextEmail = binding.editTextEmail
        editTextUsername = binding.editTextUsername
        editTextPassword = binding.editTextPassword

        binding.btnLogIn.setOnClickListener {
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        }


        binding.btnSignUp.setOnClickListener {
            if (binding.editTextUsername.text.isNullOrEmpty() || binding.editTextEmail.text.isNullOrEmpty() || binding.editTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "fill empty field", Toast.LENGTH_SHORT).show()
            } else {
                addInfoToDatabase(binding.editTextUsername.text.toString() , binding.editTextEmail.text.toString() , binding.editTextPassword.text.toString())
            }
        }

    }


    private fun addInfoToDatabase ( username : String , email:String , password : String ) {

        database = FirebaseDatabase.getInstance().getReference("User")

        val user = User(username , email , password)

        database.child(username).get().addOnSuccessListener {
            if(it.exists()){
                Toast.makeText(applicationContext , "Already exist" , Toast.LENGTH_SHORT).show()
                editTextUsername.text.clear()
            }else {
                database.child(username).setValue(user).addOnSuccessListener {
                    val intent = Intent(applicationContext , WelcomeActivity::class.java)
                    intent.putExtra("Username", username)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(applicationContext , "Failed", Toast.LENGTH_SHORT).show()
                }.addOnCanceledListener {
                    Toast.makeText(applicationContext , "Error 404" , Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext , "failed" , Toast.LENGTH_SHORT).show()
        }




    }


}