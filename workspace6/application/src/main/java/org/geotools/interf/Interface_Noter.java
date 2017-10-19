package org.geotools.interf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.geotools.compte.Objet_G;
import org.geotools.controlers.Ctrl_noter;

public class Interface_Noter extends JDialog
{	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon imageAvant= new ImageIcon("etoil_avant.png"),imageApres= new ImageIcon("etoil_apres.jpg");
	private int valeur=0;
	Objet_G obj =null;
    private Ctrl_noter noter_ctr = new Ctrl_noter();
	private void init() 
	{ 
		final JPanel etoilePanle = new JPanel();

		for (int i = 0; i < 5; i++)
		{
			etoilePanle.add(new JLabel(imageAvant));
		}		

		etoilePanle.addMouseMotionListener(new MouseMotionListener()
		{

			public void mouseMoved(MouseEvent arg0) 
			{
				Component index = etoilePanle.getComponentAt(arg0.getX(), arg0.getY());
				int x = Integer.valueOf((int) index.getLocation().getX());

				switch (x)
				{
				case 5:  	valeur=1			;	break;
				case 40:	valeur=2			;	break;
				case 75: 	valeur=3			;	break;
				case 110:	valeur=4			;	break;
				case 145:	valeur=5			;	break;

				default:valeur=0;break;
				}

				etoilePanle.removeAll();

				for (int i = 0; i < 5; i++)
				{
					if (valeur>i)
					{
						etoilePanle.add(new JLabel(imageApres));
					}else 
					{
						etoilePanle.add(new JLabel(imageAvant));
					}
				}
				etoilePanle.updateUI();
			}

			
			public void mouseDragged(MouseEvent arg0) 
			{
			
			}
		});



		JPanel infoChambrePanel = new JPanel();
		infoChambrePanel.setLayout(new FlowLayout());
		infoChambrePanel.add(etoilePanle);

		JPanel controlPanle = new JPanel();
		JButton retourBouton = new JButton("Retour");
		JButton okBouton = new JButton("noter");

		okBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{  if(obj.getNote()>0){
			   valeur=(int) ((valeur+obj.getNote())/2);  
			   }
               noter_ctr.noter(valeur,obj.getLocalisation());
               setVisible(false);
			   Interface_Selection.distroy_interface_noter();
			}
		});

		retourBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				setVisible(false);
				Interface_Selection.distroy_interface_noter();
			}      
		});

		controlPanle.add(okBouton);
		controlPanle.add(retourBouton);

		etoilePanle.setBackground(Color.black);
		infoChambrePanel.setBackground(Color.black);
		controlPanle.setBackground(Color.black);
		this.add(infoChambrePanel,BorderLayout.CENTER);
		this.add(controlPanle,BorderLayout.SOUTH);		
	}


	public Interface_Noter(JFrame parent,Objet_G obj ) 
	{
		super(parent, "Noter", true);
		this.setLayout(new BorderLayout());
		this.obj = obj;
		this.init();		
		this.setSize(200, 100);	
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
	}
	
}