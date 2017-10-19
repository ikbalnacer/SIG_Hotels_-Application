package org.geotools.interf;

import javax.swing.JMenu;

public class Menu extends JMenu
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu(String s, char m) 
	{
		super(s);
		this.setMnemonic(m);
	}
}
