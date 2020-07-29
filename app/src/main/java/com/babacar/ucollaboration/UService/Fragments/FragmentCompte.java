package com.babacar.ucollaboration.UService.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.babacar.ucollaboration.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCompte extends Fragment {


    public FragmentCompte() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.uservice_fragment_compte, container, false);
    }

}
