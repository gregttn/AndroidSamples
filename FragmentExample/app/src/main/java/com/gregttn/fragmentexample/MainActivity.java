package com.gregttn.fragmentexample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private CurrentlyDisplayedFragment currentlyDisplayedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.swap_fragments);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragments();
            }
        });

        if(findViewById(R.id.dynamic_fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }

            InjectedFragmentOne fragmentOne = new InjectedFragmentOne();
            fragmentOne.setArguments(getIntent().getExtras());

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.dynamic_fragment_container, fragmentOne)
                    .commit();

            currentlyDisplayedFragment = CurrentlyDisplayedFragment.INJECTED_FRAGMENT_ONE;
        }
    }

    private void swapFragments() {
        Fragment fragment = getFragmentToShow();
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.dynamic_fragment_container, fragment)
            .commit();
    }

    private Fragment getFragmentToShow() {
        if (CurrentlyDisplayedFragment.INJECTED_FRAGMENT_ONE.equals(currentlyDisplayedFragment)) {
            currentlyDisplayedFragment = CurrentlyDisplayedFragment.INJECTED_FRAGMENT_TWO;
            return new InjectedFragmentTwo();
        }

        currentlyDisplayedFragment = CurrentlyDisplayedFragment.INJECTED_FRAGMENT_ONE;
        return new InjectedFragmentOne();
    }

    private enum CurrentlyDisplayedFragment {
        INJECTED_FRAGMENT_ONE,
        INJECTED_FRAGMENT_TWO;
    }
}
