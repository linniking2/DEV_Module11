package org.dev.module6;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String timezoneParam = request.getParameter("timezone");
        ZoneId zoneId = getZoneIdFromRequestOrCookie(request, response, timezoneParam);

        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("formattedTime", currentTime.format(formatter));
        ctx.setVariable("timezone", zoneId.toString());

        ThymeleafConfig.getTemplateEngine(getServletContext())
                .process("time", ctx, response.getWriter());
    }

    private ZoneId getZoneIdFromRequestOrCookie(HttpServletRequest request, HttpServletResponse response, String timezoneParam) {
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                ZoneId zoneId = ZoneId.of(timezoneParam);
                Cookie cookie = new Cookie("lastTimezone", timezoneParam);
                cookie.setMaxAge(60 * 60 * 24); // зберігаємо на 1 день
                response.addCookie(cookie);
                return zoneId;
            } catch (Exception ignored) {}
        }

        // Спробувати отримати часовий пояс з Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("lastTimezone".equals(cookie.getName())) {
                    try {
                        return ZoneId.of(cookie.getValue());
                    } catch (Exception ignored) {}
                }
            }
        }

        return ZoneId.of("UTC");
    }
}

