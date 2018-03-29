package nyc.c4q.capstone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private EditText firstName_et, lastName_et, password_et, email_et, number_et;
    private Button upload_bt, submit_bt;
    private ImageView profilePic;
    private FirebaseAuth auth;
    private FirebaseUser newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.rounded_shape_dark_blue));
        getSupportActionBar().setTitle("Create Account");

        firstName_et = findViewById(R.id.name_et);
        lastName_et = findViewById(R.id.last_name_et);
        password_et = findViewById(R.id.pw_et);
        email_et = findViewById(R.id.email_et);
        upload_bt = findViewById(R.id.uploadPic_button);
        submit_bt = findViewById(R.id.create_account_bt);
        profilePic = findViewById(R.id.icon_iv);

        auth = FirebaseAuth.getInstance();

        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = email_et.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    email_et.setError("Required");
                } else {
                    email_et.setError(null);
                }

                String password = password_et.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    password_et.setError("Required");
                } else {
                    password_et.setError(null);
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "new account created?: YES!");
                                    newUser = auth.getCurrentUser();
                                    updateUI(newUser);
                                } else {
                                    Log.d(TAG, "new account created?: NOOOOO, exception is:" + task.getException());
                                    Toast.makeText(CreateAccountActivity.this, "Unable to create your account", Toast.LENGTH_SHORT).show();
                                    updateUI(null);

                                }
                            }
                        });
            }
        });

    }

    private void updateUI(FirebaseUser newUser) {
        if (newUser != null) {
            Toast.makeText(this, "Your account has been created!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
