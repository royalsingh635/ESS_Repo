package ebizframemobileapp;

import com.ess.conn.DBConnection;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import java.io.File;

import javapns.Push;

import javapns.notification.Payload;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushedNotifications;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/ebizframemobileapp")
public class GenericResource {
    public GenericResource() {
    }
    private static final String GOOGLE_SERVER_KEY = "AIzaSyDoEJLL-4QpLdSYYEMOYBBxCwir4TSqo30";
    private static final String MESSAGE_KEY = "message";

    @GET
    @Path("/abc")
    public String getData() {
        try {
            /*    DBConnection bConnection = DBConnection.getDSConnection("APPDS");
            System.out.println("-- " + bConnection); */
            Result result = null;
            if (false) {
                File file = new File("CertificatesProdAPNSKey.p12");
                System.out.println(file.getName() + ": File Object");
                PushedNotifications alert =
                    Push.alert("New " + "Shubham Bansal" + " created", file, "", true,
                               "376eb005ff54951c76d9e9be29460fbc5cbf02fa3bc897ffa257480c4866614d");
                //   "db35657dd41841ec04383bc1fb192ccf7d52f4e4218178d1fda400765bee3b43");

                System.out.println("-->" + alert + "---");
                System.out.println(alert.getFailedNotifications() + " : FMsg");
                System.out.println(alert.getSuccessfulNotifications() + " : SMsg");
            } else {
                Sender sender = new Sender(GOOGLE_SERVER_KEY);
                Message message =
                    new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY,
                                                                                      "New " + "Shubham Bansal" +
                                                                                      " created").build();
                result =
                    sender.send(message,
                                "APA91bGMVU6ZbSJsgY5iSHBGdJUUfOO1fuJKVyiMHVuXHKLKMQRc7GbTqK0rvJ8avIZoVAZOg-m-xVu73_xQtakC6Mn-1kp_nG7tKEmZe67v3xu618QheQ8O9IK5vaxG_cvF1PFXuiyv",
                                1);
                System.out.println("result : -" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("jsgdf");
        return "sdjf";
    }
}
