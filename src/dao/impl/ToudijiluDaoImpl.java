package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Toudijilu;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ToudijiluDao;









public class ToudijiluDaoImpl extends HibernateDaoSupport implements  ToudijiluDao{


	public void deleteBean(Toudijilu Toudijilu) {
		this.getHibernateTemplate().delete(Toudijilu);
		
	}

	public void insertBean(Toudijilu Toudijilu) {
		this.getHibernateTemplate().save(Toudijilu);
		
	}

	@SuppressWarnings("unchecked")
	public Toudijilu selectBean(String where) {
		List<Toudijilu> list = this.getHibernateTemplate().find("from Toudijilu " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Toudijilu "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Toudijilu> selectBeanList(final int start,final int limit,final String where) {
		return (List<Toudijilu>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Toudijilu> list = session.createQuery("from Toudijilu "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Toudijilu Toudijilu) {
		this.getHibernateTemplate().update(Toudijilu);
		
	}
	
	
}
