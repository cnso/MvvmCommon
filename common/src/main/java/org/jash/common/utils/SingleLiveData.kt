package org.jash.common.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T>:MutableLiveData<T>() {
    private val map = mutableMapOf<Observer<*>, AtomicBoolean>()
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        map[observer] = AtomicBoolean(false)
        super.observe(owner) {
            if (map[observer]?.compareAndSet(true, false) == true) {
                observer.onChanged(it)
            }
        }
    }

    override fun setValue(value: T) {
        map.values.forEach {
            it.set(true)
        }
        super.setValue(value)
    }
}