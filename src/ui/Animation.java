package ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Animation extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private final int frameEdgeX = 1000;
	private final int frameEdgeY = 1000;

	// deleted code for player count: to be integrated
	// deleted code for get dice value: to use to expand the case tree for movement
	// accordingly

	// arbitrary values, to be changed. boundaries numbered 1 are the minimum
	// (left-most and uppermost) coordinate
	// values, whereas 2's are the maximums (right-most and down-most). "Outer" is
	// the outermost ring/track of the
	// board. "Middle" is the middle, "Inner" is the innermost ring/track of the
	// board.
	// So naturally each track has different boundaries.
	public int boundaryOuter1 = 0, boundaryOuter2 = 720, boundaryMiddle1 = 120, boundaryMiddle2 = 600,
			boundaryInner1 = 240, boundaryInner2 = 480;

	// arbitrary values. squareEdges define the size of the boxes in the board. For
	// this code, all are uniform, but
	// for the demo they have to be added into for different sizes of squares on the
	// board. Or call each square's size from
	// itself? In that case, these variables are for the current square the pin is
	// on, to be changed with the movement.
	// boundaries have the aforementioned logic, however these signify the
	// boundaries of the current track the pin
	// is on.
	public int squareEdgeX = 30, squareEdgeY = 30, boundary1 = boundaryMiddle1, boundary2 = boundaryMiddle2;

	// deleted currently unused velocity variables. can be re-added when
	// implementing smooth animation.
	// initial values for x and y coordinates are the coordinates of the "GO" square
	// (bottom-right corner of the second track.)
	// unnecessary if the fields of the square are called from the square itself.
	public int x = boundaryMiddle2, y = boundaryMiddle2;

	public Animation(){
		
		Animation ani = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Testing Movement");
		setSize(frameEdgeX, frameEdgeY);
		setVisible(true);

		// this button is to help coding the basic movement. it moves the pin one (1)
		// unit when clicked on it.
		// in the demo and final versions of the game, this will be removed, and the
		// movement will be instead
		// demanded by dice value.
		JButton button = new JButton("Click To Step 1");
		add(button, BorderLayout.SOUTH);

		// also to practice basic movement, this code pops a menu to help the coder
		// dictate movement toward
		// one of the other tracks depending on their choice. in the demo and final
		// versions of the game, this
		// will be removed, and which track the pin continues on will be decided based
		// on the dice value.
		Object[] options = { "Inner", "Outer" }; // button names for the popup menu

		PopupMenu popup = new PopupMenu();
		MenuItem inner = new MenuItem("Inner");
		popup.add(inner);
		MenuItem outer = new MenuItem("Outer");
		popup.add(outer);

		// the pin
		JComponent ball = new JComponent() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.setColor(Color.RED);
				g.fillOval(x, y, 50, 50);
				// arbitrary color and size values
				// best to modify size and color values based on player count
				// need to get player count and write pin resizing algorithms so the pins won't
				// collide
			}
		};
		add(ball);
		
		// this whole section has one duty, and is basically the main thing I have done:
		// in the middle of
		// the side the pin is moving on, aka where transit point is, the GUI reacts
		// with "which other track
		// do you want to go?" the popup menu is used for this purpose. in a working
		// demo, the popup is cancelled
		// and the same movement code I wrote can be copied into conditions of dice
		// values.		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button) {
					// assume start at go - hence, middle track
					if (x <= boundary2 && x > boundary1 && y == boundary2) {
						if (x == (boundary1 + boundary2) / 2) {
							int n = showDialog();
							if (n == JOptionPane.YES_OPTION) {
								if (currentlyInWhichTrack() == 3) {
									leap(x, y + squareEdgeY, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x, y - squareEdgeY * (3 - currentlyInWhichTrack()), boundaryInner1,
											boundaryInner2);
								} // this is where I use return integer trick
							} else {
								if (currentlyInWhichTrack() == 1) {
									leap(x, y - squareEdgeY, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x, y + squareEdgeY * Math.abs(1 - currentlyInWhichTrack()), boundaryOuter1,
											boundaryOuter2);
								}
							}
						} else { // if not on transit, just carry on
							x -= squareEdgeX;
							y = boundary2;
						}
					} else if (x == boundary1 && y <= boundary2 && y > boundary1) {
						if (y == (boundary1 + boundary2) / 2) {
							int n = showDialog();
							if (n == JOptionPane.YES_OPTION) {
								if (currentlyInWhichTrack() == 3) {
									leap(x - squareEdgeX, y, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x + squareEdgeX * (3 - currentlyInWhichTrack()), y, boundaryInner1,
											boundaryInner2);
								}
							} else {
								if (currentlyInWhichTrack() == 1) {
									leap(x + squareEdgeX, y, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x - squareEdgeX * Math.abs(1 - currentlyInWhichTrack()), y, boundaryOuter1,
											boundaryOuter2);
								}
							}
						} else {
							x = boundary1;
							y -= squareEdgeY;
						}
					} else if (x >= boundary1 && x < boundary2 && y == boundary1) {
						if (x == (boundary1 + boundary2) / 2) {
							int n = showDialog();
							if (n == JOptionPane.YES_OPTION) {
								if (currentlyInWhichTrack() == 3) {
									leap(x, y - squareEdgeY, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x, y + squareEdgeY * (3 - currentlyInWhichTrack()), boundaryInner1,
											boundaryInner2);
								}
							} else {
								if (currentlyInWhichTrack() == 1) {
									leap(x, y + squareEdgeY, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x, y - squareEdgeY * Math.abs(1 - currentlyInWhichTrack()), boundaryOuter1,
											boundaryOuter2);
								}
							}
						} else {
							x += squareEdgeX;
							y = boundary1;
						}
					} else if (x == boundary2 && y >= boundary1 && y < boundary2) {
						if (y == (boundary1 + boundary2) / 2) {
							int n = showDialog();
							if (n == JOptionPane.YES_OPTION) {
								if (currentlyInWhichTrack() == 3) {
									leap(x + squareEdgeX, y, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x - squareEdgeX * (3 - currentlyInWhichTrack()), y, boundaryInner1,
											boundaryInner2);
								}
							} else {
								if (currentlyInWhichTrack() == 1) {
									leap(x - squareEdgeX, y, boundaryMiddle1, boundaryMiddle2);
								} else {
									leap(x + squareEdgeX * Math.abs(1 - currentlyInWhichTrack()), y, boundaryOuter1,
											boundaryOuter2);
								}
							}
						} else {
							x = boundary2;
							y += squareEdgeY;
						}
					}

					ball.repaint(); // repaint
				}
				;
			}

			private int showDialog() {
				return JOptionPane.showOptionDialog(ani,"Choose the new Track", "Track", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
						options, // the titles of buttons
						options[0]); // default button title
			}

		});
		
	}

	// leap function to be primarily used in transit. changes coordinates of the pin
	// simultaneously, as well as updating
	// boundary values to use next step.
	// [BUG WARNING] There's a good chance the bug comes from this function: perhaps
	// it doesn't update the parameters
	// correctly? The bug is that the pin cannot move anymore after leaping.
	// Unresolved.
	public void leap(int newX, int newY, int newBoundary1, int newBoundary2) {
		this.x = newX;
		this.y = newY;
		this.boundary1 = newBoundary1;
		this.boundary2 = newBoundary2;
	}

	// function to signify which track the pin is currently on. primarily to be used
	// to understand where exactly the pin
	// should turn to. 1 is the outermost track, 2 is the middle track, 3 is the
	// innermost track.
	// WHY RETURN INTEGERS INSTEAD OF ANYTHING ELSE? It's a trick I came up with to
	// simplify the movement code. The
	// numbers are 1-3, and when changing tracks, the pin has to move either 1 or 2
	// boxes to do so. Hence, using either
	// 3-#t or abs(3-#t), where #t is track number, I can determine what to multiply
	// the distance with to travel the
	// correct amount without adding more if-cases.
	public int currentlyInWhichTrack() {
		if (boundary1 == boundaryOuter1)
			return 1;
		if (boundary1 == boundaryMiddle1)
			return 2;
		return 3;
	}

	// obligatory main function
	public static void main(String[] args){
		new Animation().setVisible(true);
	}

}
