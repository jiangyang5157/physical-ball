package com.gmail.jiangyang5157.entry;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.gmail.jiangyang5157.canvas.Ball;
import com.gmail.jiangyang5157.canvas.Line;
import com.gmail.jiangyang5157.canvas.Physics;

/**
 * Entry Frame
 * 
 * @author JiangYang
 * 
 */
public class EntryFrame extends JFrame {

	private static final long serialVersionUID = 2305843001233693951L;

	private CanvasPanel cavansPanel = null;

	private JButton btnGenerate = null;
	private JButton btnScatter = null;
	private JButton btnReset = null;
	public JSlider radiusSlider = null;
	public JSlider densitySlider = null;
	public JSlider gravitySlider = null;
	public JSlider frictionSlider = null;

	public JComboBox<String> cbEdge = null;

	public void create(String title) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension displayDimension = Toolkit.getDefaultToolkit()
				.getScreenSize();
		Dimension frameDimension = new Dimension(1000, 600);
		setPreferredSize(frameDimension);
		this.setLocation((displayDimension.width - frameDimension.width) / 2,
				(displayDimension.height - frameDimension.height) / 2);
		this.setTitle(title);
		createComponents();

		pack();
		setVisible(true);

		initialization();
	}

	private void createComponents() {
		// TODO Auto-generated method stub
		JPanel contentPanel = new JPanel(new BorderLayout());
		getContentPane().add(contentPanel);

		/*
		 * cavans panel
		 */
		cavansPanel = new CanvasPanel(this);
		cavansPanel.addMouseListener(cavansPanelMouseListener);
		cavansPanel.addMouseMotionListener(cavansPanelMouseMotionListener);
		contentPanel.add(cavansPanel, BorderLayout.CENTER);

		/*
		 * control panel
		 */
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		contentPanel.add(controlPanel, BorderLayout.EAST);

		radiusSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 16);
		radiusSlider.setBorder(BorderFactory
				.createTitledBorder("Radius of Next Balls = "
						+ radiusSlider.getValue()));
		radiusSlider.addChangeListener(radiusSliderChangeListener);

		densitySlider = new JSlider(JSlider.HORIZONTAL, 1, 10,
				(int) Ball.DEFAULT_DENSITY);
		densitySlider.setBorder(BorderFactory
				.createTitledBorder("Density of Next Balls = "
						+ densitySlider.getValue()));
		densitySlider.addChangeListener(densitySliderChangeListener);

		gravitySlider = new JSlider(JSlider.HORIZONTAL, 0, 10000,
				(int) Physics.gravity);
		gravitySlider.setBorder(BorderFactory.createTitledBorder("Gravity = "
				+ gravitySlider.getValue()));
		gravitySlider.addChangeListener(gravitySliderChangeListener);

		frictionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100,
				(int) (100 * Physics.frictionFactor));
		frictionSlider.setBorder(BorderFactory.createTitledBorder("Friction = "
				+ frictionSlider.getValue() + "%"));
		frictionSlider.addChangeListener(frictionSliderChangeListener);

		btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(btnGenerateActionListener);

		btnScatter = new JButton("Scatter");
		btnScatter.addActionListener(btnScatterActionListener);

		btnReset = new JButton("Reset");
		btnReset.addActionListener(btnResetActionListener);

		cbEdge = new JComboBox<String>();
		cbEdge.addItem("Visible");
		cbEdge.addItem("Cycle");
		cbEdge.addItem("Gone");
		cbEdge.setBorder(BorderFactory.createTitledBorder("Boundary Type"));
		cbEdge.addItemListener(cbEdgeItemListener);
		cbEdge.setMaximumSize(new Dimension(150, 60));

		radiusSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		densitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		gravitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		frictionSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		cbEdge.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnGenerate.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnScatter.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnReset.setAlignmentX(Component.CENTER_ALIGNMENT);
		controlPanel.add(radiusSlider);
		controlPanel.add(densitySlider);
		controlPanel.add(gravitySlider);
		controlPanel.add(frictionSlider);
		controlPanel.add(cbEdge);
		controlPanel.add(btnGenerate);
		controlPanel.add(btnScatter);
		controlPanel.add(btnReset);
	}

	private void initialization() {
		// TODO Auto-generated method stub
	}

	private ActionListener btnGenerateActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cavansPanel.generateBalls();
		}
	};

	private ActionListener btnScatterActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cavansPanel.scatterBalls();
		}
	};

	private ActionListener btnResetActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cavansPanel.balls.clear();
		}
	};

	private ChangeListener radiusSliderChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider) e.getSource();
			source.setBorder(BorderFactory
					.createTitledBorder("Radius of Next Balls = "
							+ source.getValue()));
		}
	};

	private ChangeListener densitySliderChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider) e.getSource();
			source.setBorder(BorderFactory
					.createTitledBorder("Density of Next Balls = "
							+ source.getValue()));
		}
	};

	private ChangeListener gravitySliderChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider) e.getSource();
			source.setBorder(BorderFactory.createTitledBorder("Gravity = "
					+ source.getValue()));

			Physics.gravity = source.getValue();
		}
	};

	private ChangeListener frictionSliderChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider source = (JSlider) e.getSource();
			source.setBorder(BorderFactory.createTitledBorder("Friction = "
					+ source.getValue() + "%"));

			Physics.frictionFactor = (double) source.getValue() / 100;
		}
	};

	private ItemListener cbEdgeItemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if (e.getStateChange() == ItemEvent.SELECTED) {
				String type = (String) cbEdge.getSelectedItem();
				if (type.equals("Visible")) {
					cavansPanel.boundary = CanvasPanel.BOUNDARY_VISIBLE;
				} else if (type.equals("Cycle")) {
					cavansPanel.boundary = CanvasPanel.BOUNDARY_CYCLE;
				} else {
					cavansPanel.boundary = CanvasPanel.BOUNDARY_GONE;
				}
			}
		}
	};

	private MouseListener cavansPanelMouseListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			cavansPanel.rocketBall = new Ball(e.getX(), e.getY(),
					radiusSlider.getValue());

			cavansPanel.line = new Line(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (cavansPanel.line != null) {
				/*
				 * Add a new ball
				 */
				double xVector = (cavansPanel.line.target.getX() - cavansPanel.line.position
						.getX()) * 10;
				double yVector = (cavansPanel.line.target.getY() - cavansPanel.line.position
						.getY()) * 10;
				cavansPanel.rocketBall.velocity.set(xVector, yVector);
				cavansPanel.rocketBall.setDensity(densitySlider.getValue());

				cavansPanel.balls.add(cavansPanel.rocketBall);
				cavansPanel.rocketBall = null;
				cavansPanel.line = null;
			}
		}
	};

	private MouseMotionListener cavansPanelMouseMotionListener = new MouseMotionListener() {
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if (cavansPanel.line != null) {
				/*
				 * draw line
				 */
				int x1 = (int) cavansPanel.line.position.getX();
				int y1 = (int) cavansPanel.line.position.getY();
				int x2 = e.getX();
				int y2 = e.getY();
				int xDistance = Math.abs(x2 - x1);
				int yDistance = Math.abs(y2 - y1);

				if ((x2 - x1) > 0) {
					cavansPanel.line.target.setX(x1 - xDistance);
				} else {
					cavansPanel.line.target.setX(x1 + xDistance);
				}

				if ((y2 - y1) > 0) {
					cavansPanel.line.target.setY(y1 - yDistance);
				} else {
					cavansPanel.line.target.setY(y1 + yDistance);
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	};
}
