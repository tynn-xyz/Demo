package demo.realm.data.model

internal data class CredentialsImpl(
    override val username: String,
    override val password: String,
) : Credentials
