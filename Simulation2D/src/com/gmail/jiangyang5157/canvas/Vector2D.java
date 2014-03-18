package com.gmail.jiangyang5157.canvas;

import java.io.Serializable;

/**
 * An Vector represents the position and direction in a 2 dimensional space
 * 
 * @author JiangYang
 * 
 */
public class Vector2D implements Serializable {

	private static final long serialVersionUID = 2305812345233693951L;

	/**
	 * To revise accuracy of calculating double value
	 */
	public static final double EPSILON = 0.000001;

	/**
	 * x coord in the 2D space
	 */
	private double x = 0;

	/**
	 * y coord in the 2D space
	 */
	private double y = 0;

	/**
	 * Constructor
	 */
	public Vector2D() {
		this.set(0, 0);
	}

	/**
	 * Constructor
	 * 
	 * @param v2
	 */
	public Vector2D(Vector2D v2) {
		this.set(v2.x, v2.y);
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.set(x, y);
	}

	/**
	 * Shift x
	 * 
	 * @param shiftValue
	 * @return this
	 */
	public Vector2D shiftX(double shiftValue) {
		setX(getX() + shiftValue);
		return this;
	}

	/**
	 * Shift y
	 * 
	 * @param shiftValue
	 * @return this
	 */
	public Vector2D shiftY(double shiftValue) {
		setY(getY() + shiftValue);
		return this;
	}

	/**
	 * Shift
	 * 
	 * @param x
	 * @param y
	 * @return this
	 */
	public Vector2D shift(double x, double y) {
		shiftX(x);
		shiftY(y);
		return this;
	}

	/**
	 * @param v2
	 * @return A new Vector2D caculate by adding an Vector2D with this Vector2D
	 */
	public Vector2D add(Vector2D v2) {
		Vector2D ret = new Vector2D(this);
		ret.addToSelf(v2);
		return ret;
	}

	/**
	 * Add an Vector2D to this Vector2D
	 * 
	 * @param v2
	 * @return this
	 */
	public Vector2D addToSelf(Vector2D v2) {
		this.set(this.getX() + v2.getX(), this.getY() + v2.getY());
		return this;
	}

	/**
	 * v3 = v1 subtract v2; v3: v2 points to v1
	 * 
	 * @param v2
	 * @return A new Vector2D caculate by subtracting an Vector2D with this
	 *         Vector2D
	 */
	public Vector2D subtract(Vector2D v2) {
		Vector2D ret = new Vector2D(this);
		ret.subtractToSelf(v2);
		return ret;
	}

	/**
	 * v3 = v1 subtract v2; v3: v2 points to v1
	 * 
	 * @param v2
	 * @return this
	 */
	public Vector2D subtractToSelf(Vector2D v2) {
		this.set(this.getX() - v2.getX(), this.getY() - v2.getY());
		return this;
	}

	/**
	 * @param divisor
	 * @return A new Vector2D caculate by dividing this on a double
	 */
	public Vector2D divide(double divisor) {
		Vector2D ret = new Vector2D(this);
		ret.divideToSelf(divisor);
		return ret;
	}

	/**
	 * Divide this Vector2D on a double
	 * 
	 * @param divisor
	 * @return this
	 */
	public Vector2D divideToSelf(double divisor) {
		set(getX() / divisor, getY() / divisor);
		return this;
	}

	/**
	 * @param divisor
	 * @return A new Vector2D caculate by Multiplying with a double
	 */
	public Vector2D multiply(double scale) {
		Vector2D ret = new Vector2D(this);
		ret.multiplyToSelf(scale);
		return ret;
	}

	/**
	 * Multiply this Vector2D with a double
	 * 
	 * @param scale
	 * @return Vector2D
	 */
	public Vector2D multiplyToSelf(double scale) {
		set(getX() * scale, getY() * scale);
		return this;
	}

	/**
	 * @return The length of this Vector2D
	 */
	public double getLength() {
		double ret = Math.sqrt(getX() * getX() + getY() * getY());
		return ret;
	}

	public double getLengthSQ() {
		double ret = getX() * getX() + getY() * getY();
		return ret;
	}

	/**
	 * @return A new Vector2D with same direction and length 1, caculate by
	 *         normalizing this,
	 */
	public Vector2D normalize() {
		Vector2D ret = new Vector2D(this);
		ret.normalizeToSelf();
		return ret;
	}

	public Vector2D normalize(double length) {
		Vector2D ret = new Vector2D(this);
		ret.normalizeToSelf(length);
		return ret;
	}

