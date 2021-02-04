package com.zulfa.myapplication.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pasien")
data class Pasien(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nama") val nama: String,
    @ColumnInfo(name = "umur") val umur: Int,
    @ColumnInfo(name = "no_hp") val no_hp: Int
)