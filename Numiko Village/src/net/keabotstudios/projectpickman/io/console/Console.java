package net.keabotstudios.projectpickman.io.console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.PrintStream;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import net.keabotstudios.projectpickman.References;
import net.keabotstudios.projectpickman.loading.Textures;

public class Console {

	private JFrame frame;
	private JTextArea consoleText;
	private JScrollPane pane;

	public Console() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = new JFrame(References.NAME + " Console");
		frame.setIconImages(Arrays.asList(Textures.icons));
		frame.setMinimumSize(new Dimension(500, 400));
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consoleText = new JTextArea();
		consoleText.setFont(new Font("Consolas", Font.PLAIN, 12));
		consoleText.setBackground(Color.BLACK);
		consoleText.setForeground(Color.WHITE);
		consoleText.setEditable(false);
		PrintStream con = new PrintStream(new TextAreaOutputStream(consoleText, Integer.MAX_VALUE));
		System.setOut(con);
		System.setErr(con);
		pane = new JScrollPane(consoleText);
		frame.add(pane);
		frame.setVisible(true);
	}

}
