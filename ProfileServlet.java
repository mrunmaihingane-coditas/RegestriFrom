import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    PreparedStatement ps;
    ResultSet rs;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();

        HttpSession session= req.getSession();
        if(session.getAttribute("uname")==null)
        {
            resp.sendRedirect("index.html");
        }
        else {
            String name = (String) session.getAttribute("uname");

            ServletContext context = getServletContext();
            try {
                Class.forName(context.getInitParameter("driver"));
                Connection con = DriverManager.getConnection(context.getInitParameter("url"),
                        context.getInitParameter("username"), context.getInitParameter("password"));

                ps=con.prepareStatement("select*from registration where first_name=?");
                ps.setString(1,name);
                rs=ps.executeQuery();
                while(rs.next()){
//
                    int id = rs.getInt(1);
                    String firstname=rs.getString(2);
                    String lastname=rs.getString(3);
                    Date date = rs.getDate(4);
                    String email=rs.getString(5);
                    String add=rs.getString(8);
                    String phn=rs.getString(9);



                    out.println("<h2>Name:" + firstname + lastname + "</h2>");
                    out.println("<h2>DOB:" + date + "</h2>");
                    out.println("<h2>Email:" + email + "</h2>");
                    out.println("<h2>Address:" + add + "</h2>");
                    out.println("<h2>Phone:" + phn + "</h2>");

                }
                resp.setContentType("text/html");
                out.println("<a href='WelcomPage'>Back</a>");
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
