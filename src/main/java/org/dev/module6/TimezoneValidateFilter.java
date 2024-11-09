package org.dev.module6;

import java.io.IOException;
import java.time.ZoneId;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String timezone = req.getParameter("timezone");
        if (timezone != null && !timezone.isEmpty()) {
            try {
                ZoneId.of(timezone);
            } catch (Exception e) {
                res.setContentType("text/html;charset=UTF-8");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write("<html><body><h1>Invalid timezone</h1></body></html>");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

