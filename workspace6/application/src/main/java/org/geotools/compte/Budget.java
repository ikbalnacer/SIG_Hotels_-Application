package org.geotools.compte;

public class Budget 
{
	public Budget() 
	{
	}
	
	public Budget(int minPrix, int maxPrix) 
	{
		this.minPrix = minPrix;
		this.maxPrix = maxPrix;
	}

	public int getMinPrix()
	{
		return minPrix;
	}

	public void setMinPrix(int minPrix) 
	{
		this.minPrix = minPrix;
	}

	public int getMaxPrix() 
	{
		return maxPrix;
	}

	public void setMaxPrix(int maxPrix) 
	{
		this.maxPrix = maxPrix;
	}

	
	
	@Override
	public String toString() {
		return "Budget [minPrix=" + minPrix + ", maxPrix=" + maxPrix + "]";
	}



	private int minPrix,maxPrix;
}
