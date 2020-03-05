package com.apps.moviecatalogue.ui;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.apps.moviecatalogue.MainActivity;
import com.apps.moviecatalogue.R;
import com.apps.moviecatalogue.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MovieCatalogueTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @Test
    public void toDetailActivityTest() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_title)).check(matches(withText("Ad Astra")));
        onView(withId(R.id.action_bookmark)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.navigation_dashboard)).perform(click());
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_tvshow)).perform(actionOnItem(withChild(withText("The Simpsons")), click()));
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_title)).check(matches(withText("The Simpsons")));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.navigation_notifications)).perform(click());
        onView(withId(R.id.rv_bookmark)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_bookmark)).perform(swipeLeft());
    }
}
