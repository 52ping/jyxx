package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Jianli;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.JianliDao;









public class JianliDaoImpl extends HibernateDaoSupport implements  JianliDao{


	public void deleteBean(Jianli Jianli) {
		this.getHibernateTemplate().delete(Jianli);
		
	}

	public void insertBean(Jianli Jianli) {
		this.getHibernateTemplate().save(Jianli);
		
	}

	@SuppressWarnings("unchecked")
	public Jianli selectBean(String where) {
		List<Jianli> list = this.getHibernateTemplate().find("from Jianli " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Jianli "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Jianli> selectBeanList(final int start,final int limit,final String where) {
		return (List<Jianli>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Jianli> list = session.createQuery("from Jianli "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Jianli Jianli) {
		this.getHibernateTemplate().update(Jianli);
		
	}
	
	
}
