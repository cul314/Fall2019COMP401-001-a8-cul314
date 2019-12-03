

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameView extends JPanel implements ActionListener, SpotListener, ChangeListener {
	
	private JFrame mainFrame;

	private JSpotBoard field;
	private JPanel controlPanel;
	private JLabel sliderLabel;
	private List<GameViewListener> listeners;
	
	private static final Color DEAD_CELL_COLOR = new Color(1f, 1f, 1f);
	private static final Color ALIVE_CELL_COLOR = new Color(0f, 0f, 0f);
	
	public GameView(JFrame frame) {
		mainFrame = frame;
		
		setLayout(new BorderLayout());
		
		field = new JSpotBoard(GameModel.DEFAULT_FIELD_SIZE,GameModel.DEFAULT_FIELD_SIZE);
		field.addSpotListener(this);
		add(field, BorderLayout.CENTER);
		listeners = new ArrayList<GameViewListener>();
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.SOUTH);
		
		JPanel buttonPanel = new JPanel();
		controlPanel.add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setLayout(new GridLayout(1,3));
		
		JButton progressButton = new JButton("Next");
		buttonPanel.add(progressButton, BorderLayout.SOUTH);
		progressButton.addActionListener(this);
		
		JButton clearAllButton = new JButton("Clear");
		buttonPanel.add(clearAllButton);
		clearAllButton.addActionListener(this);
		
		JButton setRandomButton = new JButton("Random");
		buttonPanel.add(setRandomButton);
		setRandomButton.addActionListener(this);
		
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BorderLayout());
		controlPanel.add(sliderPanel, BorderLayout.CENTER);
		
		JSlider slider = new JSlider(GameModel.MIN_FIELD_SIZE, GameModel.MAX_FIELD_SIZE, GameModel.DEFAULT_FIELD_SIZE);
		sliderPanel.add(slider, BorderLayout.NORTH);
		slider.setPaintTrack(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(50);
		slider.addChangeListener(this);
		
		JLabel filler = new JLabel(" ");
		sliderPanel.add(filler, BorderLayout.SOUTH);
		
		sliderLabel = new JLabel("     The length/width of the field is: " + slider.getValue());
		sliderPanel.add(sliderLabel, BorderLayout.CENTER);
		//sliderLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		this.setFocusable(true);
		this.grabFocus();
	}

	public void setAliveCell(int x, int y) {
		// TODO Validate
		
		field.getSpotAt(x, y).setBackground(ALIVE_CELL_COLOR);
	}
	
	public void setDeadCell(int x, int y) {
		// TODO Validate
		
		field.getSpotAt(x, y).setBackground(DEAD_CELL_COLOR);
	}
	
	public void addGameViewListener(GameViewListener l) {
		listeners.add(l);
	}
	
	public void removeGameViewListener(GameViewListener l) {
		// TODO Add parameter validation
		
		listeners.remove(l);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		char firstChar = button.getText().charAt(0);
		
		if (firstChar == 'N') {
			fireEvent(new ProgressEvent());
		} else if (firstChar == 'C') {
			fireEvent(new ClearAllEvent());
		} else if (firstChar == 'R') {
			fireEvent(new SetRandomEvent());
		}
	}
	
	public void fireEvent(GameViewEvent e) {
		for (GameViewListener l : listeners) {
			l.handleGameViewEvent(e);
		}
	}

	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		fireEvent(new SpotClickedEvent(spot));
	}

	@Override
	public void spotEntered(Spot spot) {
		spot.highlightSpot();
	}

	@Override
	public void spotExited(Spot spot) {
		spot.unhighlightSpot();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider s = (JSlider) e.getSource();
		
		if (s.getValueIsAdjusting()) {
			return;
		}
		
		fireEvent(new ClearAllEvent());
		remove(field);
		
		field = new JSpotBoard(s.getValue(),s.getValue());
		field.addSpotListener(this);
		add(field, BorderLayout.CENTER);
		
		sliderLabel.setText("The length/width of the field is: " + s.getValue());
		
		fireEvent(new ResizeEvent(s.getValue()));
		
		this.setFocusable(true);
		this.grabFocus();
		
		mainFrame.resize(getPreferredSize());;
	}

}
