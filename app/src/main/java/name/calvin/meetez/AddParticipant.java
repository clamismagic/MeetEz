package name.calvin.meetez;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

public class AddParticipant extends Activity implements OnClickListener {

    private Button addButton;
    public TextView outputText;
    public CheckBox checkBox;
    public ArrayList<Integer> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        fetchContacts();
        addButton.setOnClickListener(this);
        addButton.setEnabled(false);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                CreateEvent.SendtoPHP sendtoPHP = new CreateEvent.SendtoPHP();
                sendtoPHP.execute(new String[]{
                        "https://mappdb-clamismagic.rhcloud.com/createEvents.php?eventName=" + eventName +"&date=" + date + "&time=" + time + "&venue=" + venue + "&description=" + description
                });
                Toast.makeText(getBaseContext(), "You just added new participant(s).", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }



    public void fetchContacts() {

        String phoneNumber = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        int num = 0;

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {

                    output.append(name);

                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n" + phoneNumber);

                    }

                    phoneCursor.close();
                }

                output.append("|");
            }
            String[] results = output.toString().split("\\|");
            LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.addparticipantPage);
            for (int i = 0; i < results.length; i ++){
                outputText = new TextView(this);
                outputText.setText(results[i]);
                outputText.setId(2000 + i);
                outputText.setTextAppearance(this, R.style.ParticipantName);
                textViews.add(2000 + i);
                mLinearLayout.addView(outputText);
                checkBox = new CheckBox(this);
                checkBox.setId(3000 + i);
                mLinearLayout.addView(checkBox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            addButton.setEnabled(true);
                        } else {
                            addButton.setEnabled(false);
                        }
                    }
                });
            }
            addButton = new Button(AddParticipant.this);
            addButton.setText(R.string.addParticipant);
            addButton.setId(R.id.add);
            mLinearLayout.addView(addButton);
            addButton.setOnClickListener(AddParticipant.this);
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

        }
    }
}
