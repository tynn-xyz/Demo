package demo.realm.data.model

sealed interface Credentials {
    val username: String
    val password: String
}

fun Credentials(
    username: String,
    password: String,
): Credentials = CredentialsImpl(
    username = username,
    password = password,
)
