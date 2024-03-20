package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivityForgotPasswordBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Forgot_Password_Activity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnReset.setOnClickListener {
            if(binding.editTextUsername.text.isNullOrEmpty()||binding.editTextPassword1.text.isNullOrEmpty()||binding.editTextPassword2.text.isNullOrEmpty()){
                Toast.makeText(applicationContext , "fill empty field" , Toast.LENGTH_SHORT).show()
            }else {
                if (binding.editTextPassword1.text.toString()==binding.editTextPassword2.text.toString()){
                    ResetPassword()
                }else {
                    Toast.makeText(applicationContext , "Password Mismatch" , Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun ResetPassword() {
        database = FirebaseDatabase.getInstance().getReference("User")

        database.child(binding.editTextUsername.text.toString()).get().addOnSuccessListener {
            if(it.exists()){
                val email = it.child("email").value.toString()
                val password = it.child("password").value.toString()
                val username = it.child("username").value.toString()

                if(binding.editTextPassword1.text.toString()==password){
                    Toast.makeText(applicationContext , "Choose New Password" , Toast.LENGTH_SHORT).show()
                }else {
                    setData(email , username , binding.editTextPassword1.text.toString())
                }

            }else {
                Toast.makeText(applicationContext , "Username not Exist" , Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext , "Failed" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData(email: String, username: String, password: String) {
        val user = User(username , email , password)
        database.child(user.username).setValue(user)
        Toast.makeText(applicationContext , "Processing" , Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext , "Successfully Reset" , Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            val intent = Intent(applicationContext , LogInActivity::class.java)
            startActivity(intent)
            finish()
        } , 2000)

    }

}