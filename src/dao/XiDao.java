package dao;

import java.util.List;

import model.Xi;


public interface XiDao  {
	
	
	
	public void insertBean(Xi Xi);
	
	public void deleteBean(Xi Xi);
	
	public void updateBean(Xi Xi);

	public Xi selectBean(String where);
	
	public List<Xi> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
