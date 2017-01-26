package name.calvin.meetez;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class Dashboard extends Activity {

    private Button backout;
    private TextView meetingevent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        backout=(Button)findViewById(R.id.backout);
        backout.setVisibility(View.GONE);
        meetingevent1 = (TextView) findViewById(R.id.meetingevent1);
        meetingevent1.setOnLongClickListener(click_listener);
    }

    private OnLongClickListener click_listener = new OnLongClickListener() {
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

}
