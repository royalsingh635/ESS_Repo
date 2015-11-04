package appSecUser.view.bundle;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static final Object[][] contents = { { "appSecUser.model.view.views.UserDocVO_LABEL", "User Doc Vo" }, { "appSecUser.model.view.links.UserDocLnk_LABEL", "User Doc Lnk" } };

    public Object[][] getContents() {
        return contents;
    }
}
