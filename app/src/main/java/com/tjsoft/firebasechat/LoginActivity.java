package com.tjsoft.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tjsoft.firebasechat.model.FirebaseDB;
import com.tjsoft.firebasechat.model.entities.User;


public class LoginActivity extends AppCompatActivity {

//    @BindView(R.id.etMail)
    EditText etMail;

//    @BindView(R.id.etPassword)
    EditText etPassword;

//    @BindView(R.id.etRPassword)
    EditText etEtPassword;

//    @BindView(R.id.btRegister)
    Button btRegister;

//    @BindView(R.id.btSingInd)
    Button btSingIn;

    private FirebaseAuth mAuth;

    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mAuth = FirebaseAuth.getInstance();

        configureUI();
    }

    private void configureUI() {
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        etEtPassword = findViewById(R.id.etRPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            // Name, email address, and profile photo Url
            Intent intent = new Intent(this, MainActivity.class);
            User usr = new User();
            usr.setDisplayName(user.getDisplayName());
            usr.setEmail(user.getEmail());
            usr.setPhotoUrl((user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png");
            usr.setVerifyEmail(user.isEmailVerified());
            usr.setUid(user.getUid());
            intent.putExtra("myUser", usr);
            startActivity(intent);
        }
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btRegister :
                registerUser();
                break;
            case R.id.btSingInd :
                singIn();
                break;
        }
    }

    private void singIn() {

        String email = etMail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.trim().isEmpty() || password.trim().isEmpty()){
            Toast.makeText(this, "Debe colocar correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        FirebaseDB db = new FirebaseDB();
                        db.saveUser(user);

                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
    }

    private void registerUser() {

        String email = etMail.getText().toString();
        String password = etPassword.getText().toString();
        String repassword = etPassword.getText().toString();

        if (email.trim().isEmpty() || password.trim().isEmpty() || repassword.trim().isEmpty()){
            Toast.makeText(this, "Debe colocar correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.trim().equals(repassword.trim())){
            Toast.makeText(this, "Las contraseñas deben de ser iguales", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        FirebaseDB db = new FirebaseDB();
                        db.saveUser(user);

                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });

    }

    private void googleSingIn(){

    }
}
