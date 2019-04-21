package bomberman.gui;

import bomberman.engine.ClientEngine;
import bomberman.network.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientConstructor {
    private String serverAddress;
    private int serverInputPort;
    private int serverOutputPort;

    private JPanel panel1;
    private JTextField joinServerTitleTextField;
    private JPanel mainPanel;
    private JButton joinButton;
    private JTextField serverAddressTitleTextField;
    private JTextField serverAddressTextField;
    private JTextField serverInputPortTitleTextField;
    private JTextField serverOutputPortTitleTextField;
    private JTextField serverInputPortTextField;
    private JTextField serverOutputPortTextField;
    private JPanel serverAddressPanel;
    private JPanel portPanel;


    public ClientConstructor() {
        JFrame frame = new JFrame("Client Constructor");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        serverAddressTextField.setText("localhost");
        serverInputPortTextField.setText("65432");
        serverOutputPortTextField.setText("65433");

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverAddress = serverAddressTextField.getText();
                try {
                    serverInputPort = Integer.parseInt(serverInputPortTextField.getText());
                    serverOutputPort = Integer.parseInt(serverOutputPortTextField.getText());
                }catch(NumberFormatException e1) {
                }
                ClientEngine clientEngine = new ClientEngine(serverAddress, serverInputPort, serverOutputPort);
                frame.dispose();
            }
        });
    }

}
