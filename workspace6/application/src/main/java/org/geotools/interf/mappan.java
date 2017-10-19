package org.geotools.interf;

import java.awt.Image;

import org.geotools.map.MapContent;
import org.geotools.swing.JMapPane;

public class mappan extends JMapPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	Image img;
	static boolean localiser = false;
   public  mappan(MapContent c) {
	   super(c);
	   System.out.println("yeah");
   }
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
