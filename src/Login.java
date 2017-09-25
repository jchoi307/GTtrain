/**
 * Created by Joon on 2016-04-12.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    private JButton btnLogin;
    private JButton btnRegister;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private DataTransfer dt = new DataTransfer();
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    //static String username;

    /**
     *
     */
    public Login() {
        this.setTitle("Login");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bLogin = new JLabel("Login");
        JLabel bUsername = new JLabel("Username : ");
        tfUsername = new JTextField(15);
        gbAdd(bLogin, 0, 0, 1, 1);
        gbAdd(bUsername, 0, 1, 1, 1);
        gbAdd(tfUsername, 1, 1, 1, 1);

        JLabel bPwd = new JLabel("Password : ");
        pfPassword = new JPasswordField(20);
        gbAdd(bPwd, 0, 2, 1, 1);
        gbAdd(pfPassword, 1, 2, 1, 1);

        JPanel pLogin = new JPanel();
        btnLogin = new JButton("Login");
        pLogin.add(btnLogin);
        gbAdd(pLogin, 0, 3, 1, 1);

        JPanel pRegister = new JPanel();
        btnRegister = new JButton("Register");
        pRegister.add(btnRegister);
        gbAdd(pRegister, 1, 3, 1, 1);

        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);

        setSize(350, 500);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void gbAdd(JComponent c, int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gb.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }
    /**
     *
     * @param args .
     */
    public static void main(String[] args) {
        new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DBConnector dbc = new DBConnector();
        DataTransfer dt = new DataTransfer();
        if (e.getSource() == btnLogin) {
            String username = tfUsername.getText();
            String pwd = pfPassword.getText();
            dt.setUsername(username);
            if (dbc.getManagerInfo(username, pwd)) {
                new ManagerFunction(username);
                dispose();
            }
            boolean ok = dbc.isUserExist(username);
            if (ok) {
                if (dbc.getCustomerInfo(username).getPwd().equals(pwd)) {
                    new Functionality(username);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong Input. Please Try Again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "User doesn't exist");
            }
        }
        if (e.getSource() == btnRegister) {
            new Registration();
        }
    }
}
