package org.jash.common.utils

import androidx.lifecycle.LifecycleOwner

val bus = SingleLiveData<Pair<String, Any>>()

fun observerBus(owner: LifecycleOwner, thiz:Any) {
    val methods = thiz.javaClass.declaredMethods
    for (method in methods) {
        val event = method.getAnnotation(Event::class.java)
        if (event != null) {
            bus.observe(owner) {
                if (it.first == event.value) {
                    method.invoke(thiz, it.second)
                }
            }
        }
    }
}