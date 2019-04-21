package bomberman.gui;

import bomberman.engine.ClientEngine;
import bomberman.engine.ServerEngine;
import bomberman.network.ServerRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerConstructor {
    private int inputPort;
    private int outputPort;
    private int numberOfPlayers;
    private int fieldSize;
    private ServerRunner serverRunner;

    private JPanel panel1;
    private JPanel portPanel;
    private JPanel nrOfPlayersPanel;
    private JPanel fieldSizePanel;
    private JPanel createServerPanel;
    private JButton createServerButton;
    private JPanel mainPanel;
    private JTextField createServerTitleTextField;
    private JTextField serverInputPortTitleTextField;
    private JTextField serverOutputPortTitleTextField;
    private JTextField serverInputPortTextField;
    private JTextField serverOutputPortTextField;
    private JTextField numberOfPlayersTitleTextField;
    private JCheckBox players2CheckBox;
    private JCheckBox players3CheckBox;
    private JCheckBox players4CheckBox;
    private JTextField gameFieldSizeTitleTextField;
    private JCheckBox verySmallCheckBox;
    private JCheckBox smallCheckBox;
    private JCheckBox mediumCheckBox;
    private JCheckBox bigCheckBox;
    private JCheckBox veryBigCheckBox;


    public ServerConstructor() {
        JFrame frame = new JFrame("Server Constructor");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        serverInputPortTextField.setText("65432");
        serverOutputPortTextField.setText("65433");
        players2CheckBox.setSelected(true);
        this.numberOfPlayers = 2;
        smallCheckBox.setSelected(true);
        this.fieldSize = 15;

        players2CheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (players2CheckBox.isSelected()){
                    players3CheckBox.setSelected(false);
                    players4CheckBox.setSelected(false);
                }
                numberOfPlayers = 2;
            }
        });
        players3CheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (players3CheckBox.isSelected()){
                    players2CheckBox.setSelected(false);
                    players4CheckBox.setSelected(false);
                }
                numberOfPlayers = 3;
            }
        });
        players4CheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (players4CheckBox.isSelected()){
                    players2CheckBox.setSelected(false);
                    players3CheckBox.setSelected(false);
                }
                numberOfPlayers = 4;
            }
        });

        verySmallCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verySmallCheckBox.isSelected()){
                    smallCheckBox.setSelected(false);
                    mediumCheckBox.setSelected(false);
                    bigCheckBox.setSelected(false);
                    veryBigCheckBox.setSelected(false);
                }
                fieldSize = 13;
            }
        });
        smallCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (smallCheckBox.isSelected()){
                    verySmallCheckBox.setSelected(false);
                    mediumCheckBox.setSelected(false);
                    bigCheckBox.setSelected(false);
                    veryBigCheckBox.setSelected(false);
                }
                fieldSize = 15;
            }
        });
        mediumCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mediumCheckBox.isSelected()){
                    verySmallCheckBox.setSelected(false);
                    smallCheckBox.setSelected(false);
                    bigCheckBox.setSelected(false);
                    veryBigCheckBox.setSelected(false);
                }
                fieldSize = 17;
            }
        });
        bigCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bigCheckBox.isSelected()){
                    verySmallCheckBox.setSelected(false);
                    smallCheckBox.setSelected(false);
                    mediumCheckBox.setSelected(false);
                    veryBigCheckBox.setSelected(false);
                }
                fieldSize = 19;
            }
        });
        veryBigCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (veryBigCheckBox.isSelected()){
                    verySmallCheckBox.setSelected(false);
                    smallCheckBox.setSelected(false);
                    mediumCheckBox.setSelected(false);
                    bigCheckBox.setSelected(false);
                }
                fieldSize = 21;
            }
        });
        createServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    inputPort = Integer.parseInt(serverInputPortTextField.getText());
                    outputPort = Integer.parseInt(serverOutputPortTextField.getText());
                }catch(NumberFormatException e1) {}
                serverRunner = new ServerRunner(inputPort, outputPort, numberOfPlayers, fieldSize);
                Thread serverThread = new Thread(serverRunner);
                serverThread.start();
                ClientEngine clientEngine = new ClientEngine("localhost", inputPort, outputPort);
                frame.dispose();

            }
        });
    }

}
