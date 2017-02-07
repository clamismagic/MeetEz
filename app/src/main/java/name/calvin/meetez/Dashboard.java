package name.calvin.meetez;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Dashboard extends Activity implements OnClickListener {

    private String[] recordArray, resultArray;
    private String[][] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        //backout = (Button) findViewById(R.id.backout);
        //backout.setVisibility(View.GONE);
        //backout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        File dbFile = this.getDatabasePath(Constants.EVENTS_TABLE);
        if (!dbFile.exists()) {
            // TODO make dialog box for new user signup
            Dialogbox newUser = new Dialogbox();
            newUser.newUser(this);
        } else {
            EventsData events = new EventsData(this);
            EventsMethods eventsMethods = new EventsMethods();
            String resultSQLite = eventsMethods.showEvents(eventsMethods.getEvents(events));
            String[] records = resultSQLite.split("\\n");
            int i = 0;
            for (String eachRecord : records) {
                values[i++] = eachRecord.split("\\t");
            }
        }

        SendtoPHP sendtoPHP = new SendtoPHP();
        sendtoPHP.execute("https://mappdb-clamismagic.rhcloud.com/select.php?tablename=events%20e,eventContacts%20ec,contacts%20c%20where%20e.eventID%20=%20ec.eventID%20and%20c.contactID%20=%20ec.contactID%20and%20c.contactNo%20=" + values[0][6]);
    }

    private OnClickListener click_listener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            // TODO implement putExtra into intent and delete switch statements
            Intent intent = null;
            switch (view.getId()) {
                case R.id.meetingevent1:
                    intent = new Intent(getApplicationContext(), Description.class);
                    startActivity(intent);
                    break;
            }
        }

    };

    /*     Making the button appear when text is long pressed */
    private OnLongClickListener long_click_listener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            // meetingevent1.;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            //noinspection ResourceType
            layoutParams.leftMargin = -100;
            view.setLayoutParams(layoutParams);
            // declare backout button here
            //backout.setVisibility(View.VISIBLE);
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
                intent.setClassName("name.calvin.meetez", "name.calvin.meetez.CreateEvent");
                startActivity(intent);
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
            /*case R.id.backout:
                Dialogbox dialog = new Dialogbox();
                dialog.dialog(this);
        }*/
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
                        return null;
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.dashboardrelative);
                if (result == null) {
                    TextView noEvent = new TextView(Dashboard.this);
                    noEvent.setText(R.string.noevents);
                    noEvent.setId(R.id.noevents);
                    relativeLayout.addView(noEvent);
                    return;
                }
                recordArray = result.split("\\?");
                int i = 0;
                for (String singleRecord : recordArray) {
                    resultArray = singleRecord.split("\\|");
                    TextView meetingevent = new TextView(Dashboard.this);
                    meetingevent.setText(resultArray[1]);
                    meetingevent.setId(10000 + i++);
                    meetingevent.setOnLongClickListener(long_click_listener);
                    meetingevent.setOnClickListener(click_listener);
                    relativeLayout.addView(meetingevent);
                }
                SharedPreferences prefs = getSharedPreferences("eventName", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEdit = prefs.edit();
                prefsEdit.putString("eventName", resultArray[1]);
                prefsEdit.commit();
            }
        }
    }


