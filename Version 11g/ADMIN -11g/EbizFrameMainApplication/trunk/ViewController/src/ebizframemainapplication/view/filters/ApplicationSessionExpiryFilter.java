package ebizframemainapplication.view.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ApplicationSessionExpiryFilter implements Filter {
    private FilterConfig _filterConfig = null;
   // private static final String __WINDOW_MANAGER_KEY = RichWindowManager.class.getName();
  //  private static final String _WINDOW_ID_KEY = "oracle.adfinternal.view.rich.RedirectTokenId";
    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                                     ServletException {
        String requestedSession = ((HttpServletRequest)request).getRequestedSessionId();
        String currentWebSession = ((HttpServletRequest)request).getSession().getId();
        boolean sessionOk = currentWebSession.equalsIgnoreCase(requestedSession);

      //  HttpServletRequest r = (HttpServletRequest)request;
       // HttpSession s = r.getSession();
        
      //  Enumeration e = s.getAttributeNames();
      /*   while (e.hasMoreElements()) {

            System.out.println("ELEMENT--" + e.nextElement());
            
        }

        System.out.println("window id key :" + _WINDOW_ID_KEY);
        String windowID = null;
        if(s.getAttribute("org.apache.myfaces.trinidadinternal.application.StateManagerImp.ACTIVE_PAGE_STATE.w0")!= null)
        System.out.println(""+s.getAttribute("org.apache.myfaces.trinidadinternal.application.StateManagerImp.ACTIVE_PAGE_STATE.w0").toString());
        
        if (s.getAttribute(_WINDOW_ID_KEY) != null) {
            windowID = s.getAttribute(_WINDOW_ID_KEY).toString();
            if (windowID == null) {
                s.invalidate();
                System.out.println("after session invalidate ");
            }
        }

        System.out.println("window id: " + windowID);

        */
        /*  Map m=((HttpServletRequest)request).getParameterMap();

       */
        // if the requested session is null then this is the first application
        // request and "false" is acceptable
      

        if (!sessionOk && requestedSession != null) {
            // the session has expired or renewed. Redirect request
         
            
            ((HttpServletResponse)response).sendRedirect(_filterConfig.getInitParameter("SessionTimeoutRedirect"));

    
        } else {
           
              chain.doFilter(request, response);
            
        }
    }
}
