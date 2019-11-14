package dao;

import java.util.List;

import model.Toudijilu;


public interface ToudijiluDao  {
	
	
	
	public void insertBean(Toudijilu Toudijilu);
	
	public void deleteBean(Toudijilu Toudijilu);
	
	public void updateBean(Toudijilu Toudijilu);

	public Toudijilu selectBean(String where);
	
	public List<Toudijilu> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
