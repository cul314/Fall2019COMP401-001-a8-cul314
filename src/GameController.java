
public class GameController implements GameObserver, GameViewListener {

	private GameModel model;
	private GameView view;
	
	public GameController (GameModel model, GameView view) {
		
		this.model = model;
		this.view = view;
		
		model.addObserver(this);
		view.addGameViewListener(this);
		
		update(model.getField());
	}
	
	public void update(boolean[][] field) {
		// TODO Validate
		
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				if (field[i][j]) {
					view.setAliveCell(i, j);
				} else {
					view.setDeadCell(i, j);
				}
			}
		}
	}

	@Override
	public void handleGameViewEvent(GameViewEvent e) {
		if (e.isSpotClickedEvent()) {
			SpotClickedEvent spot = (SpotClickedEvent) e;
			int spotX = spot.getSpot().getSpotX();
			int spotY = spot.getSpot().getSpotY();
			
			if (model.getCellAt(spotX, spotY)) {
				model.setCellAt(spotX, spotY, false);
			} else {
				model.setCellAt(spotX, spotY, true);
			}
		} else if (e.isProgressEvent()) {
			model.progress();
		} else if (e.isClearAllEvent()) {
			model.clearAll();
		} else if (e.isSetRandomEvent()) {
			model.setRandom();
		} else if (e.isResizeEvent()) {
			ResizeEvent resize = (ResizeEvent) e;
			model.resizeField(resize.getResize(), resize.getResize());
		}
		
	}
}
