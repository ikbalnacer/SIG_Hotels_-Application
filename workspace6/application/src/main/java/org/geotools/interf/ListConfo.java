package org.geotools.interf;



import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListConfo extends JList<Object>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JList<String> list;
	DefaultListModel<String> model;
	
	public ListConfo() 
	{
	}
	
	public ListConfo(ArrayList<String> array) 
	{	
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		for (String s : array)
		{
			ajout(s);
		}
	}
	
	public JList<String> getList()
	{
		return list;
	}
	
	public void ajout(String div)
	{
		model.addElement(div);
	}
	
	public ArrayList<String> getContenu()
	{
		ArrayList<String> temp = new ArrayList<String>();
		
		for (int i = 0; i < model.size(); i++) 
		{
			temp.add((String) model.get(i));
		}
		
		return temp;
	}
	
	public void supprimer(int i)
	{	// ou mettre le bouton supprimer dans un try catch
		if (model.size()>0) 
		{
			model.remove(i);	
		}
	}
}
