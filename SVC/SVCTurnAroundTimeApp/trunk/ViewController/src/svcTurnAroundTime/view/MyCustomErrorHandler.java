package svcTurnAroundTime.view;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCErrorHandlerImpl;
import oracle.jbo.JboException; 
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
 
import oracle.jbo.TxnValException;
 
 
public class MyCustomErrorHandler extends DCErrorHandlerImpl
{
    
    public MyCustomErrorHandler() 
    {    
    }  
      
    
    
    public void reportException(DCBindingContainer bc, Exception ex) {
    BindingContext ctx = bc.getBindingContext();
    String err_code = null;
    System.out.println("Exception = "+ ex);
    if ( ex instanceof TxnValException){
    // Handle JBO-27023
    err_code = ((TxnValException)ex).getErrorCode();
    if (new Integer(err_code).intValue() == 27023 ){
    //this.getDisplayMessage(ctx, ex);
    System.out.println("handle JBO-27023");
    }
    }
    if(ex instanceof oracle.jbo.TooManyObjectsException)
    {
        
        System.out.println("-----Ex----");
//   FacesContext context = FacesContext.getCurrentInstance();
//   context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record already exist ", null));
    }
    }
}