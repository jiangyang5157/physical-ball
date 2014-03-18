package com.gmail.jiangyang5157.canvas;

/**
 * Abstract renderable round
 * 
 * @author JiangYang
 * 
 */
public abstract class Round extends Particle {

	/**
	 * Radius of round
	 */
	private double radius = 0;

	/**
	 * Volume of round
	 */
	private double volume = 0;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Round(double x, double y, double radius) {
		// TODO Auto-generated constructor stub
		super(x, y);
		setRadius(radius);
	}

	/**
	 * Set the radius of round, and reset the volumn of round (v = 2 * pi * r^2)
	 * 
	 * @param radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		this.volume = Math.PI * radius * radius * 2;
	}

	/**
	 * Get thr radius of the round
	 * 
	 * @return
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Get the volume of round
	 * 
	 * @return
	 */
	public double getVolume() {
		return volume;
	}
}
