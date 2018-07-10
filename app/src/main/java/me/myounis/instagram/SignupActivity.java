package me.myounis.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public EditText usernameInput;
    public EditText handleInput;
    public EditText emailInput;
    public EditText passwordInput;
    public Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = (EditText) findViewById(R.id.etUsername);
        handleInput = (EditText) findViewById(R.id.etHandle);
        emailInput = (EditText) findViewById(R.id.etEmail);
        passwordInput = (EditText) findViewById(R.id.etPassword);
        signupBtn = (Button) findViewById(R.id.btnSignup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String email = emailInput.getText().toString();
                final String handle = handleInput.getText().toString();

                signup(username, handle, email, password);
            }
        });
    }
    private void signup(String username, String handle, String email, String password)
    {

        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Set custom properties
        user.put("handle", handle);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.d("SignupActivity", "Signup Successful!");
                    final Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("SignupActivity", "Signup Failed!");
                    e.printStackTrace();
                }
            }
        });
    }
}
