package com.prueba.hugo.view.task4

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.hugo.App.Companion.context
import com.prueba.hugo.tools.SingleLiveEvent
import com.prueba.hugo.tools.extensions.getString
import kotlinx.android.synthetic.main.fragment_task4.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit
import com.prueba.hugo.R


/**
 * Created by Josaél Hernández on 12/9/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class Task4ViewModel : ViewModel() {

    var showPlay = SingleLiveEvent<Boolean>()
    var showStop = SingleLiveEvent<Boolean>()
    var showReset = SingleLiveEvent<Boolean>()
    var txtTime = MutableLiveData<String>()
    var txtMinute = MutableLiveData<String>()

    var playbackSpeed = MutableLiveData<Int>()

    var progressBar = MutableLiveData<Int>()
    var progressBarMax = MutableLiveData<Int>()

    private var countDownTimer: CountDownTimer? = null
    private var timeCountInMilliSeconds = (1 * 60000).toLong()

    private enum class TimerStatus {
        STARTED, STOPPED
    }

    private var timerStatus = TimerStatus.STOPPED

    fun init(){
        showPlay.postValue(true)
        showStop.postValue(false)
        showReset.postValue(false)
        txtTime.value = getString(R.string._00_01_00)
        playbackSpeed.value = 1
        txtMinute.value = "1"
        progressBar.value = 100
        progressBarMax.value = 100
    }

    fun onClickPlayStop() {
        startStop()
    }

    fun onClickReset() {
        reset()
    }

    /**
     * method to reset count down timer
     */
    private fun reset() {
        stopCountDownTimer()
        startCountDownTimer()
    }

    fun onSeekBarChanged(seekBar: SeekBar, progress:Int, fromUser:Boolean){
        txtMinute.value = progress.toString()
    }

    /**
     * method to start and stop count down timer
     */
    private fun startStop() {
        if (timerStatus === TimerStatus.STOPPED) {
            // call to initialize the timer values
            setTimerValues()
            // call to initialize the progress bar values
            setProgressBarValues()
            // showing the reset icon
            showReset.postValue(true)
            // changing stop icon to start icon
            showPlay.postValue(false)
            showStop.postValue(true)
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED
            // call to start the count down timer
            startCountDownTimer()
        } else {
            // hiding the reset icon
            showReset.postValue(false)
            // changing stop icon to start icon
            showPlay.postValue(true)
            showStop.postValue(false)
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED
            stopCountDownTimer()
        }
    }

    /**
     * method to initialize the values for count down timer
     */
    private fun setTimerValues() {
        var time = 0
        if (txtMinute.value.toString().isNotEmpty()) {
            // fetching value from edit text and type cast to integer
            time = txtMinute.value.toString().trim().toInt()
        } else {
            // toast message
            Toast.makeText(context, getString(R.string.message_minutes), Toast.LENGTH_LONG).show()
        }
        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = (time * 60 * 1000).toLong()
    }


    /**
     * method to start count down timer
     */
    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeCountInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtTime.value = hmsTimeFormatter(millisUntilFinished)
                progressBar.value = (millisUntilFinished / 1000).toInt()
            }
            override fun onFinish() {
                txtTime.value = hmsTimeFormatter(timeCountInMilliSeconds)
                // call to initialize the progress bar values
                setProgressBarValues()
                // hiding the reset icon
                showReset.postValue(false)
                // changing stop icon to start icon
                showPlay.postValue(true)
                showStop.postValue(false)
                txtMinute.value = "1"
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED
            }
        }.start()
        countDownTimer?.start()
    }

    /**
     * method to stop count down timer
     */
    private fun stopCountDownTimer() {
        countDownTimer!!.cancel()
    }

    /**
     * method to set circular progress bar values
     */
    private fun setProgressBarValues() {
        progressBarMax.value = timeCountInMilliSeconds.toInt() / 1000
        progressBar.value = timeCountInMilliSeconds.toInt() / 1000
    }

    /**
     * method to convert millisecond to time format
     */
    @SuppressLint("DefaultLocale")
    private fun hmsTimeFormatter(milliSeconds: Long): kotlin.String? {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)))
    }
}

