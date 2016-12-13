package com.njucs.dictionary.client.common;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MyFrame extends JFrame {
	private static final long serialVersionUID = 7620334720542206070L;

	public MyFrame(){
		
		this.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
                SendRequest.CloseRequest();
				System.exit(0);
            }
		});
		
	}
}
