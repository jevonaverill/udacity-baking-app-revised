package com.udacity.jevonaverill.udacitybakingapprevised.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.jevonaverill.udacitybakingapprevised.R;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jevonaverill on 9/6/17.
 */

public final class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.StepViewHolder> {

    private List<Step> mStepList;
    private final StepClickListener mStepClickListener;

    public MasterListAdapter(Recipe mRecipe, StepClickListener stepClickListener) {
        mStepList = mRecipe.getSteps();
        mStepClickListener = stepClickListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.step_item_list, parent, false);
        final StepViewHolder holder = new StepViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepClickListener.onStepClick(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        String shortDescription = mStepList.get(position).getShortDescription();
        String stepNumber = (position == 0) ? "Ingredients" : "Step# " + position;
        holder.bind(stepNumber, shortDescription);
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }


    public interface StepClickListener {
        void onStepClick(int position);
    }


    class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_short_description)
        TextView shortDescriptionTextView;
        @BindView(R.id.tv_step_number)
        TextView stepNumberTextView;

        private StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String stepNumber, String shortDescription) {
            stepNumberTextView.setText(stepNumber);
            shortDescriptionTextView.setText(shortDescription);
        }
    }

}

