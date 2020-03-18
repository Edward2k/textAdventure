package PlayerClient;

import javax.swing.*;
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
        System.out.println("here");
        gamePanel = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
        int x = (int)(frameSize.width/2);
        int y = (int)(frameSize.height/2);
        gamePanel.setBounds(x,y,frameSize.width,frameSize.height);
        messages = new JTextArea(x,y);
        userInput = new JTextField();
        messages.setEditable(false);

        gamePanel.getContentPane().setLayout(new BorderLayout());
        gamePanel.getContentPane().add(new JScrollPane(messages),BorderLayout.CENTER);
        gamePanel.getContentPane().add(userInput,BorderLayout.SOUTH);
        gamePanel.setVisible(true);

        userInput.addActionListener( action );
    }

    public void append(String output) {
        messages.append(output);
    }

    public String getInput() {
        while(true) {
            if (input == null) {
                System.out.println("");
                continue;
            } else {break;}
        }
        String text = input;
        input = null;
        return text;
    }

    public void setInput() {input = null;}

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
