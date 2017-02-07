package name.calvin.meetez;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Description extends Activity implements OnClickListener {

    private Button delete;
    private Button editParticipants;
    private String[] recordArray, resultArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        editParticipants = (Button) findViewById(R.id.editparticipants);
        editParticipants.setOnClickListener(click_listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SendtoPHP sendtoPHP = new SendtoPHP();
        sendtoPHP.execute(new String[]{
                "https://mappdb-clamismagic.rhcloud.com/select.php?tablename=events"
        });
    }

    private OnClickListener click_listener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = null;
            switch (view.getId()) {
                case R.id.editparticipants:
                    intent = new Intent(getApplicationContext(), Participants.class);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    break;
            }
        }

    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:
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
                    return null;
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                return;
            }
            recordArray = result.split("\\?");
            for (String singleRecord : recordArray) {
                resultArray = singleRecord.split("\\|");
                TextView Date = (TextView) findViewById(R.id.date);
                TextView Time = (TextView) findViewById(R.id.time);
                TextView Venue = (TextView) findViewById(R.id.venue);
                TextView Desc = (TextView) findViewById(R.id.description);
                Date.append(resultArray[2]);
                Time.append(resultArray[0]);
                Venue.append(resultArray[3]);
                Desc.append(resultArray[4]);
            }
        }
    }
}