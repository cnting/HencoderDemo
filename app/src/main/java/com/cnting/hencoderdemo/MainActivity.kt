package com.cnting.hencoderdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cnting.ui1_1.UI1_1Activity
import com.cnting.ui1_2.UI1_2Activity
import com.cnting.ui1_3.UI1_3Activity
import com.cnting.ui1_4.UI1_4Activity
import com.cnting.ui1_5.UI1_5Activity
import com.cnting.ui1_6.UI1_6Activity
import com.cnting.ui1_7.UI1_7Activity
import com.cnting.ui2_1.UI2_1Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ui1_1.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_1Activity::class.java)) }
        ui1_2.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_2Activity::class.java)) }
        ui1_3.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_3Activity::class.java)) }
        ui1_4.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_4Activity::class.java)) }
        ui1_5.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_5Activity::class.java)) }
        ui1_6.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_6Activity::class.java)) }
        ui1_7.setOnClickListener { startActivity(Intent(this@MainActivity, UI1_7Activity::class.java)) }
        ui2_1.setOnClickListener { startActivity(Intent(this@MainActivity, UI2_1Activity::class.java)) }
    }
}
