package com.diamondTierHuggers.hugMeCampus;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private FirebaseAuth mAuth;
        private FirebaseUser mUser;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            //Logout Button
//            Preference logoutPreference = findPreference("LogoutButton");
//            assert logoutPreference != null;
//            logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    FirebaseAuth.getInstance().signOut();
//                    Intent LoginIntent = new Intent(SettingsFragment.this.getActivity(), LoginFragment.class);
//                    SettingsFragment.this.startActivity(LoginIntent);
//                    NavHostFragment.findNavController(SettingsFragment.this).
//                            navigate(R.id.nav_host_fragment_content_main);

//                    return true;
//                }
//            });

            //Delete Account Button
//            Preference deletePreference = findPreference("Delete Button");
//            assert deletePreference != null;
//            assert deletePreference != null;
//            deletePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    Intent i = new Intent(getActivity(), DeleteUser.class);
//                    startActivity(i);
//                    ((Activity) getActivity()).overridePendingTransition(0,0);

//                    Intent deleteIntent = new Intent(SettingsFragment.this.getActivity(), DeleteUser.class);
//                    SettingsFragment.this.startActivity(deleteIntent);
//                    startActivity(new Intent(preference.getContext(), DeleteUser.class));
//                    return true;
//                }
//            });

        }
    }




}