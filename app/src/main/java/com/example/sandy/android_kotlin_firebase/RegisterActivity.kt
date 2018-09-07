package com.example.sandy.android_kotlin_firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
//import com.example.sandy.android_kotlin_firebase.R.id.username
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_register.*
//import javax.swing.UIManager.put




class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mDatabase:DatabaseReference?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()


        regbtn.setOnClickListener {

            register()
        }

    }

    private fun register() {


            // get the user email and password from the UI
            // converted the string
            // here i have created the RXJS Observable to watch the
            // response from the server
            // the response is return the 200 i.e OK
            // then the user is redirected to the admin panel


        mAuth!!.createUserWithEmailAndPassword(regemail.text.toString(),regpassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {


                        val current_user = FirebaseAuth.getInstance().currentUser
                        val uid = current_user!!.uid

                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("message")

                        myRef.setValue("Hello, World!")

                        Toast.makeText(this@RegisterActivity,"successfully login", Toast.LENGTH_LONG).show()



                    } else {

                        Toast.makeText(this@RegisterActivity," login failed", Toast.LENGTH_LONG).show()

                    }

                    // ...
                }
    }
}
