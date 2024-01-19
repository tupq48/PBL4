package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import controller.SendDataEventHandler;
import controller.SendDataViewController;
import model.Application;


//this is First Panel in ours Project
public class SendDataView extends JLayeredPane {

	public Application application; 
	
	public JPanel panel;
	private int screenWidth = 500;
	private int screenHeight = 400;
	
	private JLabel lbStart, lbEnd;
	public JTextField txtStart;
	public JTextField txtEnd;
	public JButton btnSubmit;
	
	public InputData inputView;
	public SendDataEventHandler eHandler;
	public SendDataViewController controller;
	public SendDataView(InputData inputView, Application app)
	{
		this.application = app;
		this.inputView = inputView;
		controller = new SendDataViewController(this);
		eHandler = new SendDataEventHandler(this);
		
		//setup border in firstPanel on ours Project
		setupMainBorder();
	}


	private void setupMainBorder() {
		Border tempBorder = BorderFactory.createLineBorder(Color.black);
		Border mainBorder = BorderFactory.createTitledBorder(tempBorder, "Chọn trạm trên mạng");
		this.setBorder(mainBorder);
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		
		int x, y, w, h;
		lbStart = new JLabel("Nguồn");
		lbStart.setFont(new Font("Serif", Font.PLAIN, 40));
		lbStart.setBounds(80, 50, 170, 50);
		
		lbEnd = new JLabel("Đích");
		lbEnd.setFont(new Font("Serif", Font.PLAIN, 40));
		lbEnd.setBounds(325, 50, 170, 50);
		
		txtStart = new JTextField();
		txtStart.setBounds(110, 120, 50, 50);
		txtStart.setFont(new Font("Serif", Font.PLAIN, 40));
		txtStart.setHorizontalAlignment(SwingConstants.CENTER);
		txtStart.addKeyListener(eHandler);
		
		txtEnd = new JTextField();
		txtEnd.setBounds(340, 120, 50, 50);
		txtEnd.setFont(new Font("Serif", Font.PLAIN, 40));
		txtEnd.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnd.addKeyListener(eHandler);
		
		btnSubmit = new JButton("Chọn");
		btnSubmit.setBounds(200,230,100,50);
		btnSubmit.setFont(new Font("Serif", Font.PLAIN, 30));
		btnSubmit.addMouseListener(eHandler);
		
		this.add(lbStart);
		this.add(lbEnd);
		this.add(txtStart);
		this.add(txtEnd);
		this.add(btnSubmit);

	}


	public String getStartNodeName() 
	{
		
		return txtStart.getText();
	}

	public String getEndNodeName() {
		
		return txtEnd.getText();
	}
	
}
