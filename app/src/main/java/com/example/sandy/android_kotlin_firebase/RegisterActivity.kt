package com.example.sandy.android_kotlin_firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()



        loginbtn.setOnClickListener {

           register()
        }
    }

    private fun register() {




        mAuth!!.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this@RegisterActivity,"successfully login", Toast.LENGTH_LONG).show()
                        val user = mAuth!!.getCurrentUser()

                    } else {

                        Toast.makeText(this@RegisterActivity," login failed", Toast.LENGTH_LONG).show()

                    }

                    // ...
                }
    }
}
