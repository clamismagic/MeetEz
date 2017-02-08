package name.calvin.meetez;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Dialogbox {

    Context pref;

    public void dialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to back out of this event?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                SharedPreferences prefs = pref.getSharedPreferences("eventName", 0);
                String eventName = prefs.getString("eventName", null);
                if (eventName != null) {
                    Toast.makeText(context, "You left " + eventName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "You left the event", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    ;

    public void newUser(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Welcome to MeetEz. Please enter your name and phone number");
        builder.setTitle("MeetEz");
        final EditText name = new EditText(context);
        final EditText phoneNo = new EditText(context);
        do {
            builder.setView(name);
            builder.setView(phoneNo);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    SendtoPHP sendtoPHP = new SendtoPHP();
                    sendtoPHP.execute(new String[]{
                            "https://mappdb-clamismagic.rhcloud.com/createContacts.php?name="+name + "&contactNo="+phoneNo
                    });
                    dialog.dismiss();
                    return;
                }

            });
        } while (name.getText() == null || phoneNo.getText() == null);
    }

    ;

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

            }

        }

    public void participants (final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to remove this participant?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                {
                    Toast.makeText(context, "You have removed the participant", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    }


