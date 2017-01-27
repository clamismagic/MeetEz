package name.calvin.meetez;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnLongClickListener;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class Dashboard extends Activity implements OnClickListener {

    private Button backout;
    private TextView meetingevent1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        backout=(Button)findViewById(R.id.backout);
        backout.setVisibility(View.GONE);
        backout.setOnClickListener(this);
        meetingevent1 = (TextView) findViewById(R.id.meetingevent1);
        meetingevent1.setOnLongClickListener(long_click_listener);
        meetingevent1.setOnClickListener(click_listener);
    }

    private OnClickListener click_listener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = null;
            switch (view.getId()) {
                case R.id.meetingevent1:
                    intent = new Intent(getApplicationContext(), Description.class);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    break;
            }
        }

    };

    /*     Making the button appear when text is long pressed */
    private OnLongClickListener long_click_listener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
           // meetingevent1.;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) meetingevent1.getLayoutParams();
            //noinspection ResourceType
            layoutParams.leftMargin = -100;
            meetingevent1.setLayoutParams(layoutParams);
            backout.setVisibility(View.VISIBLE);
            return true;
        }
    };

    /* making the hamburger */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateprofile:
                //startActivity(new Intent(this, .class));
                return true;
            //more items go here, if any...
            case R.id.createevent:

                return true;

            case R.id.locationgen:

                return true;
        }
        return false;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to back out of this event?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

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
}

