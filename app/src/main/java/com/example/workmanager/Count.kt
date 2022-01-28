package com.example.workmanager

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class Count(private val countContext: Context, parameters: WorkerParameters) :
    Worker(countContext, parameters) {

    override fun doWork(): Result {
        Log.d("Worker", "Worker Runs")

        val inputData = inputData
        val name: String? = inputData.getString("name")
        Log.d("Worker", "Do Work Name :$name")

        var number = inputData.getInt("number", 0)
        Log.d("Worker", "Do Work Numbers :$number")

        for (i in 1..number) {

            try {
                Thread.sleep(1000)

                Log.e("CountDownWork", "Do Work Number :$i")
            } catch (ex: InterruptedException) {
                ex.printStackTrace()

            }
        }

        Log.e("CountDownWork", "Timer end")
        return Result.success()
    }
}