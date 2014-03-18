package com.gmail.jiangyang5157.canvas;

/**
 * Abstract renderable particle
 * 
 * @author JiangYang
 * 
 */
public abstract class Particle implements Render {

	/**
	 * Position
	 */
	public Vector2D position = null;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Particle(double x, double y) {
		this.position = new Vector2D(x, y);
	}
}
