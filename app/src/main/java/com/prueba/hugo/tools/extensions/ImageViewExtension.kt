package com.prueba.hugo.tools.extensions


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.prueba.hugo.R


//Fondos de servicio
@BindingAdapter("imageBlur")
fun ImageView.setBackgroundBlur(id: Int) {
    this.load(id) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
        //transformations(BlurTransformation(context,3f))
        //transformations(GrayscaleTransformation())
    }
}

@BindingAdapter("backgroundImg")
fun ImageView.setBackgroundImg(id: Int) {
    this.load(id) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("circleImage2")
fun ImageView.setCircleImage(id: Int) {
        this.load(id) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
            transformations(CircleCropTransformation())
            //transformations(BlurTransformation(context,3f))
            //transformations(GrayscaleTransformation())
        }
}

