package name.calvin.meetez;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Window;

public class splashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 1500) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                } finally {
                    finish();
                    Intent i = new Intent();
                    i.setClassName("name.calvin.meetez", "name.calvin.meetez.Dashboard");
                    startActivity(i);
                }
            }
        };
        splashThread.start();
    }
}
