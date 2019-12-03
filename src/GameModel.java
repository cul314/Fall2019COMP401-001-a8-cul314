

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {

	private boolean[][] field;
	private int fieldWidth;
	private int fieldHeight;
	
	private int lowBirth;
	private int highBirth;
	private int lowSurvive;
	private int highSurvive;
	
	private List<GameObserver> observers;
	
	public static final int DEFAULT_FIELD_SIZE = 20;
	public static final int MIN_FIELD_SIZE = 10;
	public static final int MAX_FIELD_SIZE = 500;
	private static final int DEFAULT_LOW_BIRTH_THRESHOLD = 3;
	private static final int DEFAULT_HIGH_BIRTH_THRESHOLD = 3;
	private static final int DEFAULT_LOW_SURVIVE_THRESHOLD = 2;
	private static final int DEFAULT_HIGH_SURVIVE_THRESHOLD = 3;
	
	public GameModel() {
		
		field = new boolean[DEFAULT_FIELD_SIZE][DEFAULT_FIELD_SIZE];
		
		observers = new ArrayList<GameObserver>();
		
		lowBirth = DEFAULT_LOW_BIRTH_THRESHOLD;
		highBirth = DEFAULT_HIGH_BIRTH_THRESHOLD;
		lowSurvive = DEFAULT_LOW_SURVIVE_THRESHOLD;
		highSurvive = DEFAULT_HIGH_SURVIVE_THRESHOLD;
		
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				field[i][j] = false;
			}
		}
	}
	
	public void setRandom() {
		Random rand = new Random();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				field[i][j] = rand.nextBoolean();
			}
		}
		notifyObservers();
	}
	
	public void progress() {
		boolean[][] fieldPostImg = cloneField();
		
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				int numNeighbors = getNumNeighbors(i, j);
				if (field[i][j]) {
					if (numNeighbors < lowSurvive || numNeighbors > highSurvive) {
						fieldPostImg[i][j] = false;
					}
				} else {
					if (numNeighbors >= lowBirth && numNeighbors <= highBirth) {
						fieldPostImg[i][j] = true;
					}
				}
			}
		}
		
		field = fieldPostImg;
		notifyObservers();
	}
	
	private int getNumNeighbors(int x, int y) {
		int count = 0;
		
		try {
			if (field[x+1][y]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x+1][y+1]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x][y+1]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x-1][y+1]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x-1][y]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x-1][y-1]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x][y-1]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		try {
			if (field[x+1][y-1]) {
				count++;
			}
		} catch(IndexOutOfBoundsException e) {}
		
		return count;
	}
	
	private boolean[][] cloneField(){
		boolean[][] clone = new boolean[field.length][];
		for (int i = 0; i < field.length; i++) {
			clone[i] = field[i].clone();
		}
		return clone;
	}
	
	public boolean[][] getField() {
		return cloneField();
	}
	
	public void setField(boolean[][] field) {
		// TODO Add validation
		
		this.field = field;
	}
	
	public void clearAll() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				field[i][j] = false;
			}
		}
		notifyObservers();
	}
	
	public void resizeField(int width, int height) {
		field = new boolean[width][height];
		clearAll();
		notifyObservers();
	}
	
	public boolean getCellAt(int x, int y) {
		// TODO Throw illegal arg exception
		
		return field[x][y];
	}
	
	public void setCellAt(int x, int y, boolean value) {
		field[x][y] = value;
		notifyObservers();
	}
	
	public void addObserver(GameObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(GameObserver o) {
		observers.remove(o);
	}
	
	private void notifyObservers() {
		for (GameObserver o : observers) {
			o.update(field.clone());
		}
	}
}
