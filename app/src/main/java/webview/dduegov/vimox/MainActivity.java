package webview.dduegov.vimox;

/**
 * Created by vimox on 3/7/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class MainActivity extends Activity {


    final int SPLASH_TIME_OUT = 3500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,
                        WebViewClient.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                finish();

            }
        }, SPLASH_TIME_OUT);


    }
}
