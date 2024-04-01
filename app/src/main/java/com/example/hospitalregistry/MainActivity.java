package com.example.hospitalregistry;

import android.content.Intent;
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
import com.example.hospitalregistry.fragments.autorization.NonAuthorizedFragment;
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
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            int itemId = menuItem.getItemId();
//            replaceFragment(new HomeFragment());
            if (itemId == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.clinics) {
                replaceFragment(new ClinicsFragment());
            }
//            if (itemId == R.id.queue) {
//                replaceFragment(new QueueFragment());
//            } else if (itemId == R.id.research) {
//                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//                    // Пользователь не авторизован
//                    // Переход на NonAuthorizedFragment
//                    replaceFragment(new NonAuthorizedFragment());
//                } else {
//                    // Пользователь авторизован
//                    replaceFragment(new ResearchFragment());
//                }
//            } else if (itemId == R.id.call) {
//                replaceFragment(new CallFragment());
//            } else if (itemId == R.id.services) {
//                replaceFragment(new ServicesFragment());
//            } 
            else if (itemId == R.id.profile) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    // Пользователь не авторизован
                    replaceFragment(new NonAuthorizedFragment());
                } else {
                    // Пользователь авторизован
                    replaceFragment(new PersonFragment());
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

    public void onArchiveButtonClick(View view)
    {
        // выводим сообщение
        System.out.println("onArchiveButtonClick");
    }

    public void onSupportButtonClick(View view)
    {
        // выводим сообщение
        System.out.println("onSupportButtonClick");
    }

    public void onSettingsButtonClick(View view)
    {
        // Переход на SettingsActivity
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToRegistration(View view){
        replaceFragment(new RegistrationFragment());
    }

    public void goToLogin(View view){
        replaceFragment(new NonAuthorizedFragment());
    }
}
