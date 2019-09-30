package co.id.hukman.device2devicepush.model

class Message(){
    var messageUser: String = ""
    var messageText: String = ""
    var messageUserId: String =""
    var messageTime: Long = 0L
    constructor(mUser: String, mText: String, mUserId: String, mTime: Long): this(){
        messageUser = mUser
       messageText = mText
        messageUserId = mUserId
        messageTime = mTime
    }

}