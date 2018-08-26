package com.example.sandy.android_kotlin_firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser




class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()



        loginbtn.setOnClickListener {

            val emailip = email.text.toString()
            val passwordip = password.text.toString()



            mAuth!!.createUserWithEmailAndPassword(emailip,passwordip)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this@MainActivity,"successfully login",Toast.LENGTH_LONG).show()
                            val user = mAuth!!.getCurrentUser()

                        } else {

                            Toast.makeText(this@MainActivity," login failed",Toast.LENGTH_LONG).show()

                        }

                        // ...
                    }
        }
    }


}
