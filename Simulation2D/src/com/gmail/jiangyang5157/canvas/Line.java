package com.gmail.jiangyang5157.canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Renderable line
 * 
 * @author JiangYang
 * 
 */
public class Line extends Particle {

	/**
	 * Line points to target
	 */
	public Vector2D target = null;

	/**
	 * Width of line
	 */
	private float width = 2;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Line(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.target = new Vector2D(x, y);
	}

	@Override
	public void update(int fps) {
		// TODO Auto-generated method stub
	}

	/**
	 * Draw a line from position to target
	 */
	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.setColor(Color.WHITE);

		// set attribute, such as width, etc.
		g2.setStroke(new BasicStroke(width));

		// draw
		g2.drawLine((int) position.getX(), (int) position.getY(),
				(int) target.getX(), (int) target.getY());
	}

	/**
	 * Get the width of line
	 * 
	 * @return
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Set the width of line
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}
}
