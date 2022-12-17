package jama.movieapp.sms_authfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import jama.movieapp.sms_authfirebase.databinding.ActivityGoogleBinding

class GoogleActivity : AppCompatActivity() {
    lateinit var gso:GoogleSignInOptions
    lateinit var gsc :GoogleSignInClient
    lateinit var binding:ActivityGoogleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGoogleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this,gso)
        binding.root.setOnClickListener {
            googleSignOut()
            SignIn()
        }
    }

    private fun SignIn() {
        val intent = gsc.signInIntent
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100){
            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(this, "Succes", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, account.displayName.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(this, account.email.toString(), Toast.LENGTH_SHORT).show()
                googleSignOut()
            }catch (e:ApiException){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun googleSignOut() {
        gsc.signOut()
            .addOnCompleteListener(this) { task ->
                // [START_EXCLUDE]
                // if(task.isSuccessful) Utility.showSnackBar(login_screen_parent_layout, LanguagePack.getString("Signout Successful"), this)
                // else Utility.showSnackBar(login_screen_parent_layout, LanguagePack.getString("Failure: ${task.exception}"), this)
                // [END_EXCLUDE]

            }
    }
}