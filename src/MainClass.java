
import javax.swing.*;
import java.awt.*;

public class MainClass {

	public static void main(String[] args) {
		
		Object[] choices = { "AI & AI", "Player & AI" };
		JFrame f = new JFrame();
		int userChoice = JOptionPane.showOptionDialog(f, "Select One Of Them!", "", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choices, choices[0]);
		
		Board b = new Board();
		JPanel textPanel = new JPanel(new BorderLayout());
		JTextArea text = new JTextArea(10,20);
		text.setMinimumSize(new Dimension(300, 300));
		text.setEditable(false);
		text.setText("--> " + choices[userChoice] + " Style Chess <--");
		Game g;
		View view;
		JLabel turnLabel;
		JScrollPane scroller;
		JFrame frame;
		switch(userChoice) {
			case 0 :
				
				turnLabel = new JLabel("AI-1's Turn");
				g = new Game(userChoice, "AI 1", "AI 2", turnLabel);
				view = new View(b, text,g);
				scroller = new JScrollPane(text);
				scroller.setMinimumSize(new Dimension(300,300));
				textPanel.add(scroller, BorderLayout.CENTER);
				textPanel.add(turnLabel, BorderLayout.NORTH);
				
				frame = new JFrame();
				frame.setLayout(new BorderLayout());
				frame.add(view, BorderLayout.CENTER);
				frame.add(textPanel, BorderLayout.SOUTH);
				frame.pack();
				frame.setMinimumSize(new Dimension(500, 700));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
				
				break;
			default :
				
				turnLabel = new JLabel("Your Turn!");
				g = new Game(userChoice, turnLabel);
				view = new View(b, text,g);

				scroller = new JScrollPane(text);
				scroller.setMinimumSize(new Dimension(300,300));
				textPanel.add(turnLabel, BorderLayout.NORTH);
				textPanel.add(scroller, BorderLayout.CENTER);
				
				frame = new JFrame();
				frame.setLayout(new BorderLayout());
				frame.add(view, BorderLayout.CENTER);
				frame.add(textPanel, BorderLayout.SOUTH);
				frame.pack();
				frame.setMinimumSize(new Dimension(500, 700));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
				
		}
	}

}
