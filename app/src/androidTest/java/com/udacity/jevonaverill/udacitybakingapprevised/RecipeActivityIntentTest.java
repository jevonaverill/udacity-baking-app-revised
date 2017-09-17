package com.udacity.jevonaverill.udacitybakingapprevised;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by jevonaverill on 9/9/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityIntentTest {
    @Rule
    public IntentsTestRule<RecipeActivity> mIntentRule = new IntentsTestRule<>(RecipeActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default, Espresso Intents does not stub any Intents. Stubbing needs to be set up before every test run.
        // In this case, all external Intents will be blocked
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(
                Activity.RESULT_OK, null));
    }
}
