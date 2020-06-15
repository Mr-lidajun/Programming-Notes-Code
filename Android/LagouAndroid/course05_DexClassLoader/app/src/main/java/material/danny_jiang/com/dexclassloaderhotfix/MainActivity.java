package material.danny_jiang.com.dexclassloaderhotfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    ISay say;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader loader = MainActivity.class.getClassLoader();
        Log.d(TAG, "onCreate: " + loader.toString());

        say = new SayException();

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取hotfix的jar包文化
                final File jarFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "say_something_hotfix.jar");

                if (!jarFile.exists()) {
                    say = new SayException();
                    Toast.makeText(MainActivity.this, say.saySomething(), Toast.LENGTH_LONG).show();
                } else {
                    // 只需有读写权限的路径均可
                    DexClassLoader dexClassLoader = new DexClassLoader(jarFile.getAbsolutePath(), getExternalCacheDir().getAbsolutePath(), null, getClassLoader());
                    try {
                        // 加载 SayHotFix类
                        Class clazz = dexClassLoader.loadClass("material.danny_jiang.com.dexclassloaderhotfix.SayHotFix");
                        // 强转成 ISay，注意 ISay 的包名需要和hotfix jar包中的一致
                        ISay iSay = (ISay) clazz.newInstance();
                        Toast.makeText(MainActivity.this, iSay.saySomething(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
