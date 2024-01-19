package model;

import java.awt.GridLayout;
import java.awt.SecondaryLoop;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.SendDataView;
import view.InputData;
import view.ResultView;

public class Application extends JFrame{

	JPanel topHaftWindow;
	JPanel bottomHaftWindow;
	
	public InputData inputView;
	public SendDataView sendDatatView;
	public ResultView resultView;
	public boolean everyThingOk = true;
	public Application() 
	{
		
	}
	
	public void GUI()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));
		
		topHaftWindow = new JPanel();
		topHaftWindow.setLayout(new GridLayout(1,2));

		inputView = new InputData();
		sendDatatView = new SendDataView(inputView,this);
		topHaftWindow.add(sendDatatView);
		topHaftWindow.add(inputView);
		
		bottomHaftWindow = new JPanel(new GridLayout(1,1));
		resultView = new ResultView(this);
		bottomHaftWindow.add(resultView);
		
		this.add(topHaftWindow);
		this.add(bottomHaftWindow);	
		this.pack();
		this.setLocationRelativeTo(null);
		if (everyThingOk) this.setVisible(true);
		this.setTitle("Short Path Routing");
		//setting not resize to application
		this.setResizable(false);
		
		//setting resize to all frame
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) 
			{
				//SET SIZE TO ALL FRAME 
				//lastView.setSize(null);
			}
		});
	}
	
	private void start() {
		GUI();
		inputView.startAppThread();
	}
	public void stop() {
		System.exit(0);
	}
	public static void main(String[] args) {
		Application app = new Application();
		app.start();
		if (app.everyThingOk == false)
			app.stop();
	}
}
