package demo.realm.data.model

import org.bson.Document

internal class CredentialsImpl(
    username: String,
    password: String,
) : Credentials, Document() {

    override var username: String by this
    override var password: String by this

    init {
        this.username = username
        this.password = password
    }
}
