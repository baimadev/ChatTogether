//package jp.co.renosys.crm.adk.ui.databinding
//
//import android.databinding.*
//import android.databinding.Observable.OnPropertyChangedCallback
//import io.reactivex.Observable
//import jp.co.renosys.crm.adk.ui.Event
//import jp.co.renosys.crm.adk.ui.NetworkErrorEvent
//import android.databinding.Observable as DataBindingObservable
//
//private fun <T> DataBindingObservable.toObservable(getter: () -> T): Observable<T> =
//    Observable.create { emitter ->
//        emitter.onNext(getter())
//
//        val callback = object : OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: DataBindingObservable, propertyId: Int) {
//                emitter.onNext(getter())
//            }
//        }
//        emitter.setCancellable { removeOnPropertyChangedCallback(callback) }
//        addOnPropertyChangedCallback(callback)
//    }
//
//data class NullableValue<T>(val value: T?)
//
//fun <T> ObservableField<T>.toNullableObservable(): Observable<NullableValue<T>> =
//    toObservable { NullableValue(get()) }
//
//fun <T> ObservableField<T>.toObservable(nullable: Boolean = false): Observable<T> = if (nullable) {
//    toNullableObservable().filter { it.value != null }.map { it.value }
//} else {
//    toObservable { get()!! }
//}
//
//fun <T> ObservableEvent<Event<T>>.toObservable(skipHanded: Boolean = true): Observable<T> =
//    toObservable(nullable = true).let {
//        if (skipHanded) {
//            it.filter { !it.hasBeenHandled }.map { it.getContentIfNotHandled() }
//        } else {
//            it.map { it.peekContent() }
//        }
//    }
//
//fun ObservableNetworkErrorEvent.toObservable(skipHanded: Boolean = true): Observable<NetworkErrorEvent> =
//    toObservable(nullable = true).let { if (skipHanded) it.filter { !it.hasBeenHandled } else it }
//
//fun ObservableBoolean.toObservable(): Observable<Boolean> = toObservable(this::get)
//
//fun ObservableInt.toObservable(): Observable<Int> = toObservable(this::get)
//
//fun ObservableLong.toObservable(): Observable<Long> = toObservable(this::get)
//
//private class ObservableListCallback<T>(private val callback: (List<T>) -> Unit) :
//    ObservableList.OnListChangedCallback<ObservableList<T>>() {
//
//    override fun onChanged(sender: ObservableList<T>) {
//        callback(sender)
//    }
//
//    override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
//        callback(sender)
//    }
//
//    override fun onItemRangeInserted(
//        sender: ObservableList<T>,
//        positionStart: Int, itemCount: Int
//    ) {
//        callback(sender)
//    }
//
//    override fun onItemRangeMoved(
//        sender: ObservableList<T>,
//        fromPosition: Int, toPosition: Int, itemCount: Int
//    ) {
//        callback(sender)
//    }
//
//    override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
//        callback(sender)
//    }
//}
//
//fun <T> ObservableList<T>.toObservable(): Observable<List<T>> = Observable.create { emitter ->
//    emitter.onNext(this)
//
//    val callback =
//        ObservableListCallback<T> { emitter.onNext(it) }
//    emitter.setCancellable { removeOnListChangedCallback(callback) }
//    addOnListChangedCallback(callback)
//}
