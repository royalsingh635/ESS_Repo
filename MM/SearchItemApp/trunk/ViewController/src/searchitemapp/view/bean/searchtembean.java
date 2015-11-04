package searchitemapp.view.bean;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

public class searchtembean {
    ArrayList<String> a=new ArrayList<String>();
    public searchtembean() {
    }

    public void setA(ArrayList<String> a) {
        this.a = a;
    }

    public ArrayList<String> getA() {
        return a;
    }

    public void searchgroup(ActionEvent actionEvent) 
    {
       // ArrayList<String> a=new ArrayList<String>();
        a.add("raushan");
        a.add("new");
    }
}
