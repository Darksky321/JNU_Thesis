package com.jnu.thesis.fragment;

import com.jnu.thesis.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MeFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_me, null) ;
		return v ;
	}
}