package service;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDao;
import domain.PageBean;
import domain.Product;

public class ProductService {

	public List<Product> findAll() throws SQLException {
		return new ProductDao().findAll();
	}

	public void addProduct(Product p) throws SQLException {
		new ProductDao().addProduct(p);
	}

	public Product getProductById(String pid) throws SQLException {
		return new ProductDao().getProductById(pid);
	}

	public void updateProduct(Product p) throws SQLException {
		new ProductDao().updateProduct(p);
	}
	/**
	 * ͨ��idɾ����Ʒ
	 * @param pid
	 * @throws SQLException 
	 */
	public void delProduct(String pid) throws SQLException {
		new ProductDao().delProduct(pid);
	}
	/**
	 * ɾ�������Ʒ
	 * @param ids
	 * @throws SQLException 
	 */
	public void delCheckedProduct(String[] ids) throws SQLException {
		ProductDao pDao = new ProductDao();
		for (String pid : ids) {
			pDao.delProduct(pid);
		}
	}

	public List<Product> findProductByCondition(String name, String kw) throws SQLException {
		return new ProductDao().findProductByCondition(name,kw);
	}

	/**
	 * ��ҳ��ѯ
	 * @param currPage 	  �ڼ�ҳ
	 * @param pageSize   ÿҳչʾ����
	 * @return pageBean
	 * @throws SQLException 
	 */
	public PageBean<Product> showProductByPage(Integer currPage, int pageSize) throws SQLException {
		//��ѯ��ǰҳ���� limit(��ǰҳ-1)*ÿҳչʾ���� �� ÿҳչʾ����
		ProductDao dao = new ProductDao();
		List<Product> list = dao.findProductByPage(currPage,pageSize);
		//��ѯ������
		int totalCount = dao.getCount();
		return new PageBean<Product>(list,currPage,pageSize,totalCount);
	}

}
