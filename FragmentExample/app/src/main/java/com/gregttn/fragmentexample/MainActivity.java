package com.gregttn.fragmentexample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gregttn.fragmentexample.fragments.InjectedFragmentOne;
import com.gregttn.fragmentexample.fragments.InjectedFragmentTwo;
import com.gregttn.fragmentexample.fragments.ShowSelectionFragment;
import com.gregttn.fragmentexample.fragments.ShowSelectionFragment.SelectedFragment;

public class MainActivity extends AppCompatActivity implements ShowSelectionFragment.OnFragmentSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.dynamic_fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            showFragment(SelectedFragment.ONE);
        }
    }

    @Override
    public void onFragmentSelected(SelectedFragment selectedFragment) {
        showFragment(selectedFragment);
    }

    private void showFragment(SelectedFragment selectedFragment) {
        Fragment fragment = getFragmentToShow(selectedFragment);
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.dynamic_fragment_container, fragment)
            .commit();
    }

    private Fragment getFragmentToShow(SelectedFragment selectedFragment) {
        switch (selectedFragment) {
            case ONE: return new InjectedFragmentOne();
            case TWO: return new InjectedFragmentTwo();
            default:  return new InjectedFragmentOne();
        }
    }
}
