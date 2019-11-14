package dao;

import java.util.List;

import model.Jianli;


public interface JianliDao  {
	
	
	
	public void insertBean(Jianli Jianli);
	
	public void deleteBean(Jianli Jianli);
	
	public void updateBean(Jianli Jianli);

	public Jianli selectBean(String where);
	
	public List<Jianli> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
