package com.gmail.jiangyang5157.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Renderable Ball
 * 
 * @author JiangYang
 */
public class Ball extends Round implements Comparable<Ball> {

	/**
	 * Velocity
	 */
	public Vector2D velocity = null;

	/**
	 * Default density
	 */
	public static final double DEFAULT_DENSITY = 4;

	/**
	 * Density
	 */
	private double density = DEFAULT_DENSITY;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Ball(double x, double y, double radius) {
		super(x, y, radius);
		// TODO Auto-generated constructor stub
		this.velocity = new Vector2D(0, 0);
	}

	@Override
	public void update(int fps) {
		// TODO Auto-generated method stub
		if (fps == 0) {
			return;
		}
		// System.out.println("update - velocity = " + velocity);

		// double values lose accuracy, when frictionFactor = 100, velocity
		// still change slightly,except makes fps is a content value
		// when fps = 71, around 42
		double elapsedSeconds = 1.0 / fps;
		// update graviry
		velocity.shiftY(Physics.gravity * (elapsedSeconds));
		// update position
		position.shift(velocity.getX() * elapsedSeconds, velocity.getY()
				* elapsedSeconds);
		/*
		 * revise accuracy
		 */
		if (Vector2D.doubleEquals(velocity.getX(), 0, Vector2D.EPSILON)) {
			velocity.setX(0);
		}
		// if (Vector2D.doubleEquals(velocity.getY(), 0, Vector2D.EPSILON)) {
		// velocity.setY(0);
		// }
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		// System.out.println("render - velocity = " + velocity);

		// set color
		// g2.setColor(Color.RED);
		int massMonitor = (int) (getMass() % 255);
		g2.setColor(new Color(255, massMonitor, massMonitor));

		/*
		 * draw
		 */
		double d = getRadius() * 2;
		// final int velocityMonitor = 5000;
		// if (velocity.getLengthSQ() < velocityMonitor) {
		// g2.drawOval((int) (position.getX() - getRadius()),
		// (int) (position.getY() - getRadius()), (int) d, (int) d);
		// } else {
		g2.fillOval((int) (position.getX() - getRadius()),
				(int) (position.getY() - getRadius()), (int) d, (int) d);
		// }
	}

	/**
	 * Check collision with an other ball
	 * 
	 * @param b2
	 */
	public void checkCollision(Ball b2, int fps) {
		// TODO Auto-generated method stub
		if (this == b2) {
			// same ball
			return;
		}

		if (!this.getRect().intersects(b2.getRect())) {
			// bounding box, avoid calculate Math.sqrt() each time
			// it doesnt matter now, because i use getLengthSQ() instead of
			// getLength()
			return;
		}

		double sumRadius = this.getRadius() + b2.getRadius();
		// a vector points from b2 to b1 (position)
		Vector2D collision = (this.position.subtract(b2.position));
		double distanceSQ = collision.getLengthSQ();

		if (Vector2D.doubleLess(distanceSQ, sumRadius * sumRadius)) {
			// colliding

			if (Vector2D.doubleEquals(distanceSQ, 0)) {
				// they are exactly overlap, also avoid div by zero
				collision.set(1.0, 0.0);
				distanceSQ = 1.0;
				return;
			}

			// because they are colliding each other, has some overlap, so,
			// update their position by their mass, avoid merging, based on
			// their mass
			// points from b2 to b1 (length = overlap part)
			double distance = Math.sqrt(distanceSQ);
			Vector2D overlap = collision.multiply((sumRadius - distance)
					/ distance);

			double mass1 = this.getMass();
			double mass2 = b2.getMass();
			double massSum = mass1 + mass2;
			this.position.addToSelf(overlap.multiply(mass2 / massSum));
			b2.position.subtractToSelf(overlap.multiply(mass1 / massSum));

			// // create the components of the velocity vectors which are
			// parallel
			// // to the collision.
			collision.normalizeToSelf(distance);

			double impulse = (this.velocity.subtract(b2.velocity))
					.dotProduct(collision);
			// a dot b
			// >0: 0-90
			// <0: 90-180
			if (Vector2D.doubleLess(impulse, 0)) {
				// total collision impulse, calculate friction factor, the
				// friction affect only once, assign impulse by their mass
				double impulseSum = (impulse) * (Physics.frictionFactor + 1.0);
				Vector2D impulseSumVector = collision.multiply(impulseSum);

				// v1' = v1 + V_imulse2 - V_imluse1
				// v1' = v1 + V_c*(imlulse2 - imluse1)
				// v1' = v1 + V_c*((imlulse2 - imluse1)*2 * mass2/(mass1 +
				// mass2))
				// v2' = v2 + V_c*((imlulse1 - imluse2)*2 * mass1/(mass1 +
				// mass2))
				Vector2D impulseVector1 = impulseSumVector.multiply(-mass2
						/ massSum);
				Vector2D impulseVector2 = impulseSumVector.multiply(mass1
						/ massSum);

				this.velocity.addToSelf(impulseVector1);
				b2.velocity.addToSelf(impulseVector2);

				// double elapsedSeconds = 1.0 / fps;
				// if (this.velocity.getY() >= )
				// if (this.velocity.getLengthSQ() >=
				// impulseVector1.getLengthSQ() * elapsedSeconds *
				// elapsedSeconds){
				// this.velocity.addToSelf(impulseVector1);
				// this.isActive = true;
				// }else{
				// this.isActive = false;
				// }
				// if (b2.velocity.getLengthSQ() >= impulseVector2.getLengthSQ()
				// * elapsedSeconds * elapsedSeconds){
				// b2.velocity.addToSelf(impulseVector2);
				// b2.isActive = true;
				// }else{
				// b2.isActive = false;
				// }
				// System.out.println("impulseVector1 : "+impulseVector1+", impulseVector2: "+impulseVector2);
				// System.out.println("A +: "+impulseSumVector.multiply(-mass2
				// / massSum)+", -: "+impulseSumVector
				// .multiply(mass1 / massSum));

			} else {
				// they are already moving away from each other
			}
		}
	}

	/**
	 * bounding box
	 * 
	 * @return Rectangle
	 */
	public Rectangle getRect() {
		int x = (int) (position.getX() - getRadius());
		int y = (int) (position.getY() - getRadius());
		int d = (int) (getRadius() * 2);
		return new Rectangle(x, y, d, d);
	}

	/**
	 * Get mass = volume * density
	 * 
	 * @return
	 */
	public double getMass() {
		return getVolume() * density;
	}

	/**
	 * Get density
	 * 
	 * @return
	 */
	public double getDensity() {
		return density;
	}

	/**
	 * Set density
	 * 
	 * @param density
	 */
	public void setDensity(double density) {
		this.density = density;
	}

	/**
	 * Compare an other ball by their x coord
	 */
	@Override
	public int compareTo(Ball b2) {
		// TODO Auto-generated method stub
		int ret = 0;

		if (Vector2D.doubleGreater(this.position.getX() - this.getRadius(),
				b2.position.getX() - b2.getRadius())) {
			ret = 1;
		} else if (Vector2D.doubleLess(this.position.getX() - this.getRadius(),
				b2.position.getX() - b2.getRadius())) {
			ret = -1;
		} else {
			ret = 0;
		}

		return ret;
	}
}
