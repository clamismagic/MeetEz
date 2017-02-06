package name.calvin.meetez;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

import static android.Manifest.permission.READ_CONTACTS;

public class AddParticipant extends Activity implements OnClickListener {

    private Button add;
    static final Integer CONTACTS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        askForPermission(READ_CONTACTS, CONTACTS);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        add.setEnabled(false);
        ContentResolver contactResolver = getContentResolver();
        Cursor cursor = contactResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", new String[]{"*"}, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }while (cursor.moveToNext() );
        }
        CheckBox checkBox = (CheckBox)findViewById(R.id.addParticipant);
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
}
