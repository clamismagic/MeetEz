package name.calvin.meetez;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class adHocMeeting extends Activity implements View.OnClickListener {

    private final int[] textViews = new int[10];
    private final int[] editTexts = new int[10];
    private final ArrayList<String> inputAddress = new ArrayList<>();
    private final ArrayList<double[]> LatLng = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_hoc_meeting);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Spinner spinner = (Spinner) findViewById(R.id.choosepaxspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choosepaxspinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.adhocmeeting);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for (int i = 0; i < textViews.length; i++) {
                    if (textViews[i] == 0) {
                        break;
                    }
                    linearLayout.removeView(findViewById(textViews[i]));
                    linearLayout.removeView(findViewById(editTexts[i]));
                    textViews[i] = 0;
                    editTexts[i] = 0;
                }

                linearLayout.removeView(findViewById(R.id.submitButton));

                if (pos != 0) {
                    int item = Integer.parseInt(adapterView.getItemAtPosition(pos).toString());
                    for (int i = 0; i < item; i++) {
                        TextView addtextview = new TextView(adHocMeeting.this);
                        addtextview.setText(R.string.address);
                        addtextview.setText(addtextview.getText() + " " + (i + 1) + ":");
                        addtextview.setId(1000 + i);
                        textViews[i] = 1000 + i;
                        linearLayout.addView(addtextview, layoutParams);
                        EditText addedittext = new EditText(adHocMeeting.this);
                        addedittext.setId(2000 + i);
                        editTexts[i] = 2000 + i;
                        linearLayout.addView(addedittext, layoutParams);
                    }
                    Button submitButton = new Button(adHocMeeting.this);
                    submitButton.setText(R.string.submit);
                    submitButton.setId(R.id.submitButton);
                    linearLayout.addView(submitButton, layoutParams);
                    submitButton.setOnClickListener(adHocMeeting.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onClick (View v) {
        for (int i : editTexts) {
            if (i == 0) {
                break;
            }
            String input = ((EditText) findViewById(i)).getText().toString();
            inputAddress.add(input);
        }

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        for (String strAddress : inputAddress) {
            System.out.println(strAddress);
            try {
                addresses = geocoder.getFromLocationName(strAddress, 5);
                Address location = addresses.get(0);
                double[] getLatLng = {location.getLatitude(), location.getLongitude()};
                System.out.println(getLatLng[0] + "," + getLatLng[1]);
                LatLng.add(getLatLng);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
