import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by Joon on 2016-04-19.
 */
public class SchoolInfo extends JFrame implements ActionListener {

    private JTextField tfEmail;
    private JButton btnBack;
    private JButton btnSubmit;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private JLabel noValid = new JLabel();
    private JFrame frame = new JFrame();
    private String id;

    /**
     *
     * @param username user id
     */
    public SchoolInfo(String username) {
        id = username;
        this.setTitle("Add School Info");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        JLabel bEmail = new JLabel("School Email Address : ");
        tfEmail = new JTextField(20);
        gbAdd(bEmail, 0, 1, 1, 1);
        gbAdd(tfEmail, 1, 1, 1, 1);

        JPanel bButton = new JPanel();
        btnBack = new JButton("Back");
        bButton.add(btnBack);
        gbAdd(bButton, 0, 3, 1, 1);

        JPanel sButton = new JPanel();
        btnSubmit = new JButton("Submit");
        sButton.add(btnSubmit);
        gbAdd(sButton, 1, 3, 1, 1);

        btnBack.addActionListener(this);
        btnSubmit.addActionListener(this);

        setSize(350, 500);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    /** gridbag Layout */
    private void gbAdd(JComponent c, int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gb.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }

    /*
    public static void main(String[] args) {
    }
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        String eMail = tfEmail.getText();
        if (e.getSource() == btnBack) {
            dispose();
        } else if (e.getSource() == btnSubmit) {
            if (tfEmail.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "Wrong Input.");
            } else if (!(eMail.contains(".edu"))) {
                JOptionPane.showMessageDialog(this, "Your Email is not a valid School Email.");
            } else {
                submitEmail();
            }
        }
    }

    /** */
    private void submitEmail() {
        DataTransfer dt = getData();
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.submitEmail(dt);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Register Successful");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Register Failed by some reason");
        }
    }

    /** */
    public DataTransfer getData() {
        DataTransfer dt = new DataTransfer();
        String email = tfEmail.getText();

        dt.setUsername(id);
        dt.setEmail(email);

        return dt;
    }
}