	/**
	 * Normalize itself, with same direction and length 1
	 * 
	 * @return this
	 */
	public Vector2D normalizeToSelf() {
		double length = getLength();

		if (length == 0) {
			// avoid to divide on 0
			set(0, 0);
		} else {
			set(getX() / length, getY() / length);
		}

		return this;
	}

	public Vector2D normalizeToSelf(double length) {
		if (length == 0) {
			// avoid to divide on 0
			set(0, 0);
		} else {
			set(getX() / length, getY() / length);
		}

		return this;
	}

	/**
	 * @param v2
	 * @return The dot product of this with a Vector2D
	 */
	public double dotProduct(Vector2D v2) {
		double ret = this.getX() * v2.getX() + this.getY() * v2.getY();
		return ret;
	}

	/**
	 * 
	 * @param v2
	 * @return The distance between this and a Vector2D
	 */
	public double getDistance(Vector2D v2) {
		double xd = v2.getX() - this.getX();
		double yd = v2.getY() - this.getY();
		double ret = Math.sqrt(xd * xd + yd * yd);
		return ret;
	}

	public double getDistanceSQ(Vector2D v2) {
		double xd = v2.getX() - this.getX();
		double yd = v2.getY() - this.getY();
		double ret = xd * xd + yd * yd;
		return ret;
	}

	/**
	 * @return A double array with x coord(index 0) and y coord(index 1)
	 */
	public double[] getCoords() {
		return (new double[] { getX(), getY() });
	}

	/**
	 * return A new Vector2D same as this
	 */
	public Vector2D clone() {
		Vector2D ret = new Vector2D(getX(), getY());
		return ret;
	}

	/**
	 * 
	 * @return The radians
	 */
	public double getRadians() {
		double ret = Math.atan2(getY(), getX());
		return ret;
	}

	/**
	 * 
	 * @return The degrees
	 */
	public double getDegrees() {
		double ret = getRadians() / Math.PI * 180;
		return ret;
	}

	/**
	 * 
	 * @param radians
	 * @return A new Vector2D by rotating radians of this
	 */
	public Vector2D rotate(double radians) {
		Vector2D ret = new Vector2D(this);
		ret.rotateSelf(radians);
		return ret;
	}

	/**
	 * Rotating radians by itself
	 * 
	 * @param radians
	 * @return this
	 */
	public Vector2D rotateSelf(double radians) {
		double cosRadians = Math.cos(radians);
		double sinRadians = Math.sin(radians);
		set(getX() * cosRadians - getY() * sinRadians, getX() * sinRadians
				+ getY() * cosRadians);
		return this;
	}

	/**
	 * Set x
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Get x
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Set y
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Get y
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Set x and y
	 * 
	 * @param x
	 * @param y
	 */
	public void set(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * set this
	 * 
	 * @param v2
	 */
	public void set(Vector2D v2) {
		this.set(v2.getX(), v2.getY());
	}

	public static boolean doubleEquals(double left, double right) {
		boolean ret = false;
		ret = Double.doubleToLongBits(left) == Double.doubleToLongBits(right);
		return ret;
	}

	public static boolean doubleEquals(double left, double right, double epsilon) {
		boolean ret = Math.abs(left - right) < epsilon;
		return ret;
	}

	public static boolean doubleLess(double left, double right) {
		boolean ret = false;
		ret = Double.doubleToLongBits(left) < Double.doubleToLongBits(right);
		return ret;
	}

	public static boolean doubleGreater(double left, double right) {
		boolean ret = false;
		ret = Double.doubleToLongBits(left) > Double.doubleToLongBits(right);
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean ret = false;

		if (obj != null) {
			if (this.getClass() == obj.getClass()) {
				Vector2D v2 = (Vector2D) obj;

				if (doubleEquals(this.getX(), v2.getX(), EPSILON)) {
					if (doubleEquals(this.getY(), v2.getY(), EPSILON)) {
						ret = true;
					}
				}
			}
		}

		return ret;
	}

	/**
	 * Algorithm from Effective Java by Joshua Bloch [Jon Aquino]
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int ret = 17;
		ret = (int) (37 * ret + hashCode(x));
		ret = (int) (37 * ret + hashCode(y));
		return ret;
	}

	/**
	 * Algorithm from Effective Java by Joshua Bloch [Jon Aquino]
	 * 
	 * @param d
	 * @return A hashcode for the double value
	 */
	public static int hashCode(double d) {
		long longBits = Double.doubleToLongBits(d);
		return (int) (longBits ^ (longBits >>> 32));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf("(" + getX() + ", " + getY() + ")");
	}
}
