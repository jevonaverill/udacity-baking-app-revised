package com.udacity.jevonaverill.udacitybakingapprevised;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by jevonaverill on 9/21/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeStepActivityTest {

    @Rule public ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(RecipeActivity.class);

    @Test public void clickingFirstRecipeStepShowsCorrectButtons() {
        // Click on the first Recipe card found in the MainActivity RecyclerView
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the first Step list item in the DetailActivity RecyclerView
        onView(withId(R.id.master_list_fragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check that the "Next" Button is displayed
        onView(withId(R.id.btn_next_step)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_add_to_widget)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_prev_step)).check(matches(not(isDisplayed())));
    }

    @Test public void clickNextUpdatesButtonsDisplayed() {
        // Click on the first Recipe card found in the MainActivity RecyclerView
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the first Step list item in the DetailActivity RecyclerView
        onView(withId(R.id.master_list_fragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click the "Next" Button in the first Step
        onView(withId(R.id.btn_next_step)).perform(click());

        // Check that the "Next" Button is displayed
        onView(withId(R.id.btn_next_step)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_add_to_widget)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btn_prev_step)).check(matches(isDisplayed()));
    }

    @Test public void exoplayerIsDisplayedInStepWithVideo() {
        // Click on the first Recipe card found in the MainActivity RecyclerView
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the first Step list item in the DetailActivity RecyclerView
        onView(withId(R.id.master_list_fragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.video_fragment_container)).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(SimpleExoPlayerView.class.getName()))).check(matches(isDisplayed()));
    }

    @Test public void exoplayerNotDisplayedInStepWithoutVideo() {
        // Click on the first Recipe card found in the MainActivity RecyclerView
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click on the first Step list item in the DetailActivity RecyclerView
        onView(withId(R.id.master_list_fragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.video_fragment_container)).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(SimpleExoPlayerView.class.getName()))).check(doesNotExist());

    }

}