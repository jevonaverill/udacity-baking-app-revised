package com.udacity.jevonaverill.udacitybakingapprevised;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.udacity.jevonaverill.udacitybakingapprevised.fragment.StepDetailFragment;
import com.udacity.jevonaverill.udacitybakingapprevised.fragment.VideoFragment;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Ingredient;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Step;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class RecipeStepActivity extends AppCompatActivity {

    private static final String TAG = "RecipeStepAcitivity";
    private static final String STEP_POSITION_KEY = "com.udacity.jevonaverill" +
            ".udacitybakingapprevised.RecipeDetailActivity.STEP_POSITION_KEY";
    private static final String STEP_RECIPE_KEY = "com.udacity.jevonaverill" +
            ".udacitybakingapprevised.RecipeDetailActivity.STEP_RECIPE_KEY";
    private Recipe mRecipe;
    private int selectedIndex = 0;

    @BindString(R.string.key_step_description)
    String STEP_DESCRIPTION;
    @BindString(R.string.key_step_url)
    String STEP_URL;
    @BindString(R.string.key_step_has_video)
    String STEP_HAS_VIDEO;

    @BindView(R.id.btn_next_step)
    Button nextButton;
    @BindView(R.id.btn_prev_step)
    Button prevButton;
    @BindView(R.id.btn_add_to_widget)
    Button widgetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mRecipe = (Recipe) getIntent().getSerializableExtra(STEP_RECIPE_KEY);
            selectedIndex = getIntent().getIntExtra(STEP_POSITION_KEY, 0);
            Log.d(TAG, "onCreate: recipe = " + mRecipe.getName());
        } else {
            mRecipe = (Recipe) savedInstanceState.getSerializable(STEP_RECIPE_KEY);
            selectedIndex = savedInstanceState.getInt(STEP_POSITION_KEY);
            Log.d(TAG, "onCreate: recipe = " + mRecipe.getName());

        }
        setButtonVisibility(selectedIndex);
        showStepDetails(selectedIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_RECIPE_KEY, mRecipe);
        outState.putInt(STEP_POSITION_KEY, selectedIndex);
    }

    private void showStepDetails(int stepPosition) {
        Step step = mRecipe.getSteps().get(stepPosition);
        String description = (stepPosition == 0) ? getIngredientDescription(mRecipe
                .getIngredients()) : step.getDescription();

        FragmentManager fragmentMgr = getFragmentManager();

        Bundle args = new Bundle();
        args.putString(STEP_DESCRIPTION, description);
        args.putString(STEP_URL, step.getVideoUrl());
        args.putBoolean(STEP_HAS_VIDEO, !step.getVideoUrl().equals(""));

        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setArguments(args);
        fragmentMgr.beginTransaction()
                .replace(R.id.detail_container_fragment, detailFragment)
                .commit();

        if (!step.getVideoUrl().equals("")) {
            VideoFragment playerFragment = new VideoFragment();
            playerFragment.setArguments(args);
            fragmentMgr.beginTransaction()
                    .replace(R.id.video_fragment_container, playerFragment)
                    .commit();
        }
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

    private void setButtonVisibility(int stepPosition) {
        boolean orientationIsPortrait = this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
        boolean stepHasVideo = !mRecipe.getSteps().get(stepPosition).getVideoUrl().equals("");


        if (orientationIsPortrait || !stepHasVideo) {
            // Show/Hide previous Button
            if (stepPosition == 0) {
                prevButton.setVisibility(View.INVISIBLE);
                widgetButton.setVisibility(View.VISIBLE);
            } else {
                prevButton.setVisibility(View.VISIBLE);
                widgetButton.setVisibility(View.INVISIBLE);
            }

            // Show/Hide next Button
            if (stepPosition + 1 == mRecipe.getSteps().size())
                nextButton.setVisibility(View.INVISIBLE);
            else nextButton.setVisibility(View.VISIBLE);
        } else {
            prevButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
            widgetButton.setVisibility(View.INVISIBLE);
        }
    }


    @OnClick(R.id.btn_next_step)
    public void nextStep() {
        selectedIndex += 1;
        setButtonVisibility(selectedIndex);
        showStepDetails(selectedIndex);
    }

    @OnClick(R.id.btn_prev_step)
    public void prevStep() {
        selectedIndex -= 1;
        setButtonVisibility(selectedIndex);
        showStepDetails(selectedIndex);
    }

    @OnClick(R.id.btn_add_to_widget)
    public void addToWidget() {
        Toast.makeText(this, "Adding to widget!", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPrefs = getSharedPreferences("widget_preferences",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        prefsEditor.putString("widget_ingredients", getIngredientDescription(mRecipe
                .getIngredients()));
        prefsEditor.putString("widget_recipe", mRecipe.getName());
        prefsEditor.apply();
        widgetButton.setClickable(false);
    }

}
