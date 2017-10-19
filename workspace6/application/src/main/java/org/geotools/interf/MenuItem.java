package org.geotools.interf;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuItem extends JMenuItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuItem(String n,boolean b, char k) 
	{
		super(n);
		this.setEnabled(b);
		k= Character.toUpperCase(k);
		int ke= k;
		this.setAccelerator(KeyStroke.getKeyStroke( ke,2 ));
	}
	
}
