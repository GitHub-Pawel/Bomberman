import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JoinGUI {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JTextField joinChatTitle;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JPanel ipAddressPanel;
    private JPanel portPanel;
    private JButton joinButton;
    private JTextField groupIPAddressTitle;
    private JTextField ipAddressText;
    private JTextField portNumberTitle;
    private JTextField portNumberText;

    private String ipAddress;
    private int portNumber;

    public JoinGUI() {
        JFrame frame = new JFrame("Join chat");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipAddress = ipAddressText.getText();
                try {
                    portNumber = Integer.parseInt(portNumberText.getText());
                }catch(NumberFormatException e1) {
                }
                ChatGUI chat = new ChatGUI(ipAddress, portNumber);
                frame.dispose();
            }
        });
    }

}
