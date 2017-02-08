package name.calvin.meetez;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Dashboard extends Activity {

    private String[] resultArray;
    private ArrayList<String[]> values = new ArrayList<>();
    private ArrayList<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    @Override
    protected void onResume() {
        super.onResume();
        values.clear();
        resultArray = null;
        textViews.clear();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dashboardlinear);
        linearLayout.removeAllViews();
        SQLiteDatabase db = null;
        EventsMethods eventsMethods = new EventsMethods();
        EventsData events = new EventsData(this);
        String resultSQLite = eventsMethods.showEvents(eventsMethods.getEvents(events));
        if (!resultSQLite.equals("")) {
            String[] records = resultSQLite.split("\\n");
            for (String eachRecord : records) {
                values.add(eachRecord.split("\\t"));
            }
            System.out.println(values.get(0)[2]);
            if (isConnectedToInternet()) {
                SendtoPHP sendtoPHP = new SendtoPHP();
                sendtoPHP.execute("https://mappdb-clamismagic.rhcloud.com/select.php?tablename=events%20e,eventContacts%20ec,contacts%20c%20where%20e.eventID%20=%20ec.eventID%20and%20c.contactID%20=%20ec.contactID%20and%20c.contactNo%20=" + values.get(0)[6]);
            } else {
                int i;
                for (i = 1; i < values.size(); i++) {
                    TextView meetingevent = new TextView(this);
                    meetingevent.setText(values.get(i)[1]);
                    linearLayout.addView(meetingevent);
                    textViews.add(meetingevent);
                }
                if (i == 1) {
                    TextView noEvent = new TextView(Dashboard.this);
                    noEvent.setText(R.string.noevents);
                    noEvent.setId(R.id.noevents);
                    linearLayout.addView(noEvent);
                }
            }
        } else {
            Dialogbox newUser = new Dialogbox();
            newUser.newUserName(this);
            eventsMethods.addEvent("Filler event", "2000-01-01", "0000", "Filler Venue", "Filler Description", "Filler Participants", "Filler contact", events);
            TextView noEvent = new TextView(Dashboard.this);
            noEvent.setText(R.string.noevents);
            noEvent.setId(R.id.noevents);
            linearLayout.addView(noEvent);
        }
    }

    private OnClickListener click_listener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Description.class);
            intent.putExtra("eventName", ((TextView) view).getText());
            startActivity(intent);
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

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
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
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dashboardlinear);
            try {
                String[] recordArray = result.split("\\?");
                int i = 0;
                for (String singleRecord : recordArray) {
                    resultArray = singleRecord.split("\\|");
                    TextView meetingevent = new TextView(Dashboard.this);
                    meetingevent.setText(resultArray[1]);
                    meetingevent.setId(10000 + i++);
                    meetingevent.setOnClickListener(click_listener);
                    linearLayout.addView(meetingevent);
                    textViews.add(meetingevent);
                }
                SharedPreferences prefs = getSharedPreferences("eventName", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEdit = prefs.edit();
                prefsEdit.putString("eventName", resultArray[1]);
                prefsEdit.commit();
            } catch (ArrayIndexOutOfBoundsException e) {
                TextView noEvent = new TextView(Dashboard.this);
                noEvent.setText(R.string.noevents);
                noEvent.setId(R.id.noevents);
                linearLayout.addView(noEvent);
            }
        }
    }
}


