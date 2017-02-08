package name.calvin.meetez;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
    private String username;

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

    public void newUserName(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.newusername);
        builder.setTitle(R.string.app_name);
        final EditText name = new EditText(context);
        builder.setView(name);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                username = name.getText().toString();
                newUserPhone(context);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void newUserPhone(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.newuserphone);
        builder.setTitle(R.string.app_name);
        final EditText phoneNo = new EditText(context);
        builder.setView(phoneNo);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                String userphone = phoneNo.getText().toString();
                SendtoPHP sendtoPHP = new SendtoPHP();
                sendtoPHP.execute(new String[]{
                        "https://mappdb-clamismagic.rhcloud.com/createContacts.php?name=" + username + "&contactNo=" + userphone
                });

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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
        }

    }

    public void participants(final Context context) {
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


