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
public class SearchTrain extends JFrame implements ActionListener {
    private JPanel p;
    private JComboBox cbDepart;
    private JComboBox cbArrive;
    private JButton btnFind;
    private String departLoc;
    private String arriveLoc;
    private String date;
    private JDatePickerImpl datePicker;
    private DataTransfer dtS;
    GridBagLayout gb;
    GridBagConstraints gbc;
    private String username;
    private int resNum;

    /**
     *
     */
    public SearchTrain(DataTransfer dt, int w) {
        dtS = dt;
        username = dt.getUsername();
        resNum = w;
        this.setTitle("Search Train");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bDepart = new JLabel("Departs From : ");
        String[] arrDepart = {"---", "Boston, MA (BOS)", "Chicago, IL (CHI)", "SanDiego, CA (SAN)", "Phoenix, AZ (PXN)",
                "Napa, CA (NAP)", "Providence, RI (PVD)", "Pueblo, CO (PUB)", "Portland, OR (PDX)",
                "New York, NY (NYP)", "Las Vegas, NV (LVS)", "Lost Angeles, CA (LAX)", "Long Beach, CA (LBC)",
                "Oakland, CAL (OAH)", "Atlanta, GA (ATL)", "Orlando, FL (ORL)", "Miami, FL (MIA)",
                "Gainesville, GA (GNS)", "Savannah, GA (SAV)", "Toccoa, GA (TCA)", "Fort Lauderdale, FL (FTL)",
                "Tampa, FL (TPA)"};
        cbDepart = new JComboBox(arrDepart);
        JPanel pDepart = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDepart.add(cbDepart);
        gbAdd(bDepart, 0, 0, 1, 1);
        gbAdd(pDepart, 1, 0, 1, 1);


        JLabel bArrive = new JLabel("Arrives At : ");
        String[] arrArrive = {"---", "Boston, MA (BOS)", "Chicago, IL (CHI)", "SanDiego, CA (SAN)", "Phoenix, AZ (PXN)",
                "Napa, CA (NAP)", "Providence, RI (PVD)", "Pueblo, CO (PUB)", "Portland, OR (PDX)",
                "New York, NY (NYP)", "Las Vegas, NV (LVS)", "Lost Angeles, CA (LAX)", "Long Beach, CA (LBC)",
                "Oakland, CAL (OAH)", "Atlanta, GA (ATL)", "Orlando, FL (ORL)", "Miami, FL (MIA)",
                "Gainesville, GA (GNS)", "Savannah, GA (SAV)", "Toccoa, GA (TCA)", "Fort Lauderdale, FL (FTL)",
                "Tampa, FL (TPA)"};
        cbArrive = new JComboBox(arrArrive);
        JPanel pArrive = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pArrive.add(cbArrive);
        gbAdd(bArrive, 0, 2, 1, 1);
        gbAdd(pArrive, 1, 2, 1, 1);

        JLabel bDate = new JLabel("Departure Date : ");
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel pDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDate.add(datePicker);
        gbAdd(bDate, 0, 4, 1, 1);
        gbAdd(pDate, 1, 4, 1, 1);

        JPanel pButton = new JPanel();
        btnFind = new JButton("Find Trains");
        pButton.add(btnFind);
        gbAdd(pButton, 0, 10, 2, 1);

        btnFind.addActionListener(this);

        setSize(600, 500);
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
        if (ae.getSource() == btnFind){
            departLoc = cbDepart.getSelectedItem().toString();
            arriveLoc = cbArrive.getSelectedItem().toString();
            date = datePicker.getJFormattedTextField().getText();
            selectDepart();
        } else {
            JOptionPane.showMessageDialog(this, "select error");
        }
    }
    /**
     *
     */
    private void selectDepart() {
        dtS.setDepartStation(resNum, departLoc);
        dtS.setArriveStation(resNum, arriveLoc);
        dtS.setDate(resNum, date);
        dtS.setSelDepCount(resNum);
        dtS.setUsername(username);
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.selectDepart(dtS, resNum);

        if (ok) {
            System.out.println("Getting Train Data");
            new SelectDeparture(dtS);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Schedule unavailable");
        }
    }

    public class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }
}