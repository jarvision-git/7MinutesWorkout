package com.example.a7minutesworkout

import android.app.Application

class WoroOutApp:Application() {
    val db by lazy{
        HistoryDatabase.getInstance(this)
    }
}