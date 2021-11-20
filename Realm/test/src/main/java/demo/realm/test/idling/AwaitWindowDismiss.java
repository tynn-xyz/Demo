package demo.realm.test.idling;

import static android.view.View.GONE;

import android.view.View;

import androidx.test.espresso.IdlingResource;

class AwaitWindowDismiss implements IdlingResource {

    private final View view;

    AwaitWindowDismiss(View view) {
        this.view = view;
    }

    @Override
    public String getName() {
        return "Await dismiss";
    }

    @Override
    public boolean isIdleNow() {
        return view.getWindowVisibility() == GONE;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
    }
}
