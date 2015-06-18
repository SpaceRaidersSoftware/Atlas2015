package edu.ahs.frc.spaceraiders.zodiac.auto;

class GPoint {
	private double x, y;
	
	/**
	 * graph point (coordinate)
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public GPoint(double x, double y){
		//TODO: check bounds
		
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * returns x coordinate of the GPoint
	 * @return the x coordinate
	 */
	public double getx(){
		return x;
	}
	
	/**
	 * returns the y coordinate of the GPoint
	 * @return the y coordinate
	 */
	public double gety(){
		return y;
	}
}
