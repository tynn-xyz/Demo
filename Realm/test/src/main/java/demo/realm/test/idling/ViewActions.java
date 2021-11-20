package demo.realm.test.idling;

import androidx.test.espresso.ViewAction;

public class ViewActions {

    public static ViewAction clickAndDismiss() {
        return new ClickAndDismiss();
    }
}
