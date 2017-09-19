package com.udacity.jevonaverill.udacitybakingapprevised;

import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by jevonaverill on 9/9/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    @Rule
    public IntentsTestRule<RecipeActivity> mIntentRule = new IntentsTestRule<>(RecipeActivity.class);

    private static String TOOLBAR_TEXT = "Udacity Baking App Revised";

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mIntentRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(
            RecipeActivity.class);

    @Test
    public void toolbarDisplaysCorrectName() {
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar_main))))
                .check(matches(withText(TOOLBAR_TEXT)));
    }

    @After
    public void unregisterIdlingResources() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
