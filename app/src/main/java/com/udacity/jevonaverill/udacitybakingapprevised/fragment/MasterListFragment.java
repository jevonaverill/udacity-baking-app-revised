package com.udacity.jevonaverill.udacitybakingapprevised.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.jevonaverill.udacitybakingapprevised.R;
import com.udacity.jevonaverill.udacitybakingapprevised.RecipeDetailActivity;
import com.udacity.jevonaverill.udacitybakingapprevised.adapter.MasterListAdapter;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class MasterListFragment extends Fragment {

    private Recipe mRecipe;

    @BindView(R.id.recycler_view_steps)
    RecyclerView mRecyclerView;

    MasterListAdapter mAdapter;
    private Unbinder mUnbinder;

    public MasterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mRecipe = ((RecipeDetailActivity) getActivity()).getSelectedRecipe();

        mAdapter = new MasterListAdapter(mRecipe, (MasterListAdapter.StepClickListener) getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
