package tempVoucherList.view.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplSessionExpiryFilter implements Filter {
    public ApplSessionExpiryFilter() {
        super();
    }
    private FilterConfig fconfig = null;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        fconfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String requestedSession = ((HttpServletRequest)servletRequest).getRequestedSessionId();
        String currentWebSession = ((HttpServletRequest)servletRequest).getSession().getId();
        String requestUri=((HttpServletRequest)servletRequest).getRequestURI();
                boolean sessionOk = currentWebSession.equalsIgnoreCase(requestedSession);
            //    System.out.println("==========***==========");
       // System.out.println("In filter :--Current SessionId:"+currentWebSession+"--Remote Port"+servletRequest.getRemotePort()+"---"+servletRequest.getRemoteAddr());
       // System.out.println("Request URI :"+requestUri);
        if (!sessionOk && requestedSession != null){
                System.out.println("Invalid Session");
            ((HttpServletResponse)servletResponse).sendRedirect(requestUri);
        }else{
         //   System.out.println("session is OK");
         //   System.out.println("----------***------------");
            filterChain.doFilter(servletRequest, servletResponse);
           
        }
    }

    @Override
    public void destroy() {
        fconfig=null;
    }
}
