package com.udacity.jevonaverill.udacitybakingapprevised;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toolbar;

import com.udacity.jevonaverill.udacitybakingapprevised.adapter.MasterListAdapter;
import com.udacity.jevonaverill.udacitybakingapprevised.fragment.StepDetailFragment;
import com.udacity.jevonaverill.udacitybakingapprevised.fragment.VideoFragment;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Ingredient;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Step;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class RecipeDetailActivity extends AppCompatActivity implements MasterListAdapter.StepClickListener {

    private static final String TAG = "RecipeDetailActivity";
    private static final String STEP_POSITION_KEY = "com.udacity.jevonaverill" +
            ".udacitybakingapprevised.RecipeDetailActivity.STEP_POSITION_KEY";
    private static final String STEP_RECIPE_KEY = "com.udacity.jevonaverill" +
            ".udacitybakingapprevised.RecipeDetailActivity.STEP_RECIPE_KEY";

    @BindString(R.string.key_step_description)
    String STEP_DESCRIPTION;
    @BindString(R.string.key_step_url)
    String STEP_URL;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    private Recipe mRecipe;
    private boolean isTwoPaneView;
    private int selectedIndex = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mRecipe = (Recipe) savedInstanceState.getSerializable(STEP_RECIPE_KEY);
            selectedIndex = savedInstanceState.getInt(STEP_POSITION_KEY);
        } else {
            mRecipe = (Recipe) getIntent().getSerializableExtra(STEP_RECIPE_KEY);
        }
        Log.d(TAG, "onCreate: recipe = " + mRecipe.getName() + " selectedIndex = " + selectedIndex);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        mToolbar.setTitle(mRecipe.getName());
        setActionBar(mToolbar);
        isTwoPaneView = (null != findViewById(R.id.detail_container_fragment));
        if (isTwoPaneView) showStepDetails();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_RECIPE_KEY, mRecipe);

        if (selectedIndex > 0) outState.putInt(STEP_POSITION_KEY, selectedIndex);
    }

    @Override public void onStepClick(int position) {
        selectedIndex = position;
        if (isTwoPaneView) showStepDetails();
        else {
            Intent intent = new Intent(this, RecipeStepActivity.class);

            intent.putExtra(STEP_RECIPE_KEY, mRecipe);
            intent.putExtra(STEP_POSITION_KEY, selectedIndex);
            startActivity(intent);
        }
    }

    private void showStepDetails() {
        FragmentManager fragmentMgr = getFragmentManager();

        Bundle args = new Bundle();
        Step step = mRecipe.getSteps().get(selectedIndex);

        String description = (selectedIndex == 0) ? getIngredientDescription(mRecipe
                .getIngredients()) : step.getDescription();

        args.putString(STEP_DESCRIPTION, description);
        args.putString(STEP_URL, step.getVideoUrl());

        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setArguments(args);
        fragmentMgr.beginTransaction()
                .replace(R.id.detail_container_fragment, detailFragment)
                .commit();

        // SHOW the FrameLayout if it's currently NOT VISIBLE
        if (!step.getVideoUrl().equals("")) {
            VideoFragment playerFragment = new VideoFragment();
            playerFragment.setArguments(args);
            fragmentMgr.beginTransaction()
                    .replace(R.id.video_fragment_container, playerFragment)
                    .commit();
        }
    }

    public Recipe getSelectedRecipe() {
        return mRecipe;
    }

    private String getIngredientDescription(List<Ingredient> ingredients) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            builder.append(getString(R.string.ingredient, ingredient.getQuantity(),
                    ingredient.getMeasure(), ingredient.getName()));
        }
        return builder.toString();
    }

}
