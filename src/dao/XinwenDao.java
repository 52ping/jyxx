package dao;

import java.util.List;

import model.Xinwen;


public interface XinwenDao  {
	
	
	
	public void insertBean(Xinwen Xinwen);
	
	public void deleteBean(Xinwen Xinwen);
	
	public void updateBean(Xinwen Xinwen);

	public Xinwen selectBean(String where);
	
	public List<Xinwen> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
