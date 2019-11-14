package dao;

import java.util.List;

import model.Zhuanye;


public interface ZhuanyeDao  {
	
	
	
	public void insertBean(Zhuanye Zhuanye);
	
	public void deleteBean(Zhuanye Zhuanye);
	
	public void updateBean(Zhuanye Zhuanye);

	public Zhuanye selectBean(String where);
	
	public List<Zhuanye> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
