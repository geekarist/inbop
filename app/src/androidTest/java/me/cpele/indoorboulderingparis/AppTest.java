package me.cpele.indoorboulderingparis;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.cpele.indoorboulderingparis.list.PlacesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class AppTest {

    @Rule
    public ActivityTestRule<PlacesActivity> activityTestRule = new ActivityTestRule<>(PlacesActivity.class);

    @Test
    public void shouldDisplayPlace() {

        String itemToClick = "Antrebloc";

        onView(allOf(withId(R.id.place_item_tv_name), withText(itemToClick))).perform(click());

        onView(withId(R.id.fragment_climbthere_tv_what))
                .check(ViewAssertions.matches(withText(containsString("Boulders and not walls"))));
    }
}
