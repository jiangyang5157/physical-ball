package com.gmail.jiangyang5157.entry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gmail.jiangyang5157.canvas.Ball;
import com.gmail.jiangyang5157.canvas.Line;
import com.gmail.jiangyang5157.canvas.Physics;
import com.gmail.jiangyang5157.canvas.Vector2D;

/**
 * Canvas panel
 * 
 * @author JiangYang
 * 
 */
public class CanvasPanel extends JPanel implements Runnable, WindowListener,
		KeyListener {

	private static final long serialVersionUID = 2305843001233622251L;

	/**
	 * Balls list
	 */
	public ArrayList<Ball> balls = null;

	/**
	 * The new ball by mouse pressed
	 */
	public Ball rocketBall = null;

	/**
	 * The line for when mouse pressed
	 */
	public Line line = null;

	/**
	 * Boundary type flag - Gone
	 */
	public static final int BOUNDARY_GONE = 0;

	/**
	 * Boundary type flag - Existent
	 */
	public static final int BOUNDARY_VISIBLE = 1;

	/**
	 * Boundary type flag - Cycle
	 */
	public static final int BOUNDARY_CYCLE = 2;

	/**
	 * Current boundary type
	 */
	public int boundary = BOUNDARY_VISIBLE;

	/**
	 * The actual render thread
	 */
	private Thread myThread = null;

	/**
	 * Thread running flag
	 */
	private boolean isRunning = false;

	/**
	 * Thread paused flag
	 */
	private boolean isPaused = false;

	/**
	 * Default frames per second
	 */
	public static final int FPS_DEFAULT = 70;

	/**
	 * FPS
	 */
	private int fps = 0;

	/**
	 * Real-time FPS
	 */
	private int realTimeFPS = FPS_DEFAULT;

	/**
	 * Period(ms) during each frame
	 */
	private long framePeriod = 0;

	/**
	 * The moment(ms) of next frame
	 */
	private long nextFrame = 0;

	/**
	 * The moment(ms) of previous frame
	 */
	private long lastTimer = 0;

	/**
	 * Frames counter
	 */
	private int framesCounter = 0;

	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public CanvasPanel(JFrame frame) {
		super();
		// TODO Auto-generated constructor stub
		frame.addWindowListener(this);
		this.setFocusable(true);
		this.addKeyListener(this);

		initialization();
	}

	/**
	 * initialization, including balls, thread, etc.
	 */
	private void initialization() {
		// TODO Auto-generated method stub
		if (balls == null) {
			balls = new ArrayList<Ball>();
		} else {
			balls.clear();
		}

		setFps(FPS_DEFAULT);
		onStart();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRunning) {
			while (isPaused) {
				synchronized (this) {
					try {
						wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			/*
			 * frame calculate
			 */
			long now = System.currentTimeMillis();
			if (now < nextFrame) {
				continue;
			} else {
				nextFrame = now + framePeriod;
			}
			long timer = now - lastTimer;
			if (timer > 1000) {
				realTimeFPS = (int) ((framesCounter * 1000) / timer);
				// System.out.println("RenderThread - real-time FPS = "
				// + realTimeFPS);
				lastTimer = now;
				framesCounter = 0;
			}
			framesCounter++;

			onRender();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		// Turn on anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// background
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());

		/*
		 * render components
		 */
		for (int i = 0; i < balls.size(); i++) {
			Ball b = balls.get(i);
			b.render(g2);
		}

		if (rocketBall != null) {
			rocketBall.render(g2);
		}

		if (line != null) {
			line.render(g2);
		}
		// foreground
		// Turn on text anti-aliasing
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.setColor(Color.WHITE);
		g2.drawString("FPS = " + getRealTimeFPS(), 20, 20);
		g2.drawString("Balls = " + balls.size(), 20, 40);
	}

	public synchronized void onRender() {
		// TODO Auto-generated method stub
		// check collision
		for (int i = 0; i < balls.size(); i++) {
			// update balls
			Ball ball = balls.get(i);
			if (ball == null) {
				break;
			}
			ball.update(getRealTimeFPS());
		}

		checkCollision(getRealTimeFPS());

		// invoke paintComponent()
		repaint();
	}

	/**
	 * Scatter balls by random gravity
	 */
	public void scatterBalls() {
		Random rand = new Random();
		for (int i = 0; i < balls.size(); i++) {
			int flag = rand.nextInt(2) == 0 ? -1 : 1;
			balls.get(i).velocity.set(Physics.gravity * rand.nextDouble()
					* flag, (Physics.gravity * rand.nextDouble()) * flag);
		}
	}

	public void generateBalls() {
		int w = getWidth();
		if (w > 0) {
			final float r = 10;
			final float d = r * 2;
			int num = (int) (w / d);

			for (int i = 0; i < num; i++) {
				Ball ball = new Ball(i * d, 0, r);
				balls.add(ball);
			}
		}
	}

	/**
	 * Sort balls by their x coord
	 */
	public void sortBalls(ArrayList<Ball> balls) {
		for (int i = 1; i < balls.size(); i++) {
			for (int j = i; j > 0 && j < balls.size(); j--) {
				Ball b1 = balls.get(j);
				Ball b2 = balls.get(j - 1);
				if (b1 != null && b2 != null) {
					if (b1.compareTo(b2) < 0) {
						Collections.swap(balls, j, j - 1);
					}
				}
			}
		}
	}

	/**
	 * Check collision, including walls, balls, etc.
	 */
	public void checkCollision(int fps) {
		// sort first, for optimize part of arithmetic
		sortBalls(balls);

		for (int i = 0; i < balls.size(); i++) {
			Ball b1 = balls.get(i);
			if (b1 == null) {
				break;
			}
			/*
			 * Check collision for balls and walls, based on boundary type
			 */
			switch (boundary) {
			case BOUNDARY_GONE:
				// do nothing
				break;
			case BOUNDARY_VISIBLE:
				if (Vector2D.doubleLess(b1.position.getX() - b1.getRadius(), 0)) {
					// Colliding with left, place the ball against edge
					b1.position.setX(b1.getRadius());
					// calculate the friction factor
					b1.velocity.setX(-b1.velocity.getX()
							* Physics.frictionFactor);
					b1.velocity.setY(b1.velocity.getY()
							* Physics.frictionFactor);
				} else if (Vector2D.doubleGreater(
						b1.position.getX() + b1.getRadius(), this.getWidth())) {
					// Colliding with right, place the ball against edge
					b1.position.setX(getWidth() - b1.getRadius());
					// calculate the friction factor
					b1.velocity.setX(-b1.velocity.getX()
							* Physics.frictionFactor);
					b1.velocity.setY(b1.velocity.getY()
							* Physics.frictionFactor);
				}

				if (Vector2D.doubleLess(b1.position.getY() - b1.getRadius(), 0)) {
					// Colliding with top, place the ball against edge
					b1.position.setY(b1.getRadius());
					// calculate the friction factor
					b1.velocity.setX(b1.velocity.getX()
							* Physics.frictionFactor);
					b1.velocity.setY(-b1.velocity.getY()
							* Physics.frictionFactor);
				} else if (Vector2D.doubleGreater(
						b1.position.getY() + b1.getRadius(), this.getHeight())) {
					// Colliding with bottom, place the ball against edge
					b1.position.setY(getHeight() - b1.getRadius());
					// calculate the friction factor
					b1.velocity.setX(b1.velocity.getX()
							* Physics.frictionFactor);
					// b1.velocity.setY(-b1.velocity.getY() *
					// Physics.frictionFactor);

					// on the floor
					double elapsedSeconds = 1.0 / fps;
					if (b1.velocity.getY() > Physics.gravity * elapsedSeconds) {
						b1.velocity.setY(-b1.velocity.getY()
								* Physics.frictionFactor);
					} else {
						b1.velocity.setY(0);
					}
				}
				break;
			case BOUNDARY_CYCLE:
				if (Vector2D.doubleLess(b1.position.getX() + b1.getRadius(), 0)) {
					// get out of left, come in from right
					b1.position.setX(this.getWidth() + b1.getRadius());
				}

				if (Vector2D.doubleGreater(b1.position.getX() - b1.getRadius(),
						this.getWidth())) {
					// get out of right, come in from left
					b1.position.setX(-b1.getRadius());
				}

				if (Vector2D.doubleLess(b1.position.getY() + b1.getRadius(), 0)) {
					// get out of top, come in from bottom
					b1.position.setY(this.getHeight() + b1.getRadius());
				}

				if (Vector2D.doubleGreater(b1.position.getY() - b1.getRadius(),
						this.getHeight())) {
					// get out of bottom, come in from top
					b1.position.setY(-b1.getRadius());
				}
				break;
			default:
				break;
			}

			/*
			 * Check collision among balls
			 */
			for (int j = i + 1; j < balls.size(); j++) {
				Ball b2 = balls.get(j);
				if (b2 == null) {
					break;
				}
				if (Vector2D.doubleLess(b1.position.getX() + b1.getRadius(),
						b2.position.getX() - b2.getRadius())) {
					// skip the balls > (on the right side) of current ball,
					// bacause they are sorted already
					// only check the balls on the left of curret ball, rest of
					// them are further away in the x axis
					break;
				}

				if (Vector2D.doubleLess(b1.position.getY() + b1.getRadius(),
						b2.position.getY() - b2.getRadius())
						|| Vector2D.doubleGreater(
								b1.position.getY() - b1.getRadius(),
								b2.position.getY() + b2.getRadius())) {
					// skip the balls they are impassable to collosing with
					// current ball
					continue;
				}

				// only check the balls on the left of curret ball, also, it is
				// possable to collosing with current ball
				b1.checkCollision(b2, fps);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			// space: pause/resume(start) render thread
			if (isRunning()) {
				if (isPaused()) {
					onResume();
				} else {
					onPause();
				}
			} else {
				onStart();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Stop the render thread when window closing
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		onStop();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Start thread
	 */
	public synchronized void onStart() {
		if (!isRunning) {
			synchronized (this) {
				// System.out.println("RenderThread - onStart");
				if (myThread == null
						|| myThread.getState() == Thread.State.TERMINATED) {
					myThread = new Thread(this);
				}

				isRunning = true;
				myThread.start();
			}
		}
	}

	/**
	 * Pause thread
	 */
	public synchronized void onPause() {
		if (isRunning && !isPaused) {
			synchronized (this) {
				// System.out.println("RenderThread - onPause");
				isPaused = true;
			}
		}
	}

	/**
	 * Resume thread
	 */
	public void onResume() {
		if (isRunning && isPaused) {
			synchronized (this) {
				// System.out.println("RenderThread - onResume");
				isPaused = false;
				notify();
			}
		}
	}

	/**
	 * Stop thread
	 */
	public void onStop() {
		if (isRunning) {
			if (isPaused) {
				onResume();
			}
			synchronized (this) {
				// System.out.println("RenderThread - onStop");
				isRunning = false;
			}

			boolean retry = true;
			while (retry) {
				try {
					myThread.join();
					retry = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get real-time fps
	 * 
	 * @return
	 */
	public int getRealTimeFPS() {
		return realTimeFPS;
	}

	/**
	 * Set FPS
	 * 
	 * @param fps
	 */
	public void setFps(int fps) {
		this.fps = fps;
		framePeriod = 1000 / this.fps;
	}

	/**
	 * Get FPS
	 * 
	 * @return
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * Get thread running flag
	 * 
	 * @return true: is running; false: is not running
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Get thread paused flag
	 * 
	 * @return true: is paused; false: is not paused
	 */
	public boolean isPaused() {
		return isPaused;
	}
}
