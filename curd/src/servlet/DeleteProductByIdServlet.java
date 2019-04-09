package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductService;

/**
 * ɾ��
 */
public class DeleteProductByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡpid��ֵ
		String pid = request.getParameter("pid");
		//����service ���ɾ������
		try {
			new ProductService().delProduct(pid);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("msg", "ɾ��ʧ��");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
		//�ض���findAll
		response.sendRedirect(request.getContextPath()+"/findAll");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
