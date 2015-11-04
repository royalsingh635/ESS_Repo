package svcinvoiceapp.view.bean;

import java.util.Map;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFrame;

import oracle.binding.BindingContainer;

import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;


/**
 * Provides various utility methods that are handy to
 * have around when working with ADF.
 */
public class ADFUtil {

/**
* When a bounded task flow manages a transaction (marked as requires-transaction,.
* requires-new-transaction, or requires-existing-transaction), then the
* task flow must issue any commits or rollbacks that are needed. This is
* essentially to keep the state of the transaction that the task flow understands
* in synch with the state of the transaction in the ADFbc layer.
*
* Use this method to issue a commit in the middle of a task flow while staying
* in the task flow.
*/
public static void saveAndContinue() {
Map sessionMap =
FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
BindingContext context =
(BindingContext)sessionMap.get(BindingContext.CONTEXT_ID);
String currentFrameName = context.getCurrentDataControlFrame();
DataControlFrame dcFrame =
context.findDataControlFrame(currentFrameName);

dcFrame.commit();
dcFrame.beginTransaction(null);
}
    public static Object resolvEl(String data) {
      FacesContext fc = FacesContext.getCurrentInstance();
      Application app = fc.getApplication();
      ExpressionFactory elFactory = app.getExpressionFactory();
      ELContext elContext = fc.getELContext();
      ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
      return valueExp.getValue(elContext);
      
    }
    public static Number roundOffAmt(Number amt) {

        Integer amt_digit =6;// Integer.parseInt(ADFUtil.resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());

        if (amt != null) {
            if (amt_digit != null) {

                return new Number(amt.round(amt_digit));

            } else {

                return new Number(amt.round(2));

            }
        } else {
            return new Number(0);
        }
    }
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
/**
* Programmatic evaluation of EL.
*
* @param el EL to evaluate
* @return Result of the evaluation
*/
public static Object evaluateEL(String el) {
FacesContext facesContext = FacesContext.getCurrentInstance();
ELContext elContext = facesContext.getELContext();
ExpressionFactory expressionFactory =
facesContext.getApplication().getExpressionFactory();
ValueExpression exp =
expressionFactory.createValueExpression(elContext, el,
Object.class);

return exp.getValue(elContext);
}

/**
* Programmatic invocation of a method that an EL evaluates to.
* The method must not take any parameters.
*
* @param el EL of the method to invoke
* @return Object that the method returns
*/
public static Object invokeEL(String el) {
return invokeEL(el, new Class[0], new Object[0]);
}

/**
* Programmatic invocation of a method that an EL evaluates to.
*
* @param el EL of the method to invoke
* @param paramTypes Array of Class defining the types of the parameters
* @param params Array of Object defining the values of the parametrs
* @return Object that the method returns
*/
public static Object invokeEL(String el, Class[] paramTypes,
Object[] params) {
FacesContext facesContext = FacesContext.getCurrentInstance();
ELContext elContext = facesContext.getELContext();
ExpressionFactory expressionFactory =
facesContext.getApplication().getExpressionFactory();
MethodExpression exp =
expressionFactory.createMethodExpression(elContext, el,
Object.class, paramTypes);

return exp.invoke(elContext, params);
}

/**
* Sets a value into an EL object. Provides similar functionality to
* the <af:setActionListener> tag, except the from is
* not an EL. You can get similar behavior by using the following...

* setEL(to, evaluateEL(from))
*
* @param el EL object to assign a value
* @param val Value to assign
*/
public static void setEL(String el, Object val) {
FacesContext facesContext = FacesContext.getCurrentInstance();
ELContext elContext = facesContext.getELContext();
ExpressionFactory expressionFactory =
facesContext.getApplication().getExpressionFactory();
ValueExpression exp =
expressionFactory.createValueExpression(elContext, el,
Object.class);

exp.setValue(elContext, val);
}
    public static void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             String msgMode) {

        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }
    
    public static Boolean isPrecisionValid(Integer precision,Integer scale,Number total){
                      JboPrecisionScaleValidator vc=new JboPrecisionScaleValidator();
                      vc.setPrecision(precision);
                      vc.setScale(scale);
                      return vc.validateValue(total);
           }
}