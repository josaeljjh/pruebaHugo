@file:Suppress("DEPRECATION")

package com.prueba.hugo.tools.extensions

import android.content.Context
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.view.updatePadding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.prueba.hugo.tools.CustomBottomSheetBehavior
import com.prueba.hugo.tools.SafeClickListener


/**
 * Created by Josaél Hernández on 8/20/20.
 * Contact : josaeljjh@gmail.com
 */

fun View.marginUpdate() {
    systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    setPadding(0, 0, 0, 0)
    doOnApplyWindowInsets { view, insets, padding ->
        view.updatePadding(bottom = padding.bottom + insets.systemWindowInsetBottom)
        //insets.consumeSystemWindowInsets()
    }

}

fun View.doOnApplyWindowInsets(f: (View, WindowInsets, InitialPadding) -> Unit) {
    // Create a snapshot of the view's padding state
    val initialPadding = recordInitialPaddingForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding state
    setOnApplyWindowInsetsListener { v, insets ->
        f(v, insets, initialPadding)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

data class InitialPadding(
    val left: Int, val top: Int,
    val right: Int, val bottom: Int
)

fun View.makeVisibility(visibilidad: Boolean = false) {
    this.visibility = android.view.View.GONE
    if (visibilidad) {
        this.visibility = android.view.View.VISIBLE
    }
}

infix fun CustomBottomSheetBehavior<LinearLayout>?.expandedDialog(stateExpanded: Boolean) {
    this?.let {
        it.state = if(stateExpanded) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
    }
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener { view ->
        onSafeClick(view)
    }
    setOnClickListener(safeClickListener)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}