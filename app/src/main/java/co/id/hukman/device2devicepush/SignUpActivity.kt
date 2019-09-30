package co.id.hukman.device2devicepush

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import co.id.hukman.device2devicepush.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var firstnameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var gender: RadioGroup
    private lateinit var submitBTN: Button
    private var firebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        layoutInitiation()
        submitBTN.setOnClickListener {
            //create user
            val userGender = if (gender.checkedRadioButtonId == R.id.radioButton) "M" else "F"
            user = User(
                firstnameET.text.toString(),
                lastNameET.text.toString(),
                email.text.toString(),
                username.text.toString(),
                password.text.toString(),
                userGender)
            // signUp process
            signUpUser()
        }
    }

    private fun signUpUser() {
        // showing progress bar
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Registering. Please Wait ...")
        progressDialog.show()

        //create user via firebase auth
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).
                addOnCompleteListener {
                    if (it.isSuccessful){
                        // set username as user display name
                        currentUser = firebaseAuth.currentUser
                        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(user.username).build()
                        currentUser!!.updateProfile(profileUpdate)

                        addUserToDatabase()
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Registration Successful", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Registration error, Check your details", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun addUserToDatabase() {
          val database = FirebaseFirestore.getInstance()
        val key = currentUser!!.uid

        val userData = HashMap<String, Any>()
        userData["firstname"] = user.firstName
        userData["lastname"] = user.lastName
        userData["password"] = user.password
        userData["username"] = user.username
        userData["email"] = user.email
        userData["gender"] = user.gender
        userData["imageUrl"] = "none"

        val update = HashMap<String, Any>()
        update[key] = userData

        database.collection("users").document(key).set(update)
    }

    private fun layoutInitiation() {
        firstnameET = firstnameEditText
        lastNameET = lastnameEditText
        email = emailEditText
        username = usernameEditText
        password = passwordEditText
        gender = radioGroup
        submitBTN = submitButton
    }
}
