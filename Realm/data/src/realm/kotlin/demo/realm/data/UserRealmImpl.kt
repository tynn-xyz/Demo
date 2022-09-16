package demo.realm.data

import demo.realm.data.model.Item
import demo.realm.data.model.ItemObject
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

internal object UserRealmImpl : UserRealm, AppRealmImpl("realm") {

    private val userRealm by lazy {
        realmFlow { id }.share()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val items by lazy {
        userRealm.flatMapLatest {
            it?.items ?: flowOf(emptyList())
        }.share(1_000)
    }

    private val Realm.items: Flow<List<Item>>
        get() = where<ItemObject>().findAll().toFlow().map {
            it.map { item ->
                Item(
                    item.itemId,
                    item.value,
                )
            }
        }

    override suspend operator fun plusAssign(item: Item) {
        launchInScope {
            userRealm.firstOrNull()?.executeTransactionAwait {
                it.insertOrUpdate(
                    item.asItemObject().apply {
                        owner = currentUser?.id
                    }
                )
            }
        }
    }

    override suspend operator fun minusAssign(item: Item) {
        launchInScope {
            userRealm.firstOrNull()?.executeTransactionAwait {
                it.where<ItemObject>().equalTo(
                    "_id",
                    item.itemId,
                ).findFirst()?.deleteFromRealm()
            }
        }
    }

    private fun Item.asItemObject() = this as? ItemObject ?: ItemObject(
        itemId = itemId,
        value = value,
    )
}
