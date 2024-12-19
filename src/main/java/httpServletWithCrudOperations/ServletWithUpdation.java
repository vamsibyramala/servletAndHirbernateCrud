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
@WebServlet("/update")
public class ServletWithUpdation extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		
		int id1 = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String add = req.getParameter("add");
		long num = Long.parseLong(req.getParameter("num"));
		
		PrintWriter out = resp.getWriter();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mona");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		User u =em.find(User.class, id);
		
		if(u!=null)
		{
			u.setId(id1);
			u.setName(name);
			u.setAddress(add);
			u.setPhoneNumber(num);
			
			et.begin();
			em.merge(u);
			et.commit();
			
			System.out.println("Data Updated Successfull at " + id);
			
			out.print("Data Updated Successfull" +id);
			
		}else {
             System.out.println("Data Not been Updated Successfull " + id);
			
			out.print("Data Fail's to Updated Successfull " + id);
		}
		
		out.close();
		
	}

}
