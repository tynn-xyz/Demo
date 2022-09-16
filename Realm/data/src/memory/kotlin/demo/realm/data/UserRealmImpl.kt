package demo.realm.data

import demo.realm.data.model.Credentials
import demo.realm.data.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

internal object UserRealmImpl : UserRealm {

    private val userItems = mutableMapOf<Credentials,
            MutableStateFlow<Map<UUID, Item>>>()
    private val userItemsMutex = Mutex()

    private suspend fun Credentials.getUserItems() = userItemsMutex.withLock {
        userItems.getOrPut(this) {
            MutableStateFlow(linkedMapOf())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val items = AppRealmImpl.credentials.flatMapLatest {
        it?.getUserItems()?.map { items ->
            items.values.toList()
        } ?: flowOf(emptyList())
    }.distinctUntilChanged()

    override suspend operator fun plusAssign(item: Item) {
        AppRealmImpl.credentials.value?.run {
            getUserItems().update {
                it + (item.itemId to item)
            }
        }
    }

    override suspend operator fun minusAssign(item: Item) {
        AppRealmImpl.credentials.value?.run {
            getUserItems().update {
                it - item.itemId
            }
        }
    }
}
