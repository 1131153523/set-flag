package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import service.ProductService;

/**
 * ������ѯ
 */
public class FindProductByConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ñ���
		request.setCharacterEncoding("utf-8");
		//������������
		String name = request.getParameter("name");
		String kw = request.getParameter("kw");
		List<Product> list = null;
		
		//����service��ɲ�ѯ���� ����list
		try {
			list = new ProductService().findProductByCondition(name,kw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//��list����request���� ����ת��
		request.setAttribute("list", list);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
