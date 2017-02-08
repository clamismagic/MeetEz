package name.calvin.meetez;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
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

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

public class AddParticipant extends Activity implements OnClickListener {

    private Button add;
    static final Integer CONTACTS = 0x1;
    public TextView outputText;
    public CheckBox checkBox;
    public ArrayList<Integer> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        askForPermission(READ_CONTACTS, CONTACTS);
        fetchContacts();
        //CheckBox checkBox = (CheckBox)findViewById(R.id.addParticipant);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    add.setEnabled(true);
                }else {
                    add.setEnabled(false);
                }
            }
        });
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        add.setEnabled(false);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Toast.makeText(getBaseContext(), "You just added new participant(s).", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(AddParticipant.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddParticipant.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(AddParticipant.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(AddParticipant.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchContacts() {

        String phoneNumber = null;
        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

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

                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

                    while (emailCursor.moveToNext()) {

                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                        output.append(email);

                    }

                    emailCursor.close();
                }

                output.append("|");
            }
            String[] results = output.toString().split("\\|");
            LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.addparticipantPage);
            for (int i = 0; i < results.length; i ++){
                outputText = new TextView(this);
                outputText.setText(results[i]);
                outputText.setId(2000 + i);
                textViews.add(2000 + i);
                mLinearLayout.addView(outputText);
                checkBox = new CheckBox(this);
                checkBox.setId(3000 + i);
                mLinearLayout.addView(checkBox);
            }
//            outputText.setText(results[i]);
        }
    }
}
