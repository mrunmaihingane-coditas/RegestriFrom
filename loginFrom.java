import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.*;
import java.util.Stack;

@WebServlet("/loginFrom")
public class loginFrom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        int cnt = 0;
        String name = "";
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        ServletContext sr = getServletContext();

        try {
            Class.forName(sr.getInitParameter("driver"));
            Connection con = DriverManager.getConnection(sr.getInitParameter("url"), sr.getInitParameter("username"), sr.getInitParameter("password"));
            out.println("JDBC Connection Done");
            out.println("<br>");
            out.println("<br>");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select email,password, first_name from registration");
            int cout=0;
            while (rs.next()){
                String un = rs.getString(1);
                String ps=rs.getString(2);

                if(un.equals(email)&& ps.equals(pwd)){
                    name= rs.getString(3);
                    cout++;
                    break;
                }

            }
            if (cout==0){
                out.println("<h1>Invalid Credentials</h1>");
//                RequestDispatcher requestDispatcher=req.getRequestDispatcher("login.html");
//                requestDispatcher.include(req,resp);
            }
            else {
                HttpSession session=req.getSession();
                session.setAttribute("uname",name);
                resp.sendRedirect("WelcomPage");
            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }



    }
}
