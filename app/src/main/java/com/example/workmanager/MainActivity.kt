package com.example.workmanager

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.work.*
import java.util.concurrent.TimeUnit
import android.util.Log
import android.widget.EditText
import android.os.CountDownTimer


class MainActivity : AppCompatActivity() {
    private lateinit var reset: AppCompatButton
    private lateinit var etText: EditText

    //   lateinit var workRequest:WorkRequest
    var countDownTimer: CountDownTimer? = null
   var periodicWorkRequest: PeriodicWorkRequest?=null
    lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Log.d("debug", "debug mode")
        }
        var x = 10
        initWidgets()
        workManager = WorkManager.getInstance(this)

        reset.setOnClickListener {
            workManagerDisplay()
            countDownTimerDisplay()
//            val number = etText.text.toString().toInt()
//            val num=etText.text.toString().toLong()
            //   countDownTimer(num)
//            WorkManager.getInstance(this).cancelAllWork()
            //          workManager(number)
        }
    }


//    suspend fun showToast() {
//        Toast.makeText(this,"Data Showed",Toast.LENGTH_LONG).show()
//    }

    private fun initWidgets() {
        reset = findViewById(R.id.btnChange)
        etText = findViewById(R.id.editTextNumber)

    }

    private fun workManagerDisplay() {


//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .setRequiresBatteryNotLow(true)
//            .setRequiresCharging(true)
//            .build()
        val data = Data.Builder()
            .putInt("number", 30)
            .putString("name", "First Work")
            .build()
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        if(periodicWorkRequest!=null){
            workManager.cancelWorkById(periodicWorkRequest!!.id)
            periodicWorkRequest =
                PeriodicWorkRequest.Builder(Count::class.java, 20, TimeUnit.SECONDS)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
            workManager.enqueueUniquePeriodicWork(
                "send_count",
                ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest!!
            )
        }
        else{
            periodicWorkRequest =
                PeriodicWorkRequest.Builder(Count::class.java, 20, TimeUnit.SECONDS)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
            workManager.enqueueUniquePeriodicWork(
                "send_count",
                ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest!!
            )
        }


//        workRequest=OneTimeWorkRequest.Builder(Count::class.java)
//            .setInputData(data)
//            .setConstraints(constraints)
//            .addTag("count_number")
//            .build()
        //  WorkManager.getInstance(this).enqueue(workRequest)

    }

    private fun countDownTimerDisplay() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.e("CountDownTimer", " Number :" + millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    Log.e("CountDownTimer", " finish")
                }
            }
            (countDownTimer as CountDownTimer).start()
        } else {
            countDownTimer = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.e("CountDown", " Number :" + millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    Log.e("CountDown", " finish")
                }
            }
            (countDownTimer as CountDownTimer).start()
        }
    }


}