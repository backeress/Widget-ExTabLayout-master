package com.sung.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sung.R;


/**
 * Created by sung on 2017-10-31.
 */

public class FragmentThree extends Fragment
{

    //기본 생성자를 반드시 생성해주어야 하는 이유
    //http://itpangpang.tistory.com/267
    public FragmentThree()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_three, container, false);
        return layout;
    }
}