package com.example.votingapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.annotation.meta.When


class VotingPage : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var voteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_page)

        database = FirebaseDatabase.getInstance()
        usersRef = database.reference.child("users")

        checkBox1 = findViewById(R.id.checkBox1)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3 = findViewById(R.id.checkBox3)
        checkBox4 = findViewById(R.id.checkBox4)
        voteButton = findViewById(R.id.voteButton)


        registerCheckBoxListeners()

        voteButton.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.uid?.let { userId ->
                usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(applicationContext, "You already voted", Toast.LENGTH_SHORT).show()
                        } else {

                            val selectedCheckBox = listOf(checkBox1, checkBox2, checkBox3, checkBox4).find { it.isChecked }

                            selectedCheckBox?.let { checkBox ->
                                val selectedCandidate = when (checkBox) {
                                    checkBox1 -> "Party A"
                                    checkBox2 -> "Party B"
                                    checkBox3 -> "Party C"
                                    checkBox4 -> "Party D"
                                    else -> null
                                }

                                usersRef.child(userId).setValue(selectedCandidate)
                                Toast.makeText(applicationContext, "Vote recorded", Toast.LENGTH_SHORT).show()
                            } ?: run {
                                Toast.makeText(applicationContext, "Please select a candidate", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException())
                    }
                })
            }
        }
    }

    private fun registerCheckBoxListeners() {
        val checkBoxes = listOf(checkBox1, checkBox2, checkBox3, checkBox4)

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkBoxes.forEach { otherCheckBox ->
                        if (otherCheckBox != checkBox) {
                            otherCheckBox.isEnabled = false
                        }
                    }
                } else {
                    checkBoxes.forEach { otherCheckBox ->
                        otherCheckBox.isEnabled = true
                    }
                }
            }
        }
    }
}

