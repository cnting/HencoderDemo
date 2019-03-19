package com.cnting.hencoderdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cnting.ui1_1.UI1_1Activity
import com.cnting.ui1_2.UI1_2Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ui1_1.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_1Activity::class.java)) }
        ui1_2.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_2Activity::class.java)) }
    }
}
