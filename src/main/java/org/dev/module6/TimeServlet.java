package org.dev.module6;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezoneParam = request.getParameter("timezone");
        ZoneId zoneId;

        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                zoneId = ZoneId.of(timezoneParam);
            } catch (Exception e) {
                zoneId = ZoneOffset.UTC; // Якщо часовий пояс некоректний, використовуємо UTC
            }
        } else {
            zoneId = ZoneOffset.UTC;
        }

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head><title>Current Time</title></head>");
            out.println("<body>");
            out.println("<h1>Current Time (UTC)</h1>");
            out.println("<p>" + currentTime.format(formatter) + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
