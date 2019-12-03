
abstract public class GameViewEvent {

	public boolean isSpotClickedEvent() {
		return false;
	}
	
	public boolean isProgressEvent() {
		return false;
	}
	
	public boolean isClearAllEvent() {
		return false;
	}
	
	public boolean isSetRandomEvent() {
		return false;
	}
	
	public boolean isResizeEvent() {
		return false;
	}
}

class SpotClickedEvent extends GameViewEvent {
	
	private Spot spot;
	
	public SpotClickedEvent(Spot spot) {
		this.spot = spot;
	}
	
	public Spot getSpot() {
		return spot;
	}
	
	@Override
	public boolean isSpotClickedEvent() {
		return true;
	}
}

class ProgressEvent extends GameViewEvent {
	@Override
	public boolean isProgressEvent() {
		return true;
	}
}

class ClearAllEvent extends GameViewEvent {
	@Override
	public boolean isClearAllEvent() {
		return true;
	}
}

class SetRandomEvent extends GameViewEvent {
	@Override
	public boolean isSetRandomEvent() {
		return true;
	}
}

class ResizeEvent extends GameViewEvent {
	
	private int size;
	
	public ResizeEvent(int value) {
		size = value;
	}
	
	public int getResize() {
		return size;
	}
	
	@Override
	public boolean isResizeEvent() {
		return true;
	}
}