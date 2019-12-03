

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Conway's Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//main_frame.setResizable(false);
		
		GameModel model = new GameModel();
		GameView view = new GameView(main_frame);
		GameController controller = new GameController(model, view);

		main_frame.setContentPane(view);

		main_frame.pack();
		main_frame.setVisible(true);
	}

}
