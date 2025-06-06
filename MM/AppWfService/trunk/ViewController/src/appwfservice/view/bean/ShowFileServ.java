   package appwfservice.view.bean;
    import java.io.BufferedInputStream;
    import java.io.BufferedOutputStream;
    import java.io.Closeable;
    import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.io.PrintWriter;

    import java.net.URLDecoder;

    import javax.servlet.*;
    import javax.servlet.http.*;

    public class ShowFileServ extends HttpServlet {

      private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
      private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
      private String filePath;


      public void init(ServletConfig config) throws ServletException {
        super.init(config);
      }

      public void doGet(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
                                                             IOException {

        // Get requested file by path info.
        String name = request.getParameter("name");
        this.filePath = request.getParameter("path");
        String filetype = request.getParameter("type");
        System.out.println("FILE NAME :"+name);
        System.out.println("FILE PATH :"+filePath);
        System.out.println("FILE TYPE :"+filetype);
        // Check if file is actually supplied to the request URI.
        if (name == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
          return;
        }


        // I want to invoke a pdf that is located on the machine where the application is running
        

        // Decode the file name (might contain spaces and on) and prepare file object.
        File file = new File(filePath);

        // Check if file actually exists in filesystem.
        if (!file.exists()) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
          return;
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(file.getName());

        // If content type is unknown, then set the default value.
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        // To add new content types, add new mime-mapping entry in web.xml.
        if (contentType == null) {
          contentType = "application/octet-stream";
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition",
                           "filename=\"" + file.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
          // Open streams.
          input =
              new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
          output =
              new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

          // Write file contents to response.
          byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
          int length;
          while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
          }
        } finally {
          // Gently close streams.
          close(output);
          close(input);
        }
      }

      private static void close(Closeable resource) {
        if (resource != null) {
          try {
            resource.close();
          } catch (IOException e) {
            // Do your thing with the exception. Print it, log it or mail it.
            e.printStackTrace();
          }
        }
      }
    }
