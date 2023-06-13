package com.example.a7minutesworkout

import androidx.room.Insert

interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)
}