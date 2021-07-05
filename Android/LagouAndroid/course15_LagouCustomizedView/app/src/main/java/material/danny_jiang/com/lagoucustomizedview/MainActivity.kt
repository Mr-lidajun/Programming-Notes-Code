package material.danny_jiang.com.lagoucustomizedview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.bt_pie_image).setOnClickListener {
            startActivity(Intent(this, PieImageActivity::class.java))
        }

        findViewById<View>(R.id.bt_flow_layout).setOnClickListener {
            startActivity(Intent(this, FlowLayoutActivity::class.java))
        }
    }
}
