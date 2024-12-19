package httpServletWithCrudOperations;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/delete")
public class ServletWithDeletion extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mona");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		User u = em.find(User.class, id);
		
		if(u!=null)
		{
			et.begin();
			em.remove(u);
			et.commit();
			
			System.out.println(" Data Deleted Successfull");
			
			out.print("Data is Found and Deleted Sucsess");
		}
		else {
			System.out.println(" Data is  Not Found ");
			
			out.print("Data is Not Found and Not  Deleted Sucsess");
		}
		
	}

}
