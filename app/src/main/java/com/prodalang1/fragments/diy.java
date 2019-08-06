package com.prodalang1.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prodalang1.R;


public class diy extends Fragment {

    // Init
    private RecyclerView recyclerView;

    public diy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diy, container, false);

        recyclerView = view.findViewById(R.id.diy_rv);
        recyclerView.setHasFixedSize(true);

        return view;
    }

}
