/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsocket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Affandi
 */
public class Main implements Runnable {

    private Client client;
    private portGui portView;
    private chatGui view;
    private String mess = "";

    public Main() {
        this.client = new Client();
        this.portView = new portGui();
        this.view = new chatGui();
        this.portView.setTitle("Port Input");
        this.portView.setVisible(true);
        this.portView.getBtnOK().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(portView.getTxtPort().getText()).equals("6666")) {
                    client.startConnection("127.0.0.1",
                            Integer.valueOf(portView.getTxtPort().getText()));
                    portView.setVisible(false);
                    view.setTitle("Client Chat");
                    view.setVisible(true);
                } else {
                    portView.getTxtPort().setText("");
                }
            }
        });
        this.view.getBtnSend().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mess += time() + "->" + String.valueOf(view.getTxtChat().getText()+"\n");
                String response = client.sendMessage(time() + "<- " +String.valueOf(view.getTxtChat().getText()));
                mess += response + "\n";
            }
        });
    }

    public String time() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        String time = simpleDateFormat.format(date);
        return time;
    }

    @Override
    public void run() {
        do {
            if (this.view.getAreaChat().getText().equals(mess) == false) {
                this.view.getAreaChat().setText(mess);
            }
        } while (true);
    }

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }
}
