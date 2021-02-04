package com.zulfa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager


import com.zulfa.myapplication.Database.AppRoomDB
import com.zulfa.myapplication.Database.Constant
import com.zulfa.myapplication.Database.Pasien
import kotlinx.android.synthetic.main.activity_pasien.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PasienActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var pasienAdapter: PasienAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadPasien()
    }

    fun loadPasien(){
        CoroutineScope(Dispatchers.IO).launch {
            val allPasien = db.pasienDao().getAllPasien()
            Log.d("PasienActivity", "dbResponse: $allPasien")
            withContext(Dispatchers.Main) {
                pasienAdapter.setData(allPasien)
            }
        }
    }

    fun setupListener() {
        btn_createPasien.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        pasienAdapter = PasienAdapter(arrayListOf(), object: PasienAdapter.OnAdapterListener {
            override fun onClick(pasien: Pasien) {
                // read detail
                intentEdit(pasien.id, Constant.TYPE_READ)
            }

            override fun onDelete(pasien: Pasien) {
                // delete data
                deleteDialog(pasien)
            }

            override fun onUpdate(pasien: Pasien) {
                // edit data
                intentEdit(pasien.id, Constant.TYPE_UPDATE)
            }

        })
        list_pasien.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = pasienAdapter
        }
    }

    fun intentEdit(pasienId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditPasienActivity::class.java)
                .putExtra("intent_id", pasienId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(pasien: Pasien) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.pasienDao().deletePasien(pasien)
                    loadPasien()
                }
            }
        }
        alertDialog.show()
    }
}