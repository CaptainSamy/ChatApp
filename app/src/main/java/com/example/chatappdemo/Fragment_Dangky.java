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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_Dangky extends Fragment {
    private Button register_button;
    private EditText register_Username, register_Phone, register_Email, register_Password, register_ConfirmPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private ProgressDialog loadingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dangky, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        loadingBar = new ProgressDialog(getActivity());

        register_button = view.findViewById(R.id.register_button);
        register_Username = view.findViewById(R.id.register_username);
        register_Phone = view.findViewById(R.id.register_phone);
        register_Email = view.findViewById(R.id.register_email);
        register_Password = view.findViewById(R.id.register_password);
        register_ConfirmPassword = view.findViewById(R.id.register_confirm_password);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccout();
            }
        });
        return view;
    }

    private void CreateNewAccout() {
        String username = register_Username.getText().toString();
        String phone = register_Phone.getText().toString();
        String email = register_Email.getText().toString();
        String password = register_Password.getText().toString();
        String confirmpassword = register_ConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getActivity(), "Please enter username!",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "Please enter phone!",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Please enter email!",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter password!",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(getActivity(), "Please enter confirm password!",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}
