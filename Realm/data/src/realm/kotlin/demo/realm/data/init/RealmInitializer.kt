package demo.realm.data.init

import android.content.Context
import androidx.startup.Initializer
import io.realm.Realm.init as initRealm

@Suppress("unused")
internal class RealmInitializer : Initializer<Unit> {
    override fun create(context: Context) = initRealm(context)
    override fun dependencies() = emptyList<Class<Initializer<*>>>()
}
