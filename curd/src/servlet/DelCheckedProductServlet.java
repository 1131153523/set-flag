package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductService;

/**
 * ɾ��ѡ����Ʒ
 */
public class DelCheckedProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡѡ�е�id
		String[] ids = request.getParameterValues("pid");
		//����service�����ɾ��ѡ����Ʒ
		try {
			new ProductService().delCheckedProduct(ids);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("msg", "ɾ��ʧ��");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
		//�ض���
		response.sendRedirect(request.getContextPath()+"/findAll");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
