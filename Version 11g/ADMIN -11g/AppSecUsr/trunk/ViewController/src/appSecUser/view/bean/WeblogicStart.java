package appSecUser.view.bean;

public class WeblogicStart extends Exception {
    public WeblogicStart(Throwable throwable) {
        super(throwable);
    }

    public WeblogicStart(String string, Throwable throwable) {
        super(string, throwable);
    }

    public WeblogicStart(String string) {
        super(string);
    }

    public WeblogicStart() {
        super();
    }
}
