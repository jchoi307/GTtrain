import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.StringContent;

/**
 * Created by Joon on 2016-04-20.
 */
public class TravelExtras extends JFrame implements ActionListener {
    private JPanel p;
    private JComboBox cbBaggage;
    private JButton btnBack;
    private JButton btnNext;
    private String departLoc;
    private String arriveLoc;
    private String date;
    private JDatePickerImpl datePicker;
    private JTextField tfPassenger;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private DataTransfer dtE;
    private int w;
    /**
     *
     */
    public TravelExtras(DataTransfer dt) {
        dtE = dt;
        w = dtE.getSelDepCount();
        this.setTitle("Travel Extras & Passenger Info");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bBaggage = new JLabel("Number of Baggage : ");
        String[] arrBaggage = {"0", "1", "2", "3", "4"};
        cbBaggage = new JComboBox(arrBaggage);
        JPanel pBaggage = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pBaggage.add(cbBaggage);
        gbAdd(bBaggage, 0, 0, 1, 1);
        gbAdd(pBaggage, 1, 0, 3, 1);

        JLabel bInfo = new JLabel("Every Passenger can bring up to 4 baggage 2 free of charge, 2 for $30 per bag");
        gbAdd(bInfo, 0, 1, 1, 1);

        JLabel bId = new JLabel("Passenger Name : ");
        tfPassenger = new JTextField(15);
        gbAdd(bId, 0, 2, 1, 1);
        gbAdd(tfPassenger, 1, 2, 3, 1);

        JPanel bButton = new JPanel();
        btnBack = new JButton("Back");
        bButton.add(btnBack);
        gbAdd(bButton, 0, 10, 4, 1);

        JPanel nButton = new JPanel();
        btnNext = new JButton("Next");
        nButton.add(btnNext);
        gbAdd(nButton, 5, 10, 4, 1);

        btnBack.addActionListener(this);
        btnNext.addActionListener(this);

        setSize(800, 600);
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

    public static void main(String[] args) {
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnBack) {
            new SearchTrain(dtE, (w - 1));
            dispose();
        } else if (ae.getSource() == btnNext) {
            String numBag = cbBaggage.getSelectedItem().toString();
            String passName = tfPassenger.getText();
            if (passName.length() < 1) {
                JOptionPane.showMessageDialog(this, "you have to input passenger name");
            } else {
                dtE.setBagNum((w - 1), numBag);
                dtE.setName((w - 1), passName);
                new MakeReservation(dtE);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "select error");
        }
    }
}