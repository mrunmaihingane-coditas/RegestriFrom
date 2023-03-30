import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/WelcomPage")
public class WelcomPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession session=req.getSession();

        PrintWriter out = resp.getWriter();
        String name = (String) session.getAttribute("uname");

        if(name==null){
            resp.sendRedirect("login.html");
        }
        else {
            out.println("WELCOME " + name);
            out.println("<br>");
            out.println("<br>");
//        RequestDispatcher requestDispatcher= req.getRequestDispatcher("Logout");
//        out.println("Login successfully");
//        requestDispatcher.forward(req,resp);
            out.println("<a href ='Logout'>logout</a>");
            out.println("<br>");
            out.println("<a href ='ProfileServlet'>profile</a>");
        }
    }
}
