package com.example.himanishah.udemyadvancedcourse.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.himanishah.udemyadvancedcourse.R;

/**
 * Created by himanishah on 8/19/17.
 */

public class RushFragment extends Fragment {
    public  static RushFragment newInstance(){
        return new RushFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rush,container,false);
        return rootView;
    }
}