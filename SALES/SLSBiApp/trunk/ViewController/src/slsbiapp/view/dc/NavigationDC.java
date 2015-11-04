package slsbiapp.view.dc;

import oracle.adf.controller.TaskFlowId;

public class NavigationDC {
    private Integer navId;
    private Integer taskFlowId;
    private StringBuffer navDesc;
    public NavigationDC(Integer navId,Integer taskFlowId, StringBuffer navDesc) {
        this.navId = navId;
        this.navDesc = navDesc;
        this.taskFlowId = taskFlowId;
    }


    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    public Integer getNavId() {
        return navId;
    }

    public void setTaskFlowId(Integer taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public Integer getTaskFlowId() {
        return taskFlowId;
    }

    public void setNavDesc(StringBuffer navDesc) {
        this.navDesc = navDesc;
    }

    public StringBuffer getNavDesc() {
        return navDesc;
    }
}
