package org.geotools.interf;

import javax.swing.JOptionPane;

public class Exception_interface extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String str ;
	public Exception_interface(String str){
		JOptionPane.showMessageDialog(null, str, "Cooriger !",
 				JOptionPane.ERROR_MESSAGE);
   }
}
