package httpServletWithCrudOperations;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/fetchName")
public class ServletFetchWithName extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the 'name' parameter from the request
        String name = req.getParameter("name");

        // Prepare response writer
        PrintWriter out = resp.getWriter();
        

        // Initialize EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mona");
        EntityManager em = emf.createEntityManager();

        try {
            // Use a named parameter query to fetch the user by name
            String jpql = "SELECT u FROM User u WHERE u.name = :name";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("name", name);

            // Execute the query
            User user = query.getSingleResult();

            // Display the result in the browser
            if (user != null) {
                out.println("<html><body style='font-family: Arial; background-color: #f9f9f9;'>");
                out.println("<h1>User Details</h1>");
                out.println("<p><strong>ID:</strong> " + user.getId() + "</p>");
                out.println("<p><strong>Name:</strong> " + user.getName() + "</p>");
                out.println("<p><strong>Address:</strong> " + user.getAddress() + "</p>");
                out.println("<p><strong>Phone Number:</strong> " + user.getPhoneNumber() + "</p>");
                out.println("<h3 style='color: green;'>Fetch Successful!</h3>");
                out.println("</body></html>");
            } else {
                out.println("<html><body><h1>User not found</h1></body></html>");
            }
        } catch (Exception e) {
            out.println("<html><body><h1>Error: " + e.getMessage() + "</h1></body></html>");
        } finally {
            em.close();
            emf.close();
        }
    }
}
