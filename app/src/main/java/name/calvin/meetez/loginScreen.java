package name.calvin.meetez;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class loginScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        EditText password = (EditText) findViewById(R.id.PasswordEditText);
        password.setTransformationMethod(new PasswordTransformationMethod());
    }
}
