package co.id.hukman.device2devicepush

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mText : TextView = itemView.findViewById(R.id.userMessageTextView)
    var mUsername : TextView = itemView.findViewById(R.id.userNameTextView)
    var mTime : TextView = itemView.findViewById(R.id.userMessageDateTextView)
    var imgProfile : ImageView = itemView.findViewById(R.id.userImgView)
    var imgDropdown : ImageView = itemView.findViewById(R.id.userDropDown)

}