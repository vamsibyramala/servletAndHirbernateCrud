package httpServletWithCrudOperations;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@SuppressWarnings("serial")
@WebServlet("/all")
public class FetchAllUsersServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Create EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mona");
        EntityManager em = emf.createEntityManager();

        try {
            // Fetch all users using JPQL
            String q = "SELECT u FROM User u"; // JPQL query to fetch all User entities
            Query query = em.createQuery(q);

            // Execute query and get results
            List<User> users = query.getResultList();

            // Display users in HTML table format
            out.println("<html><body>");
            out.println("<h1>All Users</h1>");
            out.println("<table border='1' cellpadding='10'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Address</th><th>Phone Number</th></tr>");

            for (User u : users) {
                out.println("<tr>");
                out.println("<td>" + u.getId() + "</td>");
                out.println("<td>" + u.getName() + "</td>");
                out.println("<td>" + u.getAddress() + "</td>");
                out.println("<td>" + u.getPhoneNumber() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        } finally {
            em.close();
            emf.close();
            out.close();
        }
    }
}
