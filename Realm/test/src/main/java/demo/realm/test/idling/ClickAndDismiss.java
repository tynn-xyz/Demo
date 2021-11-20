package demo.realm.test.idling;

import static android.view.View.GONE;
import static org.hamcrest.Matchers.any;

import android.view.View;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

class ClickAndDismiss implements ViewAction {

    @Override
    public Matcher<View> getConstraints() {
        return any(View.class);
    }

    @Override
    public String getDescription() {
        return "Click and dismiss";
    }

    @Override
    public void perform(UiController uiController, View view) {
        IdlingResource resources = new AwaitWindowDismiss(view);
        IdlingRegistry.getInstance().register(resources);
        view.performClick();
    }
}

