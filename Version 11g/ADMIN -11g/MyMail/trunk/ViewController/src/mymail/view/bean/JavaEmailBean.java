package mymail.view.bean;

import java.io.Serializable;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichTextEditor;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class JavaEmailBean {
    private RichInputText toBind;
    private RichInputText subjectBind;
    private RichInputText messageBind;
    private RichInputText emailIdBind;
    private RichInputText passwordBind;
    private  String emailId;
    private  String pwd;
    private  String file_name;
   
    private RichInputText showPopUpBind;
    private RichPopup loginPopUpBind;
    private  boolean attachFile = false;

    public JavaEmailBean() {
    }
    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;
    String toWhom;
    String messagae;
    String subject;

    

    public void sendMailButton(ActionEvent actionEvent) throws AddressException, MessagingException {
        // System.out.println("Rich text Value is-->"+richtextEditor.getValue().toString());

        setMailServerProperties();
        createEmailMessage();
        sendEmail();
    }

    public void setMailServerProperties() {

        String emailPort = "587"; //gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessage() throws AddressException, MessagingException {
        toWhom = toBind.getValue().toString();
        subject = subjectBind.getValue().toString();
        messagae = messageBind.getValue().toString();
        String[] toEmails = { toWhom };
        String emailSubject = subject;
        String emailBody = messagae;
        System.out.println("to---->" + toEmails + "subject is-->" + emailSubject + "body is--->" + emailBody);
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);

        //1) create MimeBodyPart object and set your message content
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText(emailBody);

        //2) create new MimeBodyPart object and set DataHandler object to this object
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();

        String filename = "D://" + file_name; //change accordingly
        System.out.println("Exact path--->" + filename);
        DataSource source = new FileDataSource(filename);
        messageBodyPart2.setDataHandler(new DataHandler(source));
        messageBodyPart2.setFileName(filename);


        //5) create Multipart object and add MimeBodyPart objects to this object
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);
        multipart.addBodyPart(messageBodyPart2);

        //6) set the multiplart object to the message object
        emailMessage.setContent(multipart);

        // emailMessage.setContent(emailBody, "text/html");//for a html email
        //emailMessage.setText(emailBody);// for a text email

    }

    public void createEmailMessageWidtAtchmnt() throws AddressException, MessagingException {
        toWhom = toBind.getValue().toString();
        subject = subjectBind.getValue().toString();
        messagae = messageBind.getValue().toString();
        String[] toEmails = { toWhom };
        System.out.println("To whom is-->{"+toWhom+"} total contact is-->"+toEmails.length);
        String emailSubject = subject;
        String emailBody = messagae;
        System.out.println("to---->" + toEmails + "subject is-->" + emailSubject + "body is--->" + emailBody);
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);

        //1) create MimeBodyPart object and set your message content
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText(emailBody);


        emailMessage.setContent(emailBody, "text/html"); //for a html email
        //emailMessage.setText(emailBody);// for a text email

    }

    public void sendEmail()  {

        String emailHost = "smtp.gmail.com";
        String fromUser = emailId; //just the id alone without @gmail.com
        String fromUserEmailPassword = pwd;
        //System.out.println("Username is--->"+fromUser+"and password is--->"+fromUserEmailPassword);
        Transport transport=null;

        try {
            transport = mailSession.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            System.out.println("No such Provider Exception");
        }
        try {
            transport.connect(emailHost, fromUser, fromUserEmailPassword);
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            transport.close();
            StringBuilder msgEmail=new StringBuilder("<html><body><b>Email Sent</b></body></html>");
            FacesMessage msg=new FacesMessage(msgEmail.toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage("No Network Error !", msg);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("somthing went wrong-->Messaging Exception");
            StringBuilder msgEmail=new StringBuilder("<html><body><b>Something went wrong !</b>");
            msgEmail.append("<p><b>please provide correct parameters</b></p>");
            msgEmail.append("</body></html>");
            FacesMessage msg=new FacesMessage(msgEmail.toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage("No Network Error !", msg);
           
        }
       
    }

    public void uploadedFileAttachmentVCE(ValueChangeEvent valueChangeEvent) {
        UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
        System.out.println("File is--->" + file.toString());
        System.out.println("Name of file is-->" + file.getFilename());
        file_name = file.getFilename();

    }

    public void setToBind(RichInputText toBind) {
        this.toBind = toBind;
    }

    public RichInputText getToBind() {
        return toBind;
    }

    public void setSubjectBind(RichInputText subjectBind) {
        this.subjectBind = subjectBind;
    }

    public RichInputText getSubjectBind() {
        return subjectBind;
    }

    public void setMessageBind(RichInputText messageBind) {
        this.messageBind = messageBind;
    }

    public RichInputText getMessageBind() {
        return messageBind;
    }

    public void setEmailIdBind(RichInputText emailIdBind) {
        this.emailIdBind = emailIdBind;
    }

    public RichInputText getEmailIdBind() {
        return emailIdBind;
    }

    public void setPasswordBind(RichInputText passwordBind) {
        this.passwordBind = passwordBind;
    }

    public RichInputText getPasswordBind() {
        return passwordBind;
    }

    public void signInButton(ActionEvent actionEvent) {
       
    }

    public void logOutlink(ActionEvent actionEvent) {
        setEmailId("");
        setPwd("");
        toBind.setValue("");
        subjectBind.setValue("");
        messageBind.setValue("");
        
              /*   ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
                HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
                HttpSession session = (HttpSession)ectx.getSession(false);
      //  Object attribute = session.getAttribute("");
                session.setAttribute("EmailId", null); */
        this.attachFile=false;
    }

    public void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

 

    public void setShowPopUpBind(RichInputText showPopUpBind) {
        this.showPopUpBind = showPopUpBind;
    }

    public RichInputText getShowPopUpBind() {
       
        return showPopUpBind;
    }

    public void setLoginPopUpBind(RichPopup loginPopUpBind) {
        this.loginPopUpBind = loginPopUpBind;
    }

    public RichPopup getLoginPopUpBind() {
        return loginPopUpBind;
    }

    public void attachFilesLink(ActionEvent actionEvent) {
       this.attachFile=true;
    }

    public void sendWithoutAttachement(ActionEvent actionEvent) throws AddressException, MessagingException {
        setMailServerProperties();
        createEmailMessageWidtAtchmnt();
        sendEmail();
    }

    public void setAttachFile(boolean attachFile) {
        this.attachFile = attachFile;
    }

    public boolean isAttachFile() {
        return attachFile;
    }


    public String signInAction() {
        if( emailIdBind.getValue()==null || passwordBind.getValue()==null){
            FacesMessage msg=new FacesMessage("Please Enter EmailId and Password both");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
        }
        else{
        emailId = emailIdBind.getValue().toString();
        pwd = passwordBind.getValue().toString();
            return "goTomail";
        }
        
    }
}
