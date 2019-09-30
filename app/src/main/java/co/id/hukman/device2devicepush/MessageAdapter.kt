package co.id.hukman.device2devicepush

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import co.id.hukman.device2devicepush.model.Message
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import java.text.DateFormat

class MessageAdapter(val context: Context, query: Query, val userID: String) :
    FirestoreRecyclerAdapter<Message, MessageHolder>(
        FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()
    ) {

    /*lateinit var storageReference : StorageReference
    val requestOptions = RequestOptions()*/
    private val MESSAGE_IN_VIEW_TYPE = 1
    private val MESSAGE_OUT_VIEW_TYPE = 2

    /*override fun getItemViewType(position: Int): Int {
        if (getItem(position).messageUserId == userID)
            return MESSAGE_OUT_VIEW_TYPE else return MESSAGE_IN_VIEW_TYPE
    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        if (viewType == MESSAGE_IN_VIEW_TYPE)
            return MessageHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.message_left,
                    parent,
                    false
                )
            )
        else
            return MessageHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.message_right,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int, message: Message) {
        val mText = holder.mText
        val mUsername = holder.mUsername
        val mTime = holder.mTime
        val imgProfile = holder.imgProfile
        val imgDropdown = holder.imgDropdown

        mText.text = message.messageText
        mUsername.text = message.messageUser
        mTime.text = DateFormat.getInstance().format(message.messageTime)

        Glide.with(context).load(R.drawable.ic_account_circle_black_24dp).into(holder.imgProfile)
    }


}