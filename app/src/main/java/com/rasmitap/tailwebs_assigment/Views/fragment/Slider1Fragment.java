package com.rasmitap.tailwebs_assigment.Views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rasmitap.tailwebs_assigment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Slider1Fragment extends Fragment {

    TextView txtwelcomesauteez,txtsubtitle1;

    public Slider1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider1, container, false);

        txtwelcomesauteez = view.findViewById(R.id.txtwelcomesauteez);
        txtsubtitle1 = view.findViewById(R.id.txtsubtitle1);

        return view;
    }
    }


