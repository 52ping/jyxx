package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Jiuye;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.JiuyeDao;









public class JiuyeDaoImpl extends HibernateDaoSupport implements  JiuyeDao{


	public void deleteBean(Jiuye Jiuye) {
		this.getHibernateTemplate().delete(Jiuye);
		
	}

	public void insertBean(Jiuye Jiuye) {
		this.getHibernateTemplate().save(Jiuye);
		
	}

	@SuppressWarnings("unchecked")
	public Jiuye selectBean(String where) {
		List<Jiuye> list = this.getHibernateTemplate().find("from Jiuye " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Jiuye "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Jiuye> selectBeanList(final int start,final int limit,final String where) {
		return (List<Jiuye>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Jiuye> list = session.createQuery("from Jiuye "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Jiuye Jiuye) {
		this.getHibernateTemplate().update(Jiuye);
		
	}
	
	
}
