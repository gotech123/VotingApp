package com.example.votingapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.votingapp.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityRegisterPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        val redirect_to_loginpage = binding.textView6
        val back = binding.backbtn

        redirect_to_loginpage.setOnClickListener {
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        back.setOnClickListener{
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {

            val name = binding.editTextText.text.toString()
            val phone = binding.editTextText1.text.toString()
            val email = binding.editTextText2.text.toString()
            val pass = binding.editTextText3.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }

        }

    }
}