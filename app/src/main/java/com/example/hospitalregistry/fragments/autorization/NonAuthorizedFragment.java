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

import com.example.hospitalregistry.PersonFragment;
import com.example.hospitalregistry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class NonAuthorizedFragment extends Fragment {

    EditText editTextEmail, editTextPassword;
    Button LoginButton;
    String email, password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    public NonAuthorizedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_non_authorized, container, false);

        LoginButton = (Button) RootView.findViewById(R.id.LoginButton);
        editTextEmail = (EditText)RootView.findViewById(R.id.LoginEmailField);
        editTextPassword = (EditText)RootView.findViewById(R.id.LoginPasswordField);
        progressBar = (ProgressBar)RootView.findViewById(R.id.LoginProgressBar);

        LoginButton.setOnClickListener(new View.OnClickListener() {
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
                                        fragmentTransaction.replace(R.id.frame_layout, new PersonFragment());
                                        fragmentTransaction.commit();
                                    } else {
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