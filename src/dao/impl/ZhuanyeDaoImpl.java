package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Zhuanye;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ZhuanyeDao;









public class ZhuanyeDaoImpl extends HibernateDaoSupport implements  ZhuanyeDao{


	public void deleteBean(Zhuanye Zhuanye) {
		this.getHibernateTemplate().delete(Zhuanye);
		
	}

	public void insertBean(Zhuanye Zhuanye) {
		this.getHibernateTemplate().save(Zhuanye);
		
	}

	@SuppressWarnings("unchecked")
	public Zhuanye selectBean(String where) {
		List<Zhuanye> list = this.getHibernateTemplate().find("from Zhuanye " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Zhuanye "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Zhuanye> selectBeanList(final int start,final int limit,final String where) {
		return (List<Zhuanye>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Zhuanye> list = session.createQuery("from Zhuanye "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Zhuanye Zhuanye) {
		this.getHibernateTemplate().update(Zhuanye);
		
	}
	
	
}
