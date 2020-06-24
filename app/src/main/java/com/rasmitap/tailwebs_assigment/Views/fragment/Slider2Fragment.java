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
public class Slider2Fragment extends Fragment {

    TextView txtembarkyourjourney,txtsubtitle2;

    public Slider2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider2, container, false);

        txtembarkyourjourney = view.findViewById(R.id.txtembarkyourjourney);
        txtsubtitle2 = view.findViewById(R.id.txtsubtitle2);

        return view;
    }

}
