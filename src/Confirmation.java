import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-24.
 */
public class Confirmation extends JFrame implements ActionListener {
    private JButton btnBack;
    private JTextField tfReservation;
    private DataTransfer dtR;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String nReservation;
    private int count;
    public Confirmation(DataTransfer dt) {
        this.setTitle("Confirmation");
        dtR = dt;
        count = dt.getCount();
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        setReservation();
        makeReservation();

        JLabel bReserve = new JLabel("Reservation ID");
        tfReservation = new JTextField(15);
        JLabel bText = new JLabel("Thank you for your purchase! Please save reservation ID for your records.");
        gbAdd(bReserve, 0, 0, 1, 1);
        gbAdd(tfReservation, 1, 0, 1, 1);
        gbAdd(bText, 0, 1, 1, 1);
        tfReservation.setText(dtR.getReservationID());
        tfReservation.setEditable(false);

        JPanel rBtn = new JPanel();
        btnBack = new JButton("Go Back to choose functionality");
        rBtn.add(btnBack);
        gbAdd(rBtn, 0, 5, 1, 1);

        btnBack.addActionListener(this);

        setSize(800, 600);
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

    private void setReservation() {
        DBConnector dbc = new DBConnector();
        dbc.setReservation(dtR);
    }
    private void makeReservation() {
        DBConnector dbc = new DBConnector();
        dbc.makeReservation(dtR);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new Functionality(dtR.getUsername());
            dispose();
        }
    }
}
