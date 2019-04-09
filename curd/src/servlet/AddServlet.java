package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import domain.Product;
import service.ProductService;
import utils.UUIDUtils;

/**
 * �����Ʒ
 */
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ñ���
		request.setCharacterEncoding("utf-8");
		//��չ ���ƻ���
		//1 ��ȡ���������
		String r_lingpai = request.getParameter("r_lingpai");
		String s_lingpai = (String) request.getSession().getAttribute("s_lingpai");
		//2 �Ƴ�session�е�����
		request.getSession().removeAttribute("s_lingpai");
		//3 �Ƚ�����������
		if(s_lingpai==null||!s_lingpai.equals(r_lingpai)) {
			//�Ѿ��ύ���ˣ���������Ϣ����request���� ��ת��msg.jsp
			request.setAttribute("msg", "��Ʒ���ύ");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
			return;
		}
		//��װ����
		Product p = new Product();
		try {
			BeanUtils.populate(p, request.getParameterMap());
			//����pid��ʱ��
			p.setPid(UUIDUtils.getId());
			p.setPdate(new Date());
			//����service�����Ӳ���
			new ProductService().addProduct(p);
			//ҳ����ת
			request.getRequestDispatcher("/findAll").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "���ʧ��");
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
