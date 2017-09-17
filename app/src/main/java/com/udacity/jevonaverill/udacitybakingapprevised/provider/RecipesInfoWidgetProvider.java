package com.udacity.jevonaverill.udacitybakingapprevised.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.udacity.jevonaverill.udacitybakingapprevised.R;
import com.udacity.jevonaverill.udacitybakingapprevised.RecipeActivity;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class RecipesInfoWidgetProvider extends AppWidgetProvider {

    private static final String WIDGET_PREFERENCES = "widget_preferences";
    private static final String PREFERENCE_RECIPE = "widget_recipe";
    private static final String PREFERENCE_INGREDIENTS = "widget_ingredients";
    private static final String DEFAULT_INGREDIENTS = "Select a recipe to display";
    private static final String DEFAULT_RECIPE = "No recipe selected";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(WIDGET_PREFERENCES,
                Context.MODE_PRIVATE);
        String recipe = sharedPrefs.getString(PREFERENCE_RECIPE, DEFAULT_RECIPE);
        String ingredients = sharedPrefs.getString(PREFERENCE_INGREDIENTS, DEFAULT_INGREDIENTS);

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_recipe_info_provider);
        views.setTextViewText(R.id.tv_widget_recipe, recipe);
        views.setTextViewText(R.id.tv_widget_ingredients, ingredients);

        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.tv_widget_ingredients, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // Called when a new widget is created as well as every update interval
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
