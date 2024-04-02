package com.example.hospitalregistry.fragments.autorization;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hospitalregistry.R;
import com.example.hospitalregistry.fragments.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationFragment extends Fragment {
    EditText editTextEmail, editTextPassword;
    Button RegistrationButton;
    String email, password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_registration, container, false);

        editTextEmail = (EditText)RootView.findViewById(R.id.RegistrationEmailField);
        editTextPassword = (EditText)RootView.findViewById(R.id.RegistrationPasswordField);
        RegistrationButton = (Button) RootView.findViewById(R.id.RegistrationButton);
        progressBar = (ProgressBar)RootView.findViewById(R.id.RegistrationProgressBar);
        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                mAuth = FirebaseAuth.getInstance();

                if (email.isEmpty()){
                    Toast.makeText(getActivity(), "Email is empty",
                            Toast.LENGTH_SHORT).show();
                } else if (!email.contains("@")) {
                    Toast.makeText(getActivity(), "Email is uncorrect",
                            Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Password is empty",
                            Toast.LENGTH_SHORT).show();
                }
                if(email.isEmpty() || password.isEmpty()){


                }else{
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Registration successful.",
                                                Toast.LENGTH_SHORT).show();

                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        progressBar.setVisibility(View.GONE);
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            FragmentManager fm = getFragmentManager();
                                                            assert fm != null;
                                                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                            fragmentTransaction.replace(R.id.frame_layout, new ProfileFragment());
                                                            fragmentTransaction.commit();
                                                        }else{
                                                            FragmentManager fm = getFragmentManager();
                                                            assert fm != null;
                                                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                            fragmentTransaction.replace(R.id.frame_layout, new LoginFragment());
                                                            fragmentTransaction.commit();
                                                        }
                                                    }
                                                });

                                    } else {
                                        Toast.makeText(getActivity(), "Registration failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        // Inflate the layout for this fragment
        return RootView;
    }
}