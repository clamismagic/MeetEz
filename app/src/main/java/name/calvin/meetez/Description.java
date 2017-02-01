package name.calvin.meetez;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Description extends Activity implements OnClickListener {

    private Button delete;
    private Button editParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        editParticipants = (Button) findViewById(R.id.editparticipants);
        editParticipants.setOnClickListener(click_listener);
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

}