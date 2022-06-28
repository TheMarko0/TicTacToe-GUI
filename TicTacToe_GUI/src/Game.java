import javax.swing.*;
import java.util.Random;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


class Main {
	public static int playerBot;
	public JButton button1 = new JButton("");
	public JButton button2 = new JButton("");
	public JButton button3 = new JButton("");
	public JButton button4 = new JButton("");
	public JButton button5 = new JButton("");
	public JButton button6 = new JButton("");
	public JButton button7 = new JButton("");
	public JButton button8 = new JButton("");
	public JButton button9 = new JButton("");
	public JButton buttonReset = new JButton("reset");

	JButton[] btns = { button1, button2, button3, button4, button5, button6, button7, button8, button9 };

	JPanel windowContent = new JPanel();
	final Random rnd = new Random();

	Main() {
		JPanel p1 = new JPanel();
		GridLayout gl = new GridLayout(4, 3);
		p1.setLayout(gl);
		p1.add(button1);
		p1.add(button2);
		p1.add(button3);
		p1.add(button4);
		p1.add(button5);
		p1.add(button6);
		p1.add(button7);
		p1.add(button8);
		p1.add(button9);
		p1.add(buttonReset);
		windowContent.add("Center", p1);

		JFrame frame = new JFrame("Main");
		frame.setContentPane(windowContent);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		Engine eng = new Engine(this);
		button1.addActionListener(eng);
		button2.addActionListener(eng);
		button3.addActionListener(eng);
		button4.addActionListener(eng);
		button5.addActionListener(eng);
		button6.addActionListener(eng);
		button7.addActionListener(eng);
		button8.addActionListener(eng);
		button9.addActionListener(eng);
		buttonReset.addActionListener(eng);
	}


	public static int selectGame()
	{
		String botPlayer;
		do {
			botPlayer = JOptionPane.showInputDialog("Do you want to play with bot/player \n 1-bot 2-player");

			if (botPlayer == null)
				System.exit(0);

		}while(botPlayer.length() != 1 || !(botPlayer.equals("1") || botPlayer.equals("2")));
		playerBot = Integer.parseInt(botPlayer);

		return playerBot;
	}

	public static void main(String[] args) {
		playerBot = selectGame();
		new Main();
	}
class Engine implements ActionListener {
	Main parent;

	Engine(Main parent) {
		this.parent = parent;
	}

	private String turn = "X";
	private int count = 0;

	public void actionPerformed(ActionEvent e) {

		JButton clickedButton = (JButton) e.getSource();
		Object src = e.getSource();

		if (src == parent.buttonReset) {
			restart();
			return;
		}

		if (!clickedButton.getText().isEmpty())
			return;

		clickedButton.setText(turn);

		if (playerBot == 1) {
			if (nextPlayer())
				return;
			moveBot();

			if (nextPlayer())
				return;
		} else if (playerBot == 2) {
			if (nextPlayer())
				return;
		}

	}
	public JButton check(String check) {

		JButton botBtn = null;

		for (int i = 0; i < btns.length && botBtn == null; i++) {
			if (btns[i].getText().isEmpty()) {
				btns[i].setText(check);
				if (hwoWin(check))
					botBtn = btns[i];
				btns[i].setText("");
			}
		}
		return botBtn;
	}

	public void moveBot() {


		JButton botBtn;

		if ((botBtn = check(turn)) == null && (botBtn = check(turn.equals("X") ? "O" : "X")) == null) {
			do {
				botBtn = btns[rnd.nextInt(btns.length)];
			} while (!botBtn.getText().isEmpty());
		}

		botBtn.setText(turn);
	}

	public boolean nextPlayer() {
		count++;

		if (count >= 5)
			if (hwoWin("O")) {
				JOptionPane.showMessageDialog(null, "O wins");
				restart();
				return true;
			} else if (hwoWin("X")) {
				JOptionPane.showMessageDialog(null, "X wins");
				restart();
				return true;
			}
		if (count == 9) {
			JOptionPane.showMessageDialog(null, "Tie");
			restart();
			return true;
		}

		if (turn.equals("X"))
			turn = "O";

		else
			turn = "X";

		return false;
	}

	public boolean hwoWin(String le) {
		return (parent.button1.getText().equals(le) && parent.button2.getText().equals(le) && parent.button3.getText().equals(le)) ||
				(parent.button4.getText().equals(le) && parent.button5.getText().equals(le) && parent.button6.getText().equals(le)) ||
				(parent.button7.getText().equals(le) && parent.button8.getText().equals(le) && parent.button9.getText().equals(le)) ||

				(parent.button1.getText().equals(le) && parent.button5.getText().equals(le) && parent.button9.getText().equals(le)) ||
				(parent.button3.getText().equals(le) && parent.button5.getText().equals(le) && parent.button7.getText().equals(le)) ||

				(parent.button1.getText().equals(le) && parent.button4.getText().equals(le) && parent.button7.getText().equals(le)) ||
				(parent.button2.getText().equals(le) && parent.button5.getText().equals(le) && parent.button8.getText().equals(le)) ||
				(parent.button3.getText().equals(le) && parent.button6.getText().equals(le) && parent.button9.getText().equals(le));
	}

	private void restart()
	{
		playerBot = selectGame();
		for (JButton btn : btns)
			btn.setText("");
		count = 0;
		turn = "X";
	}
}
}
