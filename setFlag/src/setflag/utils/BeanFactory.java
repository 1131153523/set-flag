package setflag.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	/**
	 * ʵ�幤��
	 * @param id
	 * @return
	 */
	public static Object getBean(String id) {
		//ͨ��id��ȡһ��ָ����ʵ����
		try {
			//��ȡdocument����
			Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			//��ȡָ����bean����
			Element ele = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//��ȡbean�����class����
			String value = ele.attributeValue("class");
			//4.���� ��ǰ���߼�ֱ�ӷ��ص���ʵ��	
			//return Class.forName(value).newInstance();
			
			//5.���ڶ�service��add�������м�ǿ ����ֵ���Ǵ������
			final Object obj=Class.forName(value).newInstance();
			//��service��ʵ����
			if(id.endsWith("Service")){
				Object proxyObj = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//�����ж��Ƿ���õ�add����regist
						if("add".equals(method.getName()) || "regist".equals(method.getName())){
							System.out.println("��Ӳ���");
							return method.invoke(obj, args);
						}
						
						return method.invoke(obj, args);
					}
				});
				
				//����service�������ص��Ǵ������
				return proxyObj;
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getBean("ProductService"));
	}

}
