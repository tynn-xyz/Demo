package demo.realm.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import demo.realm.data.AppRealm
import demo.realm.data.UserRealm
import demo.realm.data.model.Credentials
import demo.realm.data.model.Item
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun storeItem(item: Item) {
        viewModelScope.launch {
            UserRealm += item
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            UserRealm -= item
        }
    }

    fun login(credentials: Credentials) {
        viewModelScope.launch {
            AppRealm += credentials
        }
    }

    fun logout() {
        viewModelScope.launch {
            AppRealm -= null
        }
    }
}
