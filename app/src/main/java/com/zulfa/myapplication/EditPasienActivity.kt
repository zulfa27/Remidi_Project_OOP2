package com.zulfa.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.zulfa.myapplication.Database.AppRoomDB
import com.zulfa.myapplication.Database.Constant
import com.zulfa.myapplication.Database.Pasien
import com.zulfa.myapplication.R
import kotlinx.android.synthetic.main.activity_edit_pasien.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPasienActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var pasienId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasien)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_savePasien.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.pasienDao().addPasien(
                    Pasien(0, txt_nama.text.toString(), Integer.parseInt(txt_umur.text.toString()), Integer.parseInt(txt_no_hp.text.toString()) )
                )
                finish()
            }
        }
        btn_updatePasien.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.pasienDao().updatePasien(
                    Pasien(pasienId, txt_nama.text.toString(), Integer.parseInt(txt_umur.text.toString()), Integer.parseInt(txt_no_hp.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updatePasien.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_savePasien.visibility = View.GONE
                btn_updatePasien.visibility = View.GONE
                getPasien()
            }
            Constant.TYPE_UPDATE -> {
                btn_savePasien.visibility = View.GONE
                getPasien()
            }
        }
    }

    fun getPasien() {
        pasienId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val pasiens =  db.pasienDao().getPasien( pasienId )[0]
            txt_nama.setText( pasiens.nama )
            txt_umur.setText( pasiens.umur.toString() )
            txt_no_hp.setText( pasiens.no_hp.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}