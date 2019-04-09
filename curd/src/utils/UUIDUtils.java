package utils;

import java.util.UUID;

public class UUIDUtils {
	/**
	 * �������һ��id
	 * @return
	 */
	public static String getId() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
	/**
	 * ���������
	 * @return
	 */
	public static String getCode() {
		return getId();
	}

}
