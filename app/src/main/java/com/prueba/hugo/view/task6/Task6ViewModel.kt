package com.prueba.hugo.view.task6

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.hugo.R
import com.prueba.hugo.tools.SingleLiveEvent
import com.prueba.hugo.tools.extensions.getColor
import com.prueba.hugo.tools.extensions.getString


/**
 * Created by Josaél Hernández on 12/11/20.
 * Contact : josaeljjh@gmail.com
 */
class Task6ViewModel : ViewModel() {

    var showCreate = SingleLiveEvent<Boolean>()
    var showCircle = SingleLiveEvent<Boolean>()
    var progressSeek = MutableLiveData<Int>()
    var txtSize = MutableLiveData<String>()
    var size:Int? = null
    var imgCircle:ImageView? = null

    fun init(){
        showCreate.postValue(true)
        showCircle.postValue(false)
        progressSeek.value = 30
        txtSize.value = "${getString(R.string.tamano)} 30 %"
        size = 100
    }

    fun onSeekBarChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean){
        txtSize.value = "${getString(R.string.tamano)} ${progress.toString()} ${"%"}"
        size = progress * 10
        onClickCreate()
    }

    fun onClickCreate() {
        showCreate.postValue(false)

        if(size!! > 1){
            // Initialize a new Bitmap object
            val bitmap = Bitmap.createBitmap(
                    size!!,  // Width
                    size!!,  // Height
                    Bitmap.Config.ARGB_8888 // Config
            )
            // Initialize a new Canvas instance
            val canvas = Canvas(bitmap)


            // Draw a solid color to the canvas background
            canvas.drawColor(Color.TRANSPARENT)

            // Initialize a new Paint instance to draw the Circle
            val paint = Paint()
            paint.style = Paint.Style.FILL
            paint.color = getColor(R.color.negro_alpha)
            paint.isAntiAlias = true

            // Calculate the available radius of canvas
            val radius: Int = Math.min(canvas.width, canvas.height / 2)

            // Set a pixels value to padding around the circle
            val padding = 5
            // Finally, draw the circle on the canvas
            canvas.drawCircle(
                    (canvas.width / 2).toFloat(), // cx
                    (canvas.height / 2).toFloat(), // cy
                    (radius - padding).toFloat(), // Radius
                    paint // Paint
            )

            imgCircle?.setImageBitmap(bitmap)
            showCircle.postValue(true)
        }

    }

    fun onClickDelete() {
        showCreate.postValue(true)
        showCircle.postValue(false)
    }
}