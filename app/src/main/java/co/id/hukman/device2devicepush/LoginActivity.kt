package co.id.hukman.device2devicepush

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameTextField : EditText
    private lateinit var passwordTextField : EditText
    private lateinit var signupButton: Button
    private lateinit var loginbtn: Button
    private val auth = FirebaseAuth.getInstance()
    private  var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val toolbar = androidx.appcompat.widget.Toolbar(this)
        toolbar.title = "LoginActivity"
        setSupportActionBar(toolbar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameTextField = usernameET
        passwordTextField = passwordET
        signupButton = signUpButton
        signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        loginbtn = loginButton
        loginbtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = usernameTextField.text.toString().trim()
        val password = passwordTextField.text.toString().trim()

        /*
        Check for empty fields to avoid exception
         */
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email", Toast.LENGTH_LONG).show()
            return
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter password", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                currentUser = auth.currentUser
                Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Login unsuccessful", Toast.LENGTH_LONG).show()
            }
        }

    }
}
