package controller;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import view.SendDataView;

public class SendDataEventHandler implements MouseListener, KeyListener{

	public SendDataView sendView;
	
	public String keyShow;
	public String keyClicked;
	
	public int limitNameNodeLength = 2;
	public SendDataEventHandler(SendDataView sendDataView) {
		this.sendView = sendDataView;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyShow = null;
		keyClicked = null;
		
		int code = e.getKeyCode();
		if (e.getSource() ==  sendView.txtStart)
		{
			if (code == KeyEvent.VK_BACK_SPACE)
			{
				sendView.txtStart.setText(" ");
				return;
			}
			
			int length = sendView.txtStart.getText().length();
			if (length == limitNameNodeLength)
			{
				keyShow = sendView.txtStart.getText();
				return;
			}
			
			//if system can run to here that mean length == 0
			if (code >= KeyEvent.VK_A && code <=KeyEvent.VK_Z)
			{
				keyClicked = "" + e.getKeyChar();
				return;
			}
		}
		else if (e.getSource() == sendView.txtEnd)
		{
			if (code == KeyEvent.VK_BACK_SPACE)
			{
				sendView.txtEnd.setText(" ");
				return;
			}
			
			int length = sendView.txtEnd.getText().length();
			if (length == limitNameNodeLength)
			{
				keyShow = sendView.txtEnd.getText();
				return;
			}
			
			//if system can run to here that mean length == 0
			if (code >= KeyEvent.VK_A && code <=KeyEvent.VK_Z)
			{
				keyClicked = "" + e.getKeyChar();
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == sendView.txtStart)
		{
			if (keyShow != null)
			{
				sendView.txtStart.setText(keyShow.toUpperCase());
			}
			else if (keyClicked != null)
			{
				sendView.txtStart.setText(keyClicked.toUpperCase());
			}
		}
		else if (e.getSource() == sendView.txtEnd)
		{
			if (keyShow != null)
			{
				sendView.txtEnd.setText(keyShow.toUpperCase());
			}
			else if (keyClicked != null)
			{
				sendView.txtEnd.setText(keyClicked.toUpperCase());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == sendView.btnSubmit)
		{
			/*
			 * RESET COLOR OF FULL LINE ON INPUT VIEW RETURN TO BLACK
			 */
			sendView.application.inputView.resetLineColor();
			
			/*
			 * SEND DATA TO SERVER
			 */
			
			sendView.controller.getData();
			
			String startNode = sendView.txtStart.getText();
			String endNode = sendView.txtEnd.getText();
			boolean everyThingOk = false;
			if (sendView.controller.checkNodeName(startNode) && sendView.controller.checkNodeName(endNode)) 
				everyThingOk = true;
			if (!everyThingOk)
			{
				JOptionPane.showMessageDialog(sendView.application,"Vui lòng nhập đúng đỉnh bắt đầu và đỉnh kết thúc!");
				return;
			}
				
			if (startNode.equals(endNode))
			{
				JOptionPane.showMessageDialog(sendView.application,"Đỉnh kết thúc trùng đỉnh bắt đầu, vui lòng nhập lại!");
				sendView.txtEnd.setText(null);
				return;
			}
			sendView.controller.sendData();
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
