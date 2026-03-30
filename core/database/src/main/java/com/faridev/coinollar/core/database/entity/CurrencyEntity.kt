package com.faridev.coinollar.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    val symbol: String,
    val name: String,
    val nameEn: String,
    val price: Double,
    val changePercent: Double,
    val changeValue: Double?,
    val date: String,
    val time: String,
    val timeUnix: Int,
    val unit: String,
    val category: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
