package ebizframemainapplication.view.listener;

import com.sun.faces.lifecycle.RestoreViewPhase;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

import oracle.adfinternal.view.faces.context.RichExceptionHandler;
import oracle.adfinternal.view.faces.webapp.rich.RichWindowManager;

public class ApplicationPagePhaseListener implements PagePhaseListener {
    public ApplicationPagePhaseListener() {
        super();
    }

    @Override
    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
    // System.out.println("after phase ");      
       
        
    }


    @Override
    public void beforePhase(PagePhaseEvent pagePhaseEvent) {

       // System.out.println("before phase ");
           

       
    }
}
