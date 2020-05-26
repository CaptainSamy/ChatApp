package com.example.chatappdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class Fragment_Dangnhap extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private Button login_Button;
    private EditText login_Email, login_Password;
    private TextView login_forgetPassword;
    private ProgressDialog loadingBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dangnhap, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        loadingBar = new ProgressDialog(getActivity());

        login_Button = view.findViewById(R.id.login_button);
        login_Email = view.findViewById(R.id.login_email);
        login_Password = view.findViewById(R.id.login_password);
        login_forgetPassword = view.findViewById(R.id.forget_password_link);

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });

        login_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private void AllowUserToLogin() {
        String email = login_Email.getText().toString();
        String password = login_Password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Please enter email!",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter password!",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getActivity(), "Logged in Successful...",Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        if (currentUser != null) {
            SendUserToMainActivity();
        }
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
    }
}
