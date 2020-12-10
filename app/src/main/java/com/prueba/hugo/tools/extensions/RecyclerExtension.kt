package com.prueba.hugo.tools.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prueba.hugo.tools.DynamicBindingAdapter


fun androidx.recyclerview.widget.RecyclerView.configureRecycler(isVertical: Boolean = true): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context,
        if (isVertical) androidx.recyclerview.widget.RecyclerView.VERTICAL else androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false)
    return this
}

fun androidx.recyclerview.widget.RecyclerView.configureRecyclerGrid(elementPerRow: Int = 3): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.GridLayoutManager(context,elementPerRow)
    return this
}

//Configure simple vertical or horizontal RecyclerView
fun <T> RecyclerView.configureRecyclerBinding(
    adapter: DynamicBindingAdapter<T>,
    isVertical: Boolean = true,
    isgrid: Boolean = false,
    numColumns: Int = 2
) {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    if (isgrid) {
        layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, numColumns)
    } else {
        layoutManager = LinearLayoutManager(
            context,
            if (isVertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL, false
        )
    }

    this.adapter = adapter
}