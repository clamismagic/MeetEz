package name.calvin.meetez;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Participants extends Activity implements OnClickListener{

    private ImageButton removeParticipant;
    private Button addParticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participants);
        removeParticipant=(ImageButton)findViewById(R.id.removeParticipant);
        removeParticipant.setOnClickListener(this);
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
}
