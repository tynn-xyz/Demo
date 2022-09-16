package demo.realm.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static demo.realm.app.R.string.action_cancel;
import static demo.realm.app.R.string.action_item_add;
import static demo.realm.app.R.string.action_item_delete;
import static demo.realm.app.R.string.action_item_update;
import static demo.realm.app.R.string.action_login;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import demo.realm.app.MainActivity;
import demo.realm.app.R.id;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activity =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void add_update_delete_item() {
        loginUser("add_update_delete", "happy");

        // add item
        onView(withId(id.action_item_add))
                .perform(click());
        onView(withId(id.item_value))
                .perform(replaceText("Value"));
        onView(withButton(android.R.id.button1, action_item_add))
                .perform(click());

        // update item
        onView(withText("Value"))
                .perform(click());
        onView(withId(id.item_value))
                .perform(typeText(" update "));
        onView(withButton(android.R.id.button1, action_item_update))
                .perform(click());

        // delete item
        onView(withText("Value update"))
                .perform(click());
        onView(withButton(android.R.id.button2, action_item_delete))
                .perform(click());

        logoutUser();
    }

    @Test
    public void cancel_add_item() {
        loginUser("cancel_add", "add");

        // add item
        onView(withId(id.action_item_add))
                .perform(click());
        onView(withButton(android.R.id.button3, action_cancel))
                .perform(click());

        logoutUser();
    }

    @Test
    public void cancel_update_item() {
        loginUser("cancel_update", "update");

        // add item
        onView(withId(id.action_item_add))
                .perform(click());
        onView(withId(id.item_value))
                .perform(replaceText("Value"));
        onView(withButton(android.R.id.button1, action_item_add))
                .perform(click());

        // update item
        onView(withText("Value"))
                .perform(click());
        onView(withButton(android.R.id.button3, action_cancel))
                .perform(click());

        logoutUser();
    }

    @Test
    public void cancel_login() {
        maybeLogoutUser();

        onView(withId(id.action_login))
                .perform(click());
        onView(withButton(android.R.id.button3, action_cancel))
                .perform(click());

        onView(withId(id.action_logout))
                .check(doesNotExist());
    }

    private void loginUser(String username, String password) {
        maybeLogoutUser();

        onView(withId(id.action_login))
                .perform(click());
        onView(withId(id.login_username))
                .perform(replaceText(username));
        onView(withId(id.login_password))
                .perform(replaceText(password));
        onView(withButton(android.R.id.button1, action_login))
                .perform(click());
    }

    private void maybeLogoutUser() {
        try {
            logoutUser();
        } catch (Throwable ignore) {
        }
    }

    private void logoutUser() {
        onView(withId(id.action_logout))
                .perform(click());
    }

    private Matcher<View> withButton(@IdRes int id, @StringRes int text) {
        return allOf(withId(id), withText(text));
    }
}
