package fractals.gui.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

import fractals.gui.MainWindow;
import fractals.logic.MandelbrotLogic;
import fractals.logic.math.Utils;

public class MandelbrotPanel extends FractalPanel implements MouseListener, ComponentListener, MouseMotionListener {

	private final Logger log = Logger.getLogger(getClass().getSimpleName());

	private static final long serialVersionUID = 1937935231551028142L;

	private final MandelbrotLogic logic;

	private boolean isMoving = false;

	private Point movingStart;

	private Point temporaryMovingEnd;

	private final Color rectangleColor = Color.WHITE;

	private Image cachedImage;

	public MandelbrotPanel(int height, int width, MainWindow parent) {
		super(height, width, parent);
		logic = new MandelbrotLogic(getHeight(), getWidth());
		addMouseListener(this);
		addComponentListener(this);
		addMouseMotionListener(this);
	}

	private void drawRectangle(Graphics2D g) {
		if (temporaryMovingEnd != null) {
			g.setColor(rectangleColor);
			g.drawRect(movingStart.x, movingStart.y, temporaryMovingEnd.x - movingStart.x,
					temporaryMovingEnd.y - movingStart.y);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(cachedImage, 0, 0, null);
		drawRectangle(g2d);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		log.info("Click at: " + e.getPoint());
		parent.infoJuliaAboutClickAt(logic.complexCoordinatesOf(e.getPoint()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		isMoving = false;
		temporaryMovingEnd = null;
		repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		isMoving = true;
		movingStart = e.getPoint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		final Point newPoint = e.getPoint();
		if (isMoving && Utils.dist2(movingStart, newPoint) > 4) {
			temporaryMovingEnd = null;
			isMoving = false;
			logic.setNewBounds(movingStart, newPoint);
			log.info("New bounds: " + movingStart + " " + newPoint);
			cachedImage = logic.generateImage();
			repaint();
		}

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		logic.setNewWindowSizes(getWidth(), getHeight());
		cachedImage = logic.generateImage();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isMoving) {
			temporaryMovingEnd = e.getPoint();
			repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
