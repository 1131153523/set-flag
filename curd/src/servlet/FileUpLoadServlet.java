package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * �ļ��ϴ�demo
 */
public class FileUpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String username = request.getParameter("username");
		String file = request.getParameter("f");
		System.out.println(username); //null ������
		System.out.println(file); //null*/
		
		//���������ļ����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//���������ϴ�����
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			//��������
			List<FileItem> list = upload.parseRequest(request);
			//����list
			for (FileItem fi : list) {
				//�ж�����ͨ���ϴ���������ļ��ϴ����
				if (fi.isFormField()) {
					//��ͨ�ϴ����
					//��ȡname����
					String name = fi.getName();
					//��ȡֵ
					String string = fi.getString("utf-8");
					System.out.println(name+":"+string);
				}else {
					//�ļ��ϴ����
					//��ȡname����
					String name = fi.getFieldName();
					//��ȡ�ļ�����
					String filename = fi.getName();
					//��ȡ�ļ�����
					InputStream is = fi.getInputStream();
					System.out.println(name+"--"+filename+"--"+is);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
