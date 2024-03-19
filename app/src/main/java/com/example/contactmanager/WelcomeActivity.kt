package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contactmanager.databinding.ActivityAddContactBinding
import com.example.contactmanager.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val i = intent
        val username = i.getStringExtra("Username")

        binding.btnAddContact.setOnClickListener {
            val intent = Intent(applicationContext , AddContactActivity::class.java)
            intent.putExtra("Username" , username)
            startActivity(intent)
        }

        binding.btnFindContact.setOnClickListener {
            val intent = Intent(applicationContext , FindContactActivity::class.java)
            intent.putExtra("Username" , username)
            startActivity(intent)
        }


    }
}