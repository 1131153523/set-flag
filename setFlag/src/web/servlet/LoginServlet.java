package web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import setflag.domain.User;
import setflag.service.UserService;
import setflag.utils.BeanFactory;
import setflag.utils.JwtToken;
import setflag.utils.MD5Utils;

/**
 * �û���¼
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(LoginServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			InputStream is= null;
			is = request.getInputStream();
			String bodyInfo = IOUtils.toString(is, "utf-8");
			log.info("�����Ϣ��"+bodyInfo);
			System.out.println(bodyInfo);
			JSONObject json = JSONObject.fromObject(bodyInfo);
			String username = json.getString("userName");
			String password = json.getString("password");
			password = MD5Utils.md5(password);
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User u = us.login(username,password);
			//����Ϣд��map dataд��map2��
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			if(u==null) {
				map.put("code", 1);
				map.put("msg", "��¼ʧ�ܣ��û������������");
				map.put("data", "");
			}else {
				u.setUser_token(JwtToken.createToken(u.getUser_id()));
				us.addToken(u.getUser_token(),u.getUser_id());
				map2.put("token", u.getUser_token());
				map2.put("username", u.getUsername());
				map2.put("sex", u.getUser_sex());
				map2.put("avatar", u.getUser_avatar());
				map2.put("email", u.getUser_email());
				map2.put("score", u.getUser_score());
				map2.put("nickname", u.getUser_nickname());
				map.put("code", 0);
				map.put("msg", "��¼�ɹ�");
				map.put("data", map2);
			}
			JSONObject res = JSONObject.fromObject(map);
			System.out.println(res.toString());
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
