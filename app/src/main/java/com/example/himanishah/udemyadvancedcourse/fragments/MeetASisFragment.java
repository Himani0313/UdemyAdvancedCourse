package com.example.himanishah.udemyadvancedcourse.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.himanishah.udemyadvancedcourse.R;
import com.example.himanishah.udemyadvancedcourse.entities.Sister;
import com.example.himanishah.udemyadvancedcourse.views.MeetASisViews.MeetASisAdapter;

import java.util.ArrayList;

/**
 * Created by himanishah on 8/19/17.
 */

public class MeetASisFragment extends Fragment implements MeetASisAdapter.OnSisterClicked {

    private final String LOG_TAG = MeetASisFragment.class.getSimpleName();
    private MeetASisAdapter aSisAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Sister> sisters;

    public  static MeetASisFragment newInstance(){
        return new MeetASisFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meet_a_sis,container,false);
        aSisAdapter = new MeetASisAdapter((AppCompatActivity) getActivity(), this);
        sisters = aSisAdapter.getSisters();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_meet_a_sis_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        setUpAdapter();
        getSisters(sisters);
        return rootView;
    }

    private void setUpAdapter(){
        if(isAdded()){
            recyclerView.setAdapter(aSisAdapter);
        }
    }
    @Override
    public void onSisterClicked(Sister sister) {
        Log.d(LOG_TAG,sister.getSisterName() + " was clicked");

    }
    private ArrayList<Sister> getSisters(ArrayList<Sister> sisters){
        for(int i=0; i<32;i++){
            sisters.add(new Sister(
                    i,
                    "Sister " + i,
                    "Joined for this reason",
                    "http://www.gravatar.com/avatar/" + i + "?d=identicon",
                    "Computer science",
                    "Spring 2013",
                    "I love to code my heart out"
            ));
        }
        return sisters;
    }
}