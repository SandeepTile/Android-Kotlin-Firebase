package com.example.sandy.android_kotlin_firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sandy.android_kotlin_firebase.R.id.regemail
import com.example.sandy.android_kotlin_firebase.R.id.regpassword
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.example.sandy.android_kotlin_firebase.R.id.username
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_register.*
import java.time.Instant
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task


//import javax.swing.UIManager.put


//import javax.swing.UIManager.put




class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var mDatabase:DatabaseReference
    lateinit var mGoogleSignInClient:GoogleSignInClient
    lateinit var gso:GoogleSignInOptions
    val RC_SIGN_IN:Int=1
    private val GALLERY_PICK = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient=GoogleSignIn.getClient(this,gso)

        pic.setOnClickListener {

            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK)
        }




        regbtn.setOnClickListener {

            if (regemail.toString() == "" &&regpassword.toString() == ""){
                Toast.makeText(this@RegisterActivity,"plz enter email and password",Toast.LENGTH_LONG).show()

            }else{

                register()
            }

        }


        sign_in_button.setOnClickListener {

            googleSignin()
        }

    }



    private fun googleSignin() {


        val signInIntent:Intent=mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==RC_SIGN_IN){

            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult (task)
        }
    }

    private fun handleResult(completedTask:Task<GoogleSignInAccount>){

        try {

            val account:GoogleSignInAccount=completedTask.getResult(ApiException::class.java)
            updateUI(account)


        }catch (e:ApiException){

            Toast.makeText(this@RegisterActivity,e.toString(),Toast.LENGTH_LONG).show()
        }

    }

    private fun updateUI(account: GoogleSignInAccount) {



    }

    private fun register() {


        mAuth!!.createUserWithEmailAndPassword(regemail.text.toString(),regpassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this@RegisterActivity,"enter",Toast.LENGTH_LONG).show()

                        val current_user = FirebaseAuth.getInstance().currentUser
                        val uid = current_user!!.uid

                        val database = FirebaseDatabase.getInstance().getReference("Users").child(uid)


                        val usermap = HashMap<String,String>()
                        usermap.put("name", regname.text.toString())
                        usermap.put("status", "Hi there,I'm using Android chatApp")
                        usermap.put("image", "default")
                        usermap.put("thumb_image", "default")

                        database.setValue(usermap).addOnCompleteListener {

                            var intent=Intent(this@RegisterActivity,QRCodeActivity::class.java)
                            startActivity(intent)
                        }

                        Toast.makeText(this@RegisterActivity,"successfully login", Toast.LENGTH_LONG).show()



                    } else {

                        Toast.makeText(this@RegisterActivity," login failed", Toast.LENGTH_LONG).show()

                    }

                    // ...
                }
    }


}






