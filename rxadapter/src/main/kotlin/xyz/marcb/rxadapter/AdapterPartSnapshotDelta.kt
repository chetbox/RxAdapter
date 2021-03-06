package xyz.marcb.rxadapter

import android.support.v7.util.DiffUtil

internal class AdapterPartSnapshotDelta(
        private val old: AdapterPartSnapshot, private val new: AdapterPartSnapshot)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.itemCount
    override fun getNewListSize(): Int = new.itemCount

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old.itemIds[oldItemPosition] == new.itemIds[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old.underlyingObject(oldItemPosition) == new.underlyingObject(newItemPosition)
}