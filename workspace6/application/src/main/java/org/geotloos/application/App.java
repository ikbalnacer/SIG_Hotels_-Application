package org.geotloos.application;

import org.geotools.interf.Exe;


public class App 
{
    public static void main( String[] args )
    {Exe exe = new Exe();
       try {
		exe.init_sir();
	} catch (Exception e) {
		e.printStackTrace();
	}
    }
}
