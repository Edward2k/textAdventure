package PlayerClient;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Interface {
    private JTextArea messages;
    private JFrame gamePanel;
    private JTextField userInput;
    private String input;

    Interface() {
        input = null;
        newInterface();
    }

    private void newInterface() {
        gamePanel = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension(screenSize.width/2, screenSize.height/2);
        int x = 0;
        int y = 0;
        gamePanel.setBounds(x,y,frameSize.width,frameSize.height);
        messages = new JTextArea(x,y);
        userInput = new JTextField();

        messages.setLineWrap(true);
        messages.setWrapStyleWord(true);
        messages.setEditable(false);

        DefaultCaret caret = (DefaultCaret)messages.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        gamePanel.getContentPane().setLayout(new BorderLayout());
        gamePanel.getContentPane().add(new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
        gamePanel.getContentPane().add(userInput,BorderLayout.SOUTH);
        gamePanel.setVisible(true);

        userInput.addActionListener( action );
    }

    public void append(String output) {
        messages.append("\n" + output + "\n");
    }

    public String getInput() {
        while(true) {
            if (input == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } else {break;}
        }
        String text = input;
        append(text);
        input = null;
        return text;
    }

    Action action = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            input = userInput.getText();
            userInput.setText("");
        }
    };

}
