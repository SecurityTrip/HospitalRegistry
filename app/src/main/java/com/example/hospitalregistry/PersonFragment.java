package com.example.hospitalregistry;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.hospitalregistry.databinding.FragmentPersonBinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imageView;
    private FragmentPersonBinding binding;

    private TextView name;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_person, container, false);

        // Найдите ImageView по его id
        imageView = rootView.findViewById(R.id.imageView);

        // Создайте Drawable для плейсхолдера
        Drawable placeholderDrawable = getResources().getDrawable(R.drawable.avatar);

        // Установите плейсхолдер в ImageView
        imageView.setImageDrawable(placeholderDrawable);

        name = rootView.findViewById(R.id.nameField);
        name.setText(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());

        // URL вашего аватара
        String avatarUrl = "https://tommystinctures.com/wp-content/uploads/2020/10/avatar-icon-placeholder-1577909.jpg";

        // Загрузите изображение с использованием Picasso и установите его в ImageView
        Picasso.get().load(avatarUrl).placeholder(placeholderDrawable).into(imageView);

        return rootView;
    }

}