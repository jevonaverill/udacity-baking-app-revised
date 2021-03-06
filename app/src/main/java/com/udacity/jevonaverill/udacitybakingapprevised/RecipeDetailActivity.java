package com.udacity.jevonaverill.udacitybakingapprevised;

import android.app.FragmentManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.udacity.jevonaverill.udacitybakingapprevised.adapter.MasterListAdapter;
import com.udacity.jevonaverill.udacitybakingapprevised.fragment.StepDetailFragment;
import com.udacity.jevonaverill.udacitybakingapprevised.fragment.VideoFragment;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Ingredient;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Step;
import com.udacity.jevonaverill.udacitybakingapprevised.provider.RecipesInfoWidgetProvider;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

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
    @BindString(R.string.key_step_image_url)
    String STEP_IMAGE_URL;

    @Nullable
    @BindView(R.id.btn_add_to_widget)
    Button widgetButton;
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isTwoPaneView = (null != findViewById(R.id.detail_container_fragment));
        if (isTwoPaneView) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            showStepDetails();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_RECIPE_KEY, mRecipe);

        if (selectedIndex > 0) outState.putInt(STEP_POSITION_KEY, selectedIndex);
    }

    @Override
    public void onStepClick(int position) {
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
        args.putString(STEP_IMAGE_URL, step.getThumbnailUrl());

        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setArguments(args);
        fragmentMgr.beginTransaction()
                .replace(R.id.detail_container_fragment, detailFragment)
                .commit();

        VideoFragment playerFragment = new VideoFragment();
        playerFragment.setArguments(args);
        fragmentMgr.beginTransaction()
                .replace(R.id.video_fragment_container, playerFragment)
                .commit();
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

    @Optional
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

        Intent intent = new Intent(this, RecipesInfoWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = new ComponentName(this, RecipesInfoWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        this.sendBroadcast(intent);

        widgetButton.setClickable(false);
    }

}
