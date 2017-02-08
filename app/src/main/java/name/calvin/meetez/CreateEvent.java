package name.calvin.meetez;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CreateEvent extends Activity implements View.OnClickListener {

    private Button createevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        createevent = (Button)findViewById(R.id.createevent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        createevent.setOnClickListener(CreateEvent.this);


    }

    @Override
    public void onClick(View view) {

        String eventName = getIntent().getExtras().getString("eventName");
        String date = ((EditText)findViewById(R.id.dateedit)).getText().toString();
        String time = ((EditText)findViewById(R.id.timeedit)).getText().toString();
        String venue = ((EditText)findViewById(R.id.venueedit)).getText().toString();
        String description = ((EditText)findViewById(R.id.descedit)).getText().toString();

        SendtoPHP sendtoPHP = new SendtoPHP();
        sendtoPHP.execute(new String[]{
                "https://mappdb-clamismagic.rhcloud.com/createEvents.php?eventName=" + eventName +"&date=" + date + "&time=" + time + "&venue=" + venue + "&description=" + description
        });
        EventsMethods eventsMethods = new EventsMethods();
        EventsData eventsData = new EventsData(this);
        eventsMethods.addEvent(eventName, date, time, venue, description, "", eventsMethods.showEvents(eventsMethods.getEvents(eventsData)).split("\\n")[0].split("\\t")[6], eventsData);
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
            if (result == null) {
                return;
            }
            Intent intent = new Intent(getApplicationContext(),Dashboard.class);
            startActivity(intent);

        }
    }
}
