package material.danny_jiang.com.lagoutouchexplanation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.bt_intercept_event).setOnClickListener {
            startActivity(Intent(this, InterceptActivity::class.java))
        }

        findViewById<View>(R.id.bt_cancel_event).setOnClickListener {
            startActivity(Intent(this, CancelEventActivity::class.java))
        }
    }
}
