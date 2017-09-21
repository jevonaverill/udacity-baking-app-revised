package com.udacity.jevonaverill.udacitybakingapprevised;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.udacity.jevonaverill.udacitybakingapprevised.IdlingResource.SimpleIdlingResource;
import com.udacity.jevonaverill.udacitybakingapprevised.adapter.RecipeAdapter;
import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;
import com.udacity.jevonaverill.udacitybakingapprevised.service.RecipesService;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;

    @BindString(R.string.key_recipe_step)
    String STEP_RECIPE_KEY;
    @BindString(R.string.app_name)
    String mAppName;

    private RecipeAdapter mRecipeAdapter;
    private List<Recipe> mRecipeList;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);
        mToolbar.setTitle(mAppName);
        setSupportActionBar(mToolbar);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutMgr = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutMgr);

        loadRecipes();
    }

    private void loadRecipes() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_recipe_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        // Create a very simple REST adapter which points to the API endpoint
        RecipesService client = retrofit.create(RecipesService.class);

        // Fetch a list of the recipes
        Call<List<Recipe>> call = client.getRecipes();

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                // The network call was a success and we got a response
                mRecipeList = response.body();
                mRecipeAdapter = new RecipeAdapter(mRecipeList, RecipeActivity.this);
                mRecyclerView.setAdapter(mRecipeAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // the network call was a failure
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(STEP_RECIPE_KEY, mRecipeList.get(position));
        startActivity(intent);
    }

}
