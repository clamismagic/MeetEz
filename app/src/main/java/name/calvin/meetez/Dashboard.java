package name.calvin.meetez;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnLongClickListener;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Dashboard extends Activity implements OnClickListener {

    private Button backout;
    private TextView meetingevent;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        backout = (Button) findViewById(R.id.backout);
        backout.setVisibility(View.GONE);
        backout.setOnClickListener(this);
        meetingevent = (TextView) findViewById(R.id.meetingevent);
        meetingevent.setOnLongClickListener(long_click_listener);
        meetingevent.setOnClickListener(click_listener);
        SendtoPHP sendtoPHP = new SendtoPHP();
        sendtoPHP.execute(new String[] {
                "https://mappdb-clamismagic.rhcloud.com/select.php?tablename=events"
        });

       /* SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefsEdit = prefs.edit();*/
    }

    private OnClickListener click_listener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = null;
            switch (view.getId()) {
                case R.id.meetingevent1:
                    intent = new Intent(getApplicationContext(), Description.class);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    break;
            }
        }

    };

    /*     Making the button appear when text is long pressed */
    private OnLongClickListener long_click_listener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            // meetingevent1.;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) meetingevent.getLayoutParams();
            //noinspection ResourceType
            layoutParams.leftMargin = -100;
            meetingevent.setLayoutParams(layoutParams);
            backout.setVisibility(View.VISIBLE);
            return true;
        }
    };

    /* making the hamburger */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            //more items go here, if any...
            case R.id.createevent:
               intent = new Intent(getApplicationContext(), CreateEvent.class);
                if (intent != null) {
                    startActivity(intent);
                }
                break;

            case R.id.locationgen:
                intent.setClassName("name.calvin.meetez", "name.calvin.meetez.adHocMeeting");
                startActivity(intent);
                return true;
        }
        return false;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backout:
                Dialogbox dialog = new Dialogbox();
                dialog.dialog(this);
        }


    }


    private class SendtoPHP extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            String text = "";
            try {
                URL url = new URL(urls[0]);
                URLConnection conn = url.openConnection();
                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                System.out.println("test success!");

                text = sb.toString();
                System.out.println(text);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    return text;
                } catch (Exception e) {
                    System.out.println(e);
                    return "Error selecting record!";
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            String toStringResult = "";

        }
    }
}

