package com.hvac.vtronix.ui.contactus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hvac.vtronix.R;

public class ContactUsFragment extends Fragment {

    //private ContactUsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // notificationsViewModel = ViewModelProviders.of(this).get(ContactUsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_contact_us, container, false);

//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
