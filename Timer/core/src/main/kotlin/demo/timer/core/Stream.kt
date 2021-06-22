package demo.timer.core

import kotlinx.coroutines.flow.Flow

typealias Stream<T> = Flow<@JvmSuppressWildcards T>
