package com.example.hospitalregistry;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hospitalregistry.fragments.AnalysisFragment;
import com.example.hospitalregistry.fragments.RegistrationsFragment;

import java.util.Objects;


public class UserInfoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String source = getIntent().getStringExtra("target");
        Intent intent = new Intent(this, MainActivity.class);

        switch (Objects.requireNonNull(source)){
            case "registration":
                replaceFragment(new RegistrationsFragment());
                break;
            case "analysis":
                replaceFragment(new AnalysisFragment());
                break;
            default:
                this.startActivity(intent);
                break;
        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}