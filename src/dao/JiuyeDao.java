package dao;

import java.util.List;

import model.Jiuye;


public interface JiuyeDao  {
	
	
	
	public void insertBean(Jiuye Jiuye);
	
	public void deleteBean(Jiuye Jiuye);
	
	public void updateBean(Jiuye Jiuye);

	public Jiuye selectBean(String where);
	
	public List<Jiuye> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
