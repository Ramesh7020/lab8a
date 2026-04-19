package CookieServlet.java;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")   // No web.xml required
public class CookieServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("username");

        int visitCount = 1;

        // Read existing cookies
        Cookie cookies[] = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitCount")) {
                    visitCount = Integer.parseInt(c.getValue());
                    visitCount++;
                }
            }
        }

        // Create cookies
        Cookie nameCookie = new Cookie("username", name);
        Cookie countCookie = new Cookie("visitCount", String.valueOf(visitCount));

        // Set expiry time (60 seconds)
        nameCookie.setMaxAge(60);
        countCookie.setMaxAge(60);

        // Add cookies to response
        response.addCookie(nameCookie);
        response.addCookie(countCookie);

        // Display output
        out.println("<html><body>");

        out.println("<h2>Welcome back " + name + "!</h2>");
        out.println("<p>You have visited this page " + visitCount + " times.</p>");

        // Display cookie list
        out.println("<h3>List of Cookies:</h3>");

        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("Name: " + c.getName() +
                            " | Value: " + c.getValue() + "<br>");
            }
        }

        out.println("<br><b>Note:</b> Cookies will expire in 60 seconds.");

        out.println("</body></html>");
    }
}