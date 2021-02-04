package com.zulfa.myapplication.Database

import androidx.room.*

@Dao
interface PasienDao {
    @Insert
    suspend fun addPasien(pasien: Pasien)

    @Update
    suspend fun updatePasien(pasien: Pasien)

    @Delete
    suspend fun deletePasien(pasien: Pasien)

    @Query("SELECT * FROM pasien")
    suspend fun getAllPasien(): List<Pasien>

    @Query("SELECT * FROM pasien WHERE id=:pasien_id")
    suspend fun getPasien(pasien_id: Int) : List<Pasien>

}