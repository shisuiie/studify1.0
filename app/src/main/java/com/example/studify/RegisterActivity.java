package com.example.studify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveanaccount;
    EditText inputEmail, inputPassword, inputComfirmpassword;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveanaccount=findViewById(R.id.alreadyHaveanaccount);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputComfirmpassword=findViewById(R.id.inputConfirmpassword);
        btnRegister=findViewById(R.id.btnRegister);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();






        alreadyHaveanaccount.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this,MainActivity.class)));

        btnRegister.setOnClickListener(view -> PerforAuth());
    }

    private void PerforAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputComfirmpassword.getText().toString();

        if (!email.matches(emailPattern))

        {
            inputEmail.setError("Enter Valid Email address");
        }else if (password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter Proper Password");
        }else if (!password.equals(confirmPassword))
        {
            inputComfirmpassword.setError("Password does not match");
        }else
        {
            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                task.isSuccessful();
                {
                    progressDialog.dismiss();
                    sendUserToNextActivity();
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                }
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            });


        }


    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}