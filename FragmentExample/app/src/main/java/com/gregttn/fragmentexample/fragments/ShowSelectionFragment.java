package com.gregttn.fragmentexample.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.gregttn.fragmentexample.R;

public class ShowSelectionFragment extends Fragment {
    private OnFragmentSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_selection, container, false);

        RadioGroup selectedFragmentRadioGroup = (RadioGroup) view.findViewById(R.id.select_fragment_radio_group);
        selectedFragmentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                fragmentSelected(checkedId);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnFragmentSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException(getClass() + " does not implement " + OnFragmentSelectedListener.class, e);
        }
    }

    private void fragmentSelected(int checkedId) {
        SelectedFragment selectedFragment = R.id.one_radio_button == checkedId ? SelectedFragment.ONE : SelectedFragment.TWO;
        listener.onFragmentSelected(selectedFragment);
    }

    public interface OnFragmentSelectedListener {
         void onFragmentSelected(SelectedFragment selectedFragment);
    }

    public enum SelectedFragment {
        ONE,
        TWO;
    }
}
