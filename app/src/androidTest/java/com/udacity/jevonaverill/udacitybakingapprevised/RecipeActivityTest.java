package com.udacity.jevonaverill.udacitybakingapprevised;

import android.support.annotation.IdRes;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
    private static String TOOLBAR_TEXT = "Udacity Baking App Revised";

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule = new ActivityTestRule<>(
            RecipeActivity.class);

    @Test
    public void toolbarDisplaysCorrectName() {
        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar_main))))
                .check(matches(withText(TOOLBAR_TEXT)));
    }
}
