@file:[JvmMultifileClass JvmName("UtilsKt")]

package demo.realm.app.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope
