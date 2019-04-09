package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import service.ProductService;

/**
 * �޸�
 */
public class GetProductByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡpid
		String pid = request.getParameter("pid");
		System.out.println(pid);
		//����service ͨ��id��ȡ��Ʒ ����product
		Product p=null;
		try {
			p = new ProductService().getProductById(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//��product����request����  ����ת����edit.jsp
		request.setAttribute("bean", p);
		request.getRequestDispatcher("/edit.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
