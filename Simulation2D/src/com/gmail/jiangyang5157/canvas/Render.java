package com.gmail.jiangyang5157.canvas;

import java.awt.Graphics2D;

/**
 * A renderable component
 * 
 * @author JiangYang
 * 
 */
public interface Render {

	/**
	 * Update before render
	 * 
	 * @param fps
	 */
	public void update(int fps);

	/**
	 * Rendering
	 * 
	 * @param g2
	 */
	public void render(Graphics2D g2);
}
