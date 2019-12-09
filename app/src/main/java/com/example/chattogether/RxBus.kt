package com.example.chattogether

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus private constructor(){
    private val mBus=PublishSubject.create<Any>().toSerialized()
    companion object{
        val instance:RxBus by lazy (mode =LazyThreadSafetyMode.SYNCHRONIZED){ RxBus() }
    }

    /**
     *根据eventType转换成相应的Observable
     */
    fun <T>toObservable(eventType:Class<T>):Observable<T>{
        return mBus.ofType(eventType)
    }
    /**
     * 发送事件
     */
    fun postEvent(event:Any){
        mBus.onNext(event)
    }
    /**
     *
     */
      fun hasObservablers():Boolean{
        return mBus.hasObservers()
    }

}