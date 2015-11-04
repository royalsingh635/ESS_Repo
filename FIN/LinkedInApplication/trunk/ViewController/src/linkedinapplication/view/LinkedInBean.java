package linkedinapplication.view;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.NetworkUpdateType;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.enumeration.ProfileType;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;

import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;

import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;

import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;

import com.google.code.linkedinapi.schema.Company;
import com.google.code.linkedinapi.schema.Connections;
import com.google.code.linkedinapi.schema.ContactInfo;
import com.google.code.linkedinapi.schema.CurrentShare;
import com.google.code.linkedinapi.schema.Location;
import com.google.code.linkedinapi.schema.Network;
import com.google.code.linkedinapi.schema.Person;
import com.google.code.linkedinapi.schema.PhoneNumber;
import com.google.code.linkedinapi.schema.PhoneNumbers;

import com.google.code.linkedinapi.schema.Update;
import com.google.code.linkedinapi.schema.UpdateComment;
import com.google.code.linkedinapi.schema.UpdateContent;
import com.google.code.linkedinapi.schema.Updates;
import com.google.code.linkedinapi.schema.impl.CurrentShareImpl;

import java.io.Serializable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import java.util.ListIterator;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.ControllerContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;


public class LinkedInBean implements Serializable {
    private LinkedInApiClientFactory factory;
    private LinkedInApiClient client;
    private String ConsumerKey="qfa85i1rw4jt";
    private String ConsumerSecret="ESO5ul5LtYB2RXaD";
    private String AuthenticationURL;
    private String TokenSecretValue;
    private LinkedInAccessToken AccessToken;
    private LinkedInOAuthService OauthService;
    private LinkedInRequestToken RequestToken;
    private FacesContext context;
    private HttpSession httpsession;
    private String CallBackURL;
//-------------------------------After login-------------------------------------------------------------------------//    
    private Updates updates;
    private Person person;
    private Connections connections;
    private List<Person> connectionlist;
    private  List<Update> updatelist;
    private Network network;
    private String UserName;
    private String UserEmail;
    private String UserPhno;
    private String UserCompanyName;
    private String UserHeadline;
    private String UserLocation;
    private String UserPicURL;
    private String UserSummary;
    private String UserIndustry;
    private RichInputText statusUpdateMessegeBind;
    private RichInputText msgSndingToInputTxt;
    private RichInputText msgSubjectInputTxt;
    private RichInputText msgTextInputText;
    private RichPopup msgSndPopupBind;
    private RichOutputText nameFieldBind;
    private RichOutputText idFieldBind;
    
    private String idsend;
    private String namesend;
    private RichPopup sndMsgSucessBind;
    private RichPopup statusUpdatesSucessBInd;
    private RichPopup msgPopupBind;
    private String popmsg;

    public LinkedInBean() {
        //OauthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(ConsumerKey, ConsumerSecret);
        
    }

    public void setAuthenticationURL(String AuthenticationURL) {
        this.AuthenticationURL = AuthenticationURL;
    }

    public String getAuthenticationURL() {
        try {
            OauthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(ConsumerKey, ConsumerSecret);
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext eContext = context.getExternalContext();
            ViewHandler handler = context.getApplication().getViewHandler();
            
            String prefix;
            Object requestObject = eContext.getRequest();
            if (requestObject instanceof HttpServletRequest)
            {
              prefix = getHttpUrlPrefix((HttpServletRequest)requestObject);
            }
            else
            {
              // Portlet?
              prefix = "";
            }
            
            System.out.println("------>>"+prefix+""+ControllerContext.getInstance().getGlobalViewActivityURL(ControllerContext.getInstance().getCurrentViewPort().getViewId()));
            System.out.println("======>>"+prefix);
            String s =  ControllerContext.getInstance().getGlobalViewActivityURL(ControllerContext.getInstance().getCurrentViewPort().getViewId()).toString();
            Integer indx = s.indexOf("//");
            String url = s.substring(0, indx);
            System.out.println("Final callback url is :"+prefix+""+url);
            this.CallBackURL = prefix+""+url+"//callback_linkedin";
            RequestToken = OauthService.getOAuthRequestToken(CallBackURL);
            //System.out.println("Request token is : "+RequestToken.toString());
            
            this.AuthenticationURL = RequestToken.getAuthorizationUrl();
            //System.out.println("The access URL is :::==>"+this.AuthenticationURL);
            return AuthenticationURL;            
        } catch (Exception e) {
            System.out.println("Error in getting auth URL : "+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    private String getHttpUrlPrefix(HttpServletRequest request)
      {
        StringBuilder builder = new StringBuilder(32);
        builder.append(request.getScheme());
        builder.append(":/");
        builder.append(request.getServerName());
        builder.append(':');
        builder.append(request.getServerPort());
        
        return builder.toString();
      }
    public String loginToLinkedInAction() {
        context = FacesContext.getCurrentInstance();
        httpsession = (HttpSession)context.getExternalContext().getSession(false);

        System.out.println("The auth_verifier is:"+httpsession.getAttribute("oauth_verifier"));
        try{    
        String auth_verifier = httpsession.getAttribute("oauth_verifier").toString();
        AccessToken= OauthService.getOAuthAccessToken(RequestToken, auth_verifier);
        factory=LinkedInApiClientFactory.newInstance(ConsumerKey, ConsumerSecret);
        this.client = factory.createLinkedInApiClient(AccessToken); 
        httpsession.setAttribute("linkedIn_accesstoken", AccessToken);
            person=client.getProfileForCurrentUser(EnumSet.allOf(ProfileField.class));
            System.out.println("The name of the Logged in user is :"+person.getFirstName()+" "+person.getLastName());
            getUserProfile();
            ActionEvent a=null;
            getConnections(a);
            getNetworkUpdates(a);
        return "logged_in";
        }
        catch(Exception e){
            
                        FacesMessage message = new FacesMessage("There was an error in Authentication from the server. Please perform the authentication by clicking ' Get authorisation from linkedIn' link.");   
                        message.setSeverity(FacesMessage.SEVERITY_WARN);   
                        FacesContext fc = FacesContext.getCurrentInstance();   
                        fc.addMessage(null, message); 
                        e.printStackTrace();
//                this.popmsg = "There was an error in Authentication from the server. Please perform the authentication.";
//                this.showPopup(this.msgPopupBind, true);
                
                System.out.println("There was an error: "+e.getMessage());
                
            }
        return "";
    }
    
    
    public void getUserProfile(){
        
        try{
            //System.out.println("================================");
            //System.out.println("Name:" + person.getFirstName() + " " + person.getLastName());
            this.UserName = person.getFirstName() + " " + person.getLastName(); 
            //System.out.println("Headline:" + person.getHeadline());
            this.UserHeadline = person.getHeadline();
            //System.out.println("Summary:" + person.getSummary());
            this.UserSummary = person.getSummary();
            //System.out.println("Industry:" + person.getIndustry());
            this.UserIndustry=person.getIndustry();
            //System.out.println("Picture:" + person.getPictureUrl());
            this.UserPicURL=person.getPictureUrl();
            //System.out.println("Location: Country code: "+person.getLocation().getCountry().getCode());
            //System.out.println("Location: Location: "+person.getLocation().getName());
            this.UserLocation = person.getLocation().getName();
            //System.out.println("Number of connections : "+person.getNumConnections());
            
            System.out.println("================================");
           
        

        }catch(Exception e){
            this.popmsg = "There was an error in fetching Profile details from server. Please try again.";
            showPopup(msgPopupBind, true);
            e.printStackTrace();
        }
    }
    
    public void getConnections(ActionEvent actionEvent) {
        try{
        this.client = null;
        this.client = factory.createLinkedInApiClient(AccessToken);
        connections = client.getConnectionsForCurrentUser(EnumSet.allOf(ProfileField.class)) ;
        //System.out.println("Connections : "+connections);
        connectionlist = connections.getPersonList();
        System.out.println("<<<<====CONNECTIONS======>>>>");
//        for(Person p:connectionlist){
//            System.out.println("Name : "+p.getFirstName()+"____ProfilePicUrl : "+p.getPictureUrl()+"___Headline : "+p.getHeadline());
//        }
        }catch(Exception e){
            this.popmsg = "There was an error in fetching Connections list from server. Please try again.";
            showPopup(msgPopupBind, true);
        }
    }
    
    public void updateStatus(ActionEvent actionEvent) {                                               
        try{        
            
            this.client = null;
            this.client = factory.createLinkedInApiClient(AccessToken);
            //client.postNetworkUpdate(this.statusUpdateMessegeBind.getValue().toString());
            client.updateCurrentStatus(this.statusUpdateMessegeBind.getValue().toString());
           //person.setCurrentStatus(this.statusUpdateMessegeBind.getValue().toString());
           // System.out.println("The status is updated :"+this.statusUpdateMessegeBind.getValue().toString());
            this.statusUpdateMessegeBind.setValue("");
            this.popmsg="Status updated sucessfuly! ";
            showPopup(this.msgPopupBind, true);
        }catch(Exception e){
            //System.out.println("Some error in posting messege : "+e.getMessage());
            this.popmsg = "There was an error in Updating status. Please try again.";
            showPopup(msgPopupBind, true);
        }
        //System.out.println("Current status update : "+person.getCurrentStatus());
    }
    
    
    public void getNetworkUpdates(ActionEvent actionEvent) {
        try{
            this.client = null;
            this.client = factory.createLinkedInApiClient(AccessToken);
        network = client.getNetworkUpdates(EnumSet.of(NetworkUpdateType.STATUS_UPDATE));
        System.out.println("Total updates fetched:" + network.getUpdates().getTotal());
        Updates updates = network.getUpdates();
        updatelist = updates.getUpdateList();
//        for (Update update : network.getUpdates().getUpdateList()) {
//                System.out.println("-------------------------------");
//                System.out.println(update.getUpdateKey() + ":" 
//                                   + update.getUpdateContent().getPerson().getFirstName() + " " 
//                                   + update.getUpdateContent().getPerson().getLastName() + "->");            
//            if (update.getUpdateComments() != null) {
//                        System.out.println("Total comments fetched:" + update.getUpdateComments().getTotal());
//                        for (UpdateComment comment : update.getUpdateComments().getUpdateCommentList()) {
//                                System.out.println(comment.getPerson().getFirstName() + " " + comment.getPerson().getLastName() + "->" + comment.getComment());                         
//                        }
//                }
//        }
        }catch(Exception e){
            this.popmsg = "There was an error in fetching Network updates. Please try again.";
            showPopup(msgPopupBind, true);
        }
        
    }
    
    public void sendMessege(ActionEvent actionEvent) {
        try{
//        System.out.println("Name    : "+this.msgSndingToInputTxt.getValue());
//        System.out.println("Id      : "+this.idsend);
//        System.out.println("Subject : "+this.msgSubjectInputTxt.getValue());
//        System.out.println("Message : "+this.msgTextInputText.getValue());
        client.sendMessage(Arrays.asList(this.idsend), 
                           this.msgSubjectInputTxt.getValue().toString(), 
                           this.msgTextInputText.getValue().toString());
        this.msgSndPopupBind.hide();
            
        this.popmsg="The messege sent sucessfully to "+this.msgSndingToInputTxt.getValue().toString()+"!";
        showPopup(this.msgPopupBind, true);
    
        }catch(Exception e){
            System.out.println("Error in sending messege : "+e.getMessage());
            this.popmsg = "There was an error in sending message. Please try again.";
            showPopup(msgPopupBind, true);
        }
        
        

    }
    
    
    public void sendMsglink(ActionEvent actionEvent) {
        
        showPopup(this.msgSndPopupBind, true);
        //System.out.println("Name is : "+this.nameFieldBind.getValue()); 
        this.namesend= this.nameFieldBind.getValue().toString();
        this.idsend=this.idFieldBind.getValue().toString();
        
    }
    
    
    private void showPopup(RichPopup pop, boolean visible) {  
         try {  
           FacesContext context = FacesContext.getCurrentInstance();  
           if (context != null && pop != null) {  
             String popupId = pop.getClientId(context);  
             if (popupId != null) {  
               StringBuilder script = new StringBuilder();  
               script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");  
               if (visible) {  
                 script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");  
               } else {  
                 script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");  
               }  
               ExtendedRenderKitService erks =  
                 Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);  
               erks.addScript(context, script.toString());  
             }  
           }  
         } catch (Exception e) {  
           throw new RuntimeException(e);  
         }  
       } 
    
    public void setUserHeadline(String UserHeadline) {
        this.UserHeadline = UserHeadline;
    }

    public String getUserHeadline() {
        return UserHeadline;
    }

    public void setUserLocation(String UserLocation) {
        this.UserLocation = UserLocation;
    }

    public String getUserLocation() {
        return UserLocation;
    }

    public void setUserPicURL(String UserPicURL) {
        this.UserPicURL = UserPicURL;
    }

    public String getUserPicURL() {
        return UserPicURL;
    }

    public void setUserSummary(String UserSummary) {
        this.UserSummary = UserSummary;
    }

    public String getUserSummary() {
        return UserSummary;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserPhno(String UserPhno) {
        this.UserPhno = UserPhno;
    }

    public String getUserPhno() {
        return UserPhno;
    }

    public void setUserCompanyName(String UserCompanyName) {
        this.UserCompanyName = UserCompanyName;
    }

    public String getUserCompanyName() {
        return UserCompanyName;
    }

    public void setUserIndustry(String UserIndustry) {
        this.UserIndustry = UserIndustry;
    }

    public String getUserIndustry() {
        return UserIndustry;
    }


    public void setConnectionlist(List<Person> connectionlist) {
        this.connectionlist = connectionlist;
    }

    public List<Person> getConnectionlist() {
        return connectionlist;
    }

    public void setStatusUpdateMessegeBind(RichInputText statusUpdateMessegeBind) {
        this.statusUpdateMessegeBind = statusUpdateMessegeBind;
    }

    public RichInputText getStatusUpdateMessegeBind() {
        return statusUpdateMessegeBind;
    }


    public void setNetwork(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    public void setUpdatelist(List<Update> updatelist) {
        this.updatelist = updatelist;
    }

    public List<Update> getUpdatelist() {
        return updatelist;
    }

    public void setMsgSndingToInputTxt(RichInputText msgSndingToInputTxt) {
        this.msgSndingToInputTxt = msgSndingToInputTxt;
    }

    public RichInputText getMsgSndingToInputTxt() {
        return msgSndingToInputTxt;
    }

    public void setMsgSubjectInputTxt(RichInputText msgSubjectInputTxt) {
        this.msgSubjectInputTxt = msgSubjectInputTxt;
    }

    public RichInputText getMsgSubjectInputTxt() {
        return msgSubjectInputTxt;
    }

    public void setMsgTextInputText(RichInputText msgTextInputText) {
        this.msgTextInputText = msgTextInputText;
    }

    public RichInputText getMsgTextInputText() {
        return msgTextInputText;
    }

    

    public void setMsgSndPopupBind(RichPopup msgSndPopupBind) {
        this.msgSndPopupBind = msgSndPopupBind;
    }

    public RichPopup getMsgSndPopupBind() {
        return msgSndPopupBind;
    }

    public void setNameFieldBind(RichOutputText nameFieldBind) {
        this.nameFieldBind = nameFieldBind;
    }

    public RichOutputText getNameFieldBind() {
        return nameFieldBind;
    }


    public void setIdFieldBind(RichOutputText idFieldBind) {
        this.idFieldBind = idFieldBind;
    }

    public RichOutputText getIdFieldBind() {
        return idFieldBind;
    }

    public void setIdsend(String idsend) {
        this.idsend = idsend;
    }

    public String getIdsend() {
        return idsend;
    }

    public void setNamesend(String namesend) {
        this.namesend = namesend;
    }

    public String getNamesend() {
        return namesend;
    }

    public void setSndMsgSucessBind(RichPopup sndMsgSucessBind) {
        this.sndMsgSucessBind = sndMsgSucessBind;
    }

    public RichPopup getSndMsgSucessBind() {
        return sndMsgSucessBind;
    }

    public void setStatusUpdatesSucessBInd(RichPopup statusUpdatesSucessBInd) {
        this.statusUpdatesSucessBInd = statusUpdatesSucessBInd;
    }

    public RichPopup getStatusUpdatesSucessBInd() {
        return statusUpdatesSucessBInd;
    }

    public void setMsgPopupBind(RichPopup msgPopupBind) {
        this.msgPopupBind = msgPopupBind;
    }

    public RichPopup getMsgPopupBind() {
        return msgPopupBind;
    }

    public void setPopmsg(String popmsg) {
        this.popmsg = popmsg;
    }

    public String getPopmsg() {
        return popmsg;
    }
    public String LoginIdentifier(){
        context = FacesContext.getCurrentInstance();
        httpsession = (HttpSession)context.getExternalContext().getSession(false);
        System.out.println("Session : "+httpsession);
        System.out.println("Access Token : "+httpsession.getAttribute("linkedIn_accesstoken"));
        if(httpsession.getAttribute("linkedIn_accesstoken") == null){
            return "login_page";
        }
        else{
            this.AccessToken = (LinkedInAccessToken) httpsession.getAttribute("linkedIn_accesstoken");
            getUserProfile();
            ActionEvent a=null;
            getConnections(a);
            getNetworkUpdates(a);
            return "main_page";
        }
    
    }

   

    public String logout() {
        context = FacesContext.getCurrentInstance();
        httpsession = (HttpSession)context.getExternalContext().getSession(false);
        httpsession.setAttribute("linkedIn_accesstoken", null);
//        this.popmsg = "Logged out sucessfully!";
//        this.showPopup(msgPopupBind, true);
        
                        FacesMessage message = new FacesMessage("Logged out sucessfully!");   
                        message.setSeverity(FacesMessage.SEVERITY_INFO);   
                        FacesContext fc = FacesContext.getCurrentInstance();   
                        fc.addMessage(null, message); 

        return "return_to_main_page";
    }
}
