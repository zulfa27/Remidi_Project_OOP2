package com.zulfa.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zulfa.myapplication.Database.Pasien
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_pasien.setOnClickListener{
            val intent = Intent(this, PasienActivity::class.java)
            startActivity(intent)
        }
    }
}