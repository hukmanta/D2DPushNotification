package co.id.hukman.device2devicepush

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.hukman.device2devicepush.model.Message
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MESSAGE_TABLE = "message"
        const val ORDER_BY = "messageTime"
    }

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var database: FirebaseFirestore
    private lateinit var query: Query
    private var adapter: MessageAdapter? = null
    private lateinit var input: MultiAutoCompleteTextView
    private lateinit var progressBar: ProgressBar
    private lateinit var sendFAB: FloatingActionButton
    private lateinit var userId: String
    private lateinit var messageRecyclerView: RecyclerView
    private var userName: String? = null
    lateinit var imgProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializingView()
        initializingFireBase()
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        gettingFireBaseData()
        adapter = MessageAdapter(this, query, userId)
        messageRecyclerView.adapter = adapter
    }

    fun initializingView() {
        input = messageEditText
        progressBar = loadingProgressBar
        messageRecyclerView = recyclerView
        messageRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        messageRecyclerView.setHasFixedSize(true)
        sendFAB = floatingActionButton
        sendFAB.setOnClickListener {
            if (it.id == R.id.floatingActionButton) {
                val message = input.text.toString()
                if (TextUtils.isEmpty(message)) Toast.makeText(
                    this,
                    "No Post to Post",
                    Toast.LENGTH_SHORT
                ).show()
                database.collection(MESSAGE_TABLE)
                    .add(Message(userName!!, message, userId, Date().time))
                input.setText("")
            }
        }
    }

    private fun initializingFireBase() {
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if (user != null) Log.i(this::javaClass.name, "On create Found user ${user.toString()}")
    }

    private fun gettingFireBaseData() {
        userId = user!!.uid
        userName = user?.displayName
        database = FirebaseFirestore.getInstance()
        query = database.collection(MESSAGE_TABLE).orderBy(ORDER_BY)
        query.addSnapshotListener { querySnapshot, _ ->
            if (querySnapshot != null && !querySnapshot.isEmpty)
                progressBar.visibility = View.GONE
        }

    }

    override fun onStart() {
        super.onStart()
        if (adapter != null) adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter!=null) adapter!!.stopListening()
    }
}
