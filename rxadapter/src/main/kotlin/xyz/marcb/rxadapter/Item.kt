package xyz.marcb.rxadapter

import android.support.v7.widget.RecyclerView
import rx.Observable
import java.util.*

class Item<I, VH>(private val vhClass: Class<VH>,
                  private val item: Observable<I>)
    : AdapterPart where I: Any?, VH: RecyclerView.ViewHolder {

    var binder: ((I, VH) -> Unit)? = null
    override var visible: Observable<Boolean>? = null
    private val id = UUID.randomUUID().toString()

    override val snapshots: Observable<AdapterPartSnapshot> get() =
        item.map { item -> Snapshot(vhClass, listOf(item), listOf(id), binder) }
}