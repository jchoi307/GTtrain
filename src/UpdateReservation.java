import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-24.
 */
public class UpdateReservation extends JFrame implements ActionListener {
    private JButton btnBack;
    private JButton btnSearch;
    private JTextField tfReservation;
    private DataTransfer dtR = new DataTransfer();
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String nReservation;
    private int count;

    public UpdateReservation(String username) {
        this.setTitle("Update Reservation");
        dtR.setUsername(username);
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bReserve = new JLabel("Reservation ID");
        tfReservation = new JTextField(15);
        gbAdd(bReserve, 0, 0, 1, 1);
        gbAdd(tfReservation, 1, 0, 1, 1);

        JPanel sBtn = new JPanel();
        btnSearch = new JButton("Search");
        sBtn.add(btnSearch);
        gbAdd(sBtn, 2, 0, 1, 1);

        JPanel rBtn = new JPanel();
        btnBack = new JButton("Back");
        rBtn.add(btnBack);
        gbAdd(rBtn, 1, 3, 1, 1);

        btnBack.addActionListener(this);
        btnSearch.addActionListener(this);

        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            nReservation =  tfReservation.getText();
            dtR.setReservationID(nReservation);
            boolean check = reservedUserCheck();
            boolean ok = getReservation();
            boolean cancelCheck = checkIsCanceled();
            if (check) {
                if (cancelCheck) {
                    if (ok) {
                        new UpdateReservationNext(dtR);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Reservation Number might be wrong");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Reservation already Canceled.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username doesn't match");
            }
        }
        if (e.getSource() == btnBack) {
            new Functionality(dtR.getUsername());
            dispose();
        }
    }

    private boolean reservedUserCheck() {
        DBConnector dbc = new DBConnector();
        return dbc.reservedUserCheck(dtR);
    }

    private boolean checkIsCanceled() {
        DBConnector dbc = new DBConnector();
        dbc.checkIsCanceled(dtR);
        if (dtR.getIsCanceled() == 1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean getReservation() {
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.getReservation(dtR);
        if (ok) {
            return true;
        }
        return ok;
    }

}
