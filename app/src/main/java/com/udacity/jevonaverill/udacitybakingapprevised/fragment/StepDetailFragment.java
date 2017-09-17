package com.udacity.jevonaverill.udacitybakingapprevised.fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.jevonaverill.udacitybakingapprevised.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class StepDetailFragment extends Fragment {

    private static final String TAG = "StepDetailFragment";

    @BindView(R.id.tv_description)
    TextView descriptionTextView;

    @BindString(R.string.key_step_description)
    String STEP_DESCRIPTION;
    @BindString(R.string.key_step_has_video)
    String STEP_HAS_VIDEO;

    private Unbinder mUnbinder;
    private String description;
    private Bundle args;
    private boolean stepHasVideo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        description = args.getString(STEP_DESCRIPTION);
        stepHasVideo = args.getBoolean(STEP_HAS_VIDEO);

        boolean orientationIsPortrait = this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;
        Log.d(TAG, "onCreateView: orientationIsPortrait = " + orientationIsPortrait);

        if (!orientationIsPortrait && stepHasVideo) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(description);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STEP_DESCRIPTION, description);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
