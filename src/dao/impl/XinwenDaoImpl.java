package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Xinwen;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.XinwenDao;









public class XinwenDaoImpl extends HibernateDaoSupport implements  XinwenDao{


	public void deleteBean(Xinwen Xinwen) {
		this.getHibernateTemplate().delete(Xinwen);
		
	}

	public void insertBean(Xinwen Xinwen) {
		this.getHibernateTemplate().save(Xinwen);
		
	}

	@SuppressWarnings("unchecked")
	public Xinwen selectBean(String where) {
		List<Xinwen> list = this.getHibernateTemplate().find("from Xinwen " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Xinwen "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Xinwen> selectBeanList(final int start,final int limit,final String where) {
		return (List<Xinwen>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Xinwen> list = session.createQuery("from Xinwen "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Xinwen Xinwen) {
		this.getHibernateTemplate().update(Xinwen);
		
	}
	
	
}
