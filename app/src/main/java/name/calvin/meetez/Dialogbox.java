package name.calvin.meetez;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.view.View;



public class Dialogbox {

    Context pref;

    public void dialog(final Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to back out of this event?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    SharedPreferences prefs = pref.getSharedPreferences("eventName", 0);
                    String eventName = prefs.getString("eventName", "Error");
                    Toast.makeText(context, "You left "+ eventName, Toast.LENGTH_SHORT).show();
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

    };
}
