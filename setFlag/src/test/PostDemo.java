package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
 
public class PostDemo {
	public static void main(String[] args){
		try{
			// Configure and open a connection to the site you will send the request
			URL url = new URL("http://www.iana.org/domains/example/");
			URLConnection urlConnection = url.openConnection();
			// ����doOutput����Ϊtrue��ʾ��ʹ�ô�urlConnectionд������
			urlConnection.setDoOutput(true);
			// �����д�����ݵ��������ͣ���������Ϊapplication/x-www-form-urlencoded����
			urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// �õ���������������
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			// ������д�������Body
			out.write("message = Hello World chszs");
			out.flush();
			out.close();
			
			// �ӷ�������ȡ��Ӧ
			InputStream inputStream = urlConnection.getInputStream();
			String encoding = urlConnection.getContentEncoding();
			String body = IOUtils.toString(inputStream, encoding);
			System.out.println(body);
		}catch(IOException e){
			Logger.getLogger(PostDemo.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
