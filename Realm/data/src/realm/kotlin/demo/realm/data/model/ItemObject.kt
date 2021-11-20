package demo.realm.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import io.realm.annotations.Required
import java.util.*
import java.util.UUID.randomUUID

@RealmClass("Item")
internal open class ItemObject constructor(
    @[Required PrimaryKey RealmField("_id")]
    override var itemId: UUID = randomUUID(),
    @Required
    override var value: String = "",
    @[Required RealmField("_owner")]
    var owner: String? = null
) : Item, RealmObject()
