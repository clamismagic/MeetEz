package name.calvin.meetez;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Participants extends Activity implements OnClickListener{

    private ImageButton removeParticipant;
    private Button addParticipant;
    private String[] recordArray, resultArray;
    private TextView outputText;
    public ArrayList<Integer> textViews = new ArrayList<>();
    String eventName = getIntent().getExtras().getString("eventName");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participants);
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.addparticipantPage);
        SendtoPHP sendtoPHP = new SendtoPHP();
        sendtoPHP.execute(new String[]{
                "https://mappdb-clamismagic.rhcloud.com/select.php?tablename=eventContacts%20ec,contacts%20c%20where%ec.eventID=(select%20eventID%20from%20events%20where%20eventName=\"" + eventName + "\"&ec.contactID=c.contactID)"
        });
        for (int i = 0; i < resultArray.length; i ++){
            outputText = new TextView(this);
            outputText.setText(resultArray[i]);
            outputText.setId(2000 + i);
            outputText.setTextAppearance(this, R.style.ParticipantName);
            textViews.add(2000 + i);
            mLinearLayout.addView(outputText);
            removeParticipant = new ImageButton(this);
            removeParticipant.setId(3000 + i);
            removeParticipant.setImageResource(R.drawable.removebutton);
            mLinearLayout.addView(removeParticipant);
            removeParticipant.setOnClickListener(this);
        }
        addParticipant = new Button(Participants.this);
        addParticipant.setText(R.string.addParticipant);
        addParticipant.setId(R.id.add);
        mLinearLayout.addView(addParticipant);
        addParticipant.setOnClickListener(Participants.this);
        addParticipant=(Button)findViewById(R.id.addParticipant);
        addParticipant.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeParticipant:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to remove this participant from the event?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(getBaseContext(),"You removed the participant." ,  Toast.LENGTH_SHORT).show();
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
            case R.id.addParticipant:
                Intent intent = new Intent(getApplicationContext(), AddParticipant.class);
                if (intent != null){
                    startActivity(intent);
                }
                break;
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
                TextView Participant = (TextView) findViewById(R.id.participantName);
                Participant.append(resultArray[0]);
            }
        }
    }
}
