package httpServletWithCrudOperations;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/demo")
public class ServletUserInsertionWithAnnotation extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Set response content type
        res.setContentType("text/html");
        
        PrintWriter out = res.getWriter();

        // Retrieve input parameters
        String name = req.getParameter("name");
        String add = req.getParameter("add");
        String numStr = req.getParameter("num");
        
        // Validate inputs
        if (name == null || name.isEmpty() || add == null || add.isEmpty() || numStr == null || numStr.isEmpty()) {
            out.println("<h3>Invalid input. All fields are required.</h3>");
            return;
        }

        long phno;
        try {
            phno = Long.parseLong(numStr);
        } catch (NumberFormatException e) {
            out.println("<h3>Invalid phone number format.</h3>");
            return;
        }

        // Create entity object and set values
        User d = new User();
        d.setName(name);
        d.setAddress(add);
        d.setPhoneNumber(phno);

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("mona");
            em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            // Persist the entity
            et.begin();
            em.persist(d);
            et.commit();

            out.println("<h3>Data Inserted Successfully</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error inserting data: " + e.getMessage() + "</h3>");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
