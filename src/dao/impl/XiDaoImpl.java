package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Xi;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.XiDao;









public class XiDaoImpl extends HibernateDaoSupport implements  XiDao{


	public void deleteBean(Xi Xi) {
		this.getHibernateTemplate().delete(Xi);
		
	}

	public void insertBean(Xi Xi) {
		this.getHibernateTemplate().save(Xi);
		
	}

	@SuppressWarnings("unchecked")
	public Xi selectBean(String where) {
		List<Xi> list = this.getHibernateTemplate().find("from Xi " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Xi "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Xi> selectBeanList(final int start,final int limit,final String where) {
		return (List<Xi>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Xi> list = session.createQuery("from Xi "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Xi Xi) {
		this.getHibernateTemplate().update(Xi);
		
	}
	
	
}
