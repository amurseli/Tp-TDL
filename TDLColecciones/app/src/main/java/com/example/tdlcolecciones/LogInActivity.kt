package com.example.tdlcolecciones

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LogInActivity : AppCompatActivity() {
    //lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient : GoogleSignInClient
    lateinit var mAuth:FirebaseAuth
    private val RC_SIGN_IN: Int = 123
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val log_in_btn: Button = findViewById(R.id.log_in_btn)
        val logOut: Button = findViewById(R.id.logOut)
        val logInText: TextView = findViewById(R.id.loginTextView)

        mAuth = FirebaseAuth.getInstance();
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey("logout")) {
                val boolLogOut = extras.getBoolean("logout", false)
                if (boolLogOut){
                    signOut()
                }
            }
        }
        createRequest();

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        if (acct != null) {
            logInText.text = "Hola: \n" + acct.email
            log_in_btn.text = "Ingresar"
            val user: FirebaseUser? = mAuth.getCurrentUser()
            email = mAuth.currentUser?.email.toString()
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        } else {
            logOut.visibility = View.INVISIBLE
            logInText.text = "Ingresa con tu cuenta!"
        }


        log_in_btn.setOnClickListener(View.OnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })

        logOut.setOnClickListener(View.OnClickListener {
            if(acct != null){
                mAuth.signOut()
                mGoogleSignInClient.signOut()
                logOut.visibility = View.INVISIBLE
                logInText.text = "Ingresa con tu cuenta!"
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    public fun signOut(){
        createRequest()
        mGoogleSignInClient.signOut()
    }

    private fun createRequest() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("753873585039-2ma4ua4n0d3713s7dmuaun7ubnkqpp4v.apps.googleusercontent.com")
            .requestEmail()
            .build()
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = mAuth.getCurrentUser()
                        email = mAuth.currentUser?.email.toString()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Auth Failed", Toast.LENGTH_SHORT).show()
                    }
                })
    }


}