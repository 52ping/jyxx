package dao;

import java.util.List;

import model.Liuyan;


public interface LiuyanDao  {
	
	
	
	public void insertBean(Liuyan Liuyan);
	
	public void deleteBean(Liuyan Liuyan);
	
	public void updateBean(Liuyan Liuyan);

	public Liuyan selectBean(String where);
	
	public List<Liuyan> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
