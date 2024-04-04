package com.example.hospitalregistry;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hospitalregistry.databinding.ActivityMainBinding;
import com.example.hospitalregistry.fragments.ClinicsFragment;
import com.example.hospitalregistry.fragments.HomeFragment;
import com.example.hospitalregistry.fragments.ProfileFragment;
import com.example.hospitalregistry.fragments.autorization.LoginFragment;
import com.example.hospitalregistry.fragments.autorization.RegistrationFragment;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        com.example.hospitalregistry.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String source = getIntent().getStringExtra("source");
        if (source != null && source.equals("SettingsActivity")) {
            replaceFragment(new ProfileFragment());
            binding.bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
        } else {
            replaceFragment(new HomeFragment());
        }



        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            int itemId = menuItem.getItemId();
            if (itemId == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.clinics) {
                replaceFragment(new ClinicsFragment());
            } else if (itemId == R.id.profile) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    // Пользователь не авторизован
                    replaceFragment(new LoginFragment());
                } else {
                    // Пользователь авторизован
                    replaceFragment(new ProfileFragment());
                }
            }


            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void goToRegistration(View view){
        replaceFragment(new RegistrationFragment());
    }

    public void goToLogin(View view){
        replaceFragment(new LoginFragment());
    }
}
