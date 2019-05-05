import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatGUI implements MessageObserver{
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel chatPanel;
    private JPanel buttonsPanel;
    private JTextField title;
    private JTextArea chatText;
    private JButton sendMessageButton;
    private JButton leaveChatButton;
    private JPanel messagePanel;
    private JTextField messageText;

    private String ipAddress;
    private int portNumber;
    private Runner runner;

    public ChatGUI(String ipAddress, int port) {

        this.ipAddress = ipAddress;
        this.portNumber = port;

        JFrame frame = new JFrame("Chat");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        try {
            this.runner = new Runner(this.ipAddress, this.portNumber);
        } catch (IOException e) {}

        this.runner.getClient().subscribe(this);


        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    runner.getServer().sendMessage(messageText.getText());
                } catch (IOException e1) {}
                messageText.setText(null);
            }
        });
        leaveChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    runner.getClient().leave();
                } catch (IOException e1) {}
                System.exit(0);
            }
        });

    }

    @Override
    public void showMessage() {
        chatText.append(runner.getClient().getMessage() + "\n");
    }

}
