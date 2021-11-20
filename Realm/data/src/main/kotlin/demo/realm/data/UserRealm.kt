package demo.realm.data

import demo.realm.data.model.Item
import kotlinx.coroutines.flow.Flow

interface UserRealm {

    val items: Flow<List<Item>>

    suspend operator fun plusAssign(item: Item)
    suspend operator fun minusAssign(item: Item)

    companion object : UserRealm by UserRealmImpl
}
