package name.calvin.meetez;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

public class adHocMeeting extends Activity implements AdapterView.OnItemSelectedListener {

    private int[] textViews = new int[10];

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
                for (int i : textViews) {
                    if (i == 0) {
                        break;
                    } else {
                        linearLayout.removeView(findViewById(i));
                    }
                }

                for (int i = 0; i < textViews.length; i++) {
                    textViews[i] = 0;
                }

                if (pos != 0) {
                    int item = Integer.parseInt(adapterView.getItemAtPosition(pos).toString());
                    for (int i = 0; i < item; i++) {
                        TextView addtextview = new TextView(adHocMeeting.this);
                        addtextview.setText("Address " + (i + 1) + ":");
                        addtextview.setId(1000 + i);
                        textViews[i] = 1000 + i;
                        linearLayout.addView(addtextview);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(adHocMeeting.this, "Nothing is selected!", Toast.LENGTH_SHORT).show();
    }
}
