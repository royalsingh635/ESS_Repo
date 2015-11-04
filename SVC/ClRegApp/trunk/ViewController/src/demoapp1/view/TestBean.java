package demoapp1.view;

import javax.faces.event.ActionEvent;

public class TestBean {
    public TestBean() {
    }

    public void TestBeanString(ActionEvent actionEvent) {
        // Add event code here...
        Object x = null;
        if (x != null) {
            System.out.println("Value of x===");
        } else {
            System.out.println("value not null");
        }
    }
}

