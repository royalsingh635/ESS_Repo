package ebizresources.resource.bundle;
import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    private static  Object[][] contents = EbizLangResource.initContents();
    public Resource() {
        System.out.println("Resource ________________");
        for (int i = 0; i < 15000; i++) {
            contents[i][0] = "";
            contents[i][1] = "";
        }
    }

    public Object[][] getContents() {
        return EbizLangResource.getContents(contents);
    }
    public void h(){
        getContents();
    }

}