package Funcoes;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class AutoCompleteTextField {

    private JFrame frame;
    
    private ArrayList<String> listSomeString = new ArrayList<String>();
    
    private Java2sAutoTextField someTextField = new Java2sAutoTextField(listSomeString);

    public AutoCompleteTextField() {
        
        listSomeString.add("-");
        listSomeString.add("fazer");

//
        someTextField.setFont(new Font("Serif", Font.BOLD, 16));
        someTextField.setForeground(Color.black);
        someTextField.setBackground(Color.orange);
        someTextField.setName("someTextField");
        someTextField.setDataList(listSomeString);

//
        frame = new JFrame();
        frame.setLayout(new GridLayout(0, 1, 10, 10));
        frame.add(someTextField);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.pack();
        frame.setVisible(true);
//
        SwingUtilities.invokeLater(() -> {
            someTextField.setText("-");
            someTextField.grabFocus();
            someTextField.requestFocus();
            someTextField.setText(someTextField.getText());
            someTextField.selectAll();
        });

    }
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutoCompleteTextField aCTF = new AutoCompleteTextField();
        });
    }*/
}