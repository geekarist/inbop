package me.cpele.inbop;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.cpele.inbop.list.ListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class AppTest {

    @Rule
    public ActivityTestRule<ListActivity> activityTestRule = new ActivityTestRule<>(ListActivity.class);

    @Test
    public void shouldDisplayPlace() {

        String itemToClick = "Antrebloc";

        onView(allOf(withId(R.id.place_tv_name), withText(itemToClick))).perform(click());

        onView(withId(R.id.detail_tb)).check(
                ViewAssertions.selectedDescendantsMatch(
                        withClassName(equalTo(AppCompatTextView.class.getName())),
                        withText(containsString("Antrebloc"))));
    }
}
