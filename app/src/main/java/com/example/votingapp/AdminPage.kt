package com.example.votingapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminPage : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference


    private lateinit var countTextView1: TextView
    private lateinit var countTextView2: TextView
    private lateinit var countTextView3: TextView
    private lateinit var countTextView4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)


        database = FirebaseDatabase.getInstance()
        usersRef = database.reference.child("users")


        countTextView1 = findViewById(R.id.count1)
        countTextView2 = findViewById(R.id.count2)
        countTextView3 = findViewById(R.id.count3)
        countTextView4 = findViewById(R.id.count4)


        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var countPartyA = 0
                var countPartyB = 0
                var countPartyC = 0
                var countPartyD = 0

                for (userSnapshot in dataSnapshot.children) {
                    val party = userSnapshot.getValue(String::class.java)
                    if (party != null) {
                        when (party) {
                            "Party A" -> countPartyA++
                            "Party B" -> countPartyB++
                            "Party C" -> countPartyC++
                            "Party D" -> countPartyD++
                        }
                    }
                }

                countTextView1.text = countPartyA.toString()
                countTextView2.text = countPartyB.toString()
                countTextView3.text = countPartyC.toString()
                countTextView4.text = countPartyD.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "Database error: ${databaseError.message}")
            }
        })

        val logoutButton: Button = findViewById(R.id.logout)
        logoutButton.setOnClickListener {
            intent = Intent(this,EnterActivity::class.java)
            startActivity(intent)
        }
    }
}