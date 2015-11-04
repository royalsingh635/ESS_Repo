package appOpportunity.view.bean;

import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichTextEditor;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class JavaEmailBean {
    private RichInputText toBind;
    private RichInputText subjectBind;
    private RichInputText emailIdBind;
    private RichInputText passwordBind;
    private String emailId;
    private String pwd;
    private String file_name;
    private RichInputText showPopUpBind;
    private RichPopup loginPopUpBind;
    private boolean attachFile = false;
    private RichTextEditor messageBinding;
    //private static String reciever;
    private Properties emailProperties;
    private Session mailSession;
    private MimeMessage emailMessage;
    private String toWhom;
    private String message;
    private String subject;
    private RichInputFile attachmentBinding;
    private Boolean isAttached;

    public JavaEmailBean() {
    }
    
    public void sendMailButton(ActionEvent actionEvent) throws AddressException, MessagingException {      
        setMailServerProperties();
        if (subjectBind.getValue()!=null) {
            if (attachmentBinding.getValue() != null) {
                UploadedFile file = (UploadedFile)attachmentBinding.getValue();
                file_name = file.getFilename();
                System.out.println(file_name);
                createEmailMessageWidtAtchmnt();
            } else {
                createEmailMessage();

            }
            sendEmail();
        }
    }

    public  void setMailServerProperties() {
        String emailPort = "587"; //gmail's smtp port
        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
    } 
       
    public void createEmailMessageWidtAtchmnt() throws AddressException, MessagingException {
        toWhom = toBind.getValue().toString(); // Value From page(Reciepient)
        subject = subjectBind.getValue().toString(); // Value from page(Subject)
        message = messageBinding.getValue().toString(); // Value from page(Message/Mail)
                
        String[] toEmails = { toWhom };
        String emailSubject = subject;
        String emailBody = message;
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
        
        
        /* String filename = file_name;
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename); */

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
        System.out.println(emailMessage);
        System.out.println("createEmailMessage execution finished");

    }

    public void createEmailMessage() throws AddressException, MessagingException {
        toWhom = toBind.getValue().toString();
        subject = subjectBind.getValue().toString();
        message = messageBinding.getValue().toString();
        String[] toEmails = { toWhom };
        System.out.println("To whom is-->{" + toWhom + "} total contact is-->" + toEmails.length);
        String emailSubject = subject;
        String emailBody = message;
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
        System.out.println(emailMessage);
        System.out.println("exec 2 finished");


    }

    public void sendEmail() {

        String emailHost = "smtp.gmail.com";
        String fromUser = "mailatess27"; //just the id alone without @gmail.com
        String fromUserEmailPassword = "ess12345";
        Transport transport = null;
        try {
            transport = mailSession.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            System.out.println("No such Provider Exception");
        }
        try {
            transport.connect(emailHost, fromUser, fromUserEmailPassword);
            System.out.println("transport connected");
            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
            System.out.println("transport sent msg");
            transport.close();
            System.out.println("transport closed");
            StringBuilder msgEmail = new StringBuilder("<html><body><b>"+resolvElDCMsg("#{bundle['MSG.946']}").toString()+"</b></body></html>");//MSG.946 Email Sent
            FacesMessage msg = new FacesMessage(msgEmail.toString());
            System.out.println(msg);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage("No Network Error !", msg);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("somthing went wrong-->Messaging Exception");
            StringBuilder msgEmail = new StringBuilder("<html><body><b>"+resolvElDCMsg("#{bundle['MSG.945']}").toString()+"</b>");//MSG.945 Something went wrong !
            msgEmail.append("<p><b>"+resolvElDCMsg("#{bundle['MSG.948']}").toString()+"</b></p>"); //MSG.948  please provide correct parameters
            msgEmail.append("</body></html>");
            FacesMessage msg = new FacesMessage(msgEmail.toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage("No Network Error !", msg);

        }

    }

    public void uploadedFileAttachmentVCE(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
            file_name = file.getFilename();
            System.out.println(file_name);
            setIsAttached(true);
            
        }else{
            setIsAttached(false);
        }
        
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

 /*    public void setMessageBind(RichInputText messageBind) {
        this.messageBinding = messageBind;
    }

    public RichInputText getMessageBind() {
        return messageBinding;
    } */

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
        messageBinding.setValue("");
        this.attachFile = false;
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
        this.attachFile = true;
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


 /*    public String signInAction() {
        if (emailIdBind.getValue() == null || passwordBind.getValue() == null) {
            FacesMessage msg = new FacesMessage("Please Enter EmailId and Password both");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        } else {
            emailId = emailIdBind.getValue().toString();
            pwd = passwordBind.getValue().toString();
            return "goTomail";
        }

    } */

    public void setMessageBinding(RichTextEditor messageBinding) {
        this.messageBinding = messageBinding;
    }

    public RichTextEditor getMessageBinding() {
        return messageBinding;
    }
    
    public void setAttachmentBinding(RichInputFile attachmentBinding) {
        this.attachmentBinding = attachmentBinding;
    }

    public RichInputFile getAttachmentBinding() {
        return attachmentBinding;
    }
    
    
/*     public  void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {

           Send(username, password, recipientEmail,"", title, message);
       } */

    /* public  void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
           final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";


           // Get a Properties object
           Properties props = System.getProperties();
           props.setProperty("mail.smtps.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.setProperty("mail.smtps.auth", "true");

           /*
           If set to false, the QUIT command is sent and the connection is immediately closed. If set
           to true (the default), causes the transport to wait for the response to the QUIT command.

           ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                   http://forum.java.sun.com/thread.jspa?threadID=5205249
                   smtpsend.java - demo program from javamail
           */
           /*props.put("mail.smtps.quitwait", "false");

           Session session = Session.getInstance(props, null);

           // -- Create a new message --
           final MimeMessage msg = new MimeMessage(session);

           // -- Set the FROM and TO fields --
           msg.setFrom(new InternetAddress(username + "@gmail.com"));
           msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

           if (ccEmail.length() > 0) {
               msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
           } */

     /*       msg.setSubject(title);
           msg.setText(message, "utf-8");
           msg.setSentDate(new Date());

           SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

           t.connect("smtp.gmail.com", username, password);
           t.sendMessage(msg, msg.getAllRecipients());
           t.close();
       }  */


    public void setIsAttached(Boolean isAttached) {
        this.isAttached = isAttached;
    }

    public Boolean getIsAttached() {
        return isAttached;
    }
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 
    
    
    
    
}

