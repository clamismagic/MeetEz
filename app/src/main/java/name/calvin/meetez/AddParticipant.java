package name.calvin.meetez;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Toast;

public class AddParticipant extends Activity implements OnClickListener {

    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);
        add.setEnabled(false);
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
                Intent intent = new Intent(getApplicationContext(), Participants.class);
                if (intent != null) {
                    startActivity(intent);
                }

                break;
        }
    }
}
