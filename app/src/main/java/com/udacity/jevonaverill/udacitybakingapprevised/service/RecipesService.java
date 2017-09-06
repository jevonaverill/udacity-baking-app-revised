package com.udacity.jevonaverill.udacitybakingapprevised.service;

import com.udacity.jevonaverill.udacitybakingapprevised.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jevonaverill on 9/6/17.
 */

public interface RecipesService {

    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();

}
