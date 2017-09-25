import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JFormattedTextField.AbstractFormatter;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class UpdateReservationF extends JFrame implements ActionListener{
    private JButton btnBack;
    private JButton btnSubmit;
    private JButton btnSearch;
    private JTextField tfChangeFee;
    private JTextField tfTotalCost;
    private DataTransfer dtM;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private JDatePickerImpl datePicker;
    private int count;
    private String updatePrice;
    private DefaultTableModel rowModel2;

    public UpdateReservationF(DataTransfer dt, int i) {
        count = i;
        this.setTitle("Update Reservation");
        dtM = dt;
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        //getPrice();

        JLabel bCurrent = new JLabel("Current Train Ticket");
        gbAdd(bCurrent, 0, 0, 1, 1);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Train (Train Number)");
        model.addColumn("Time (Duration)");
        model.addColumn("Departs From");
        model.addColumn("Arrives At");
        model.addColumn("Class");
        model.addColumn("Price");
        model.addColumn("# of Baggage");
        model.addColumn("Passenger Name");
        JTable table = new JTable(model);

        DefaultTableModel rowModel = (DefaultTableModel) table.getModel();
        rowModel.addRow(new Object[]{dtM.getTrainNums(i), dtM.getDate(i) + " "
                + dtM.getDepartureTime(i) + " ~ " + dtM.getArrivalTime(i) + " "
                + dtM.getDuration(i), dtM.getDepartStation(i),
                    dtM.getArriveStation(i), dtM.getSeatClass(i), "$" + dtM.getConfirmPrice(i), dtM.getBagNum(i), dtM.getName(i)});

        JScrollPane scrollPane = new JScrollPane(table);
        gbAdd(scrollPane, 0, 1, 3, 1);

        JLabel bNewDate = new JLabel("New Departure Date");
        UtilDateModel calModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(calModel, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel pDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDate.add(datePicker);
        gbAdd(bNewDate, 0, 2, 1, 1);
        gbAdd(pDate, 1, 2, 1, 1);
        JPanel sBtn = new JPanel();
        btnSearch = new JButton("Search Availability");
        sBtn.add(btnSearch);
        gbAdd(sBtn, 2, 2, 1, 1);

        JLabel bUpdated = new JLabel("Updated Train Ticket");
        gbAdd(bUpdated, 0, 3, 1, 1);
        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("Train (Train Number)");
        model2.addColumn("Time (Duration)");
        model2.addColumn("Departs From");
        model2.addColumn("Arrives At");
        model2.addColumn("Class");
        model2.addColumn("Price");
        model2.addColumn("# of Baggage");
        model2.addColumn("Passenger Name");
        JTable table2 = new JTable(model2);
        rowModel2 = (DefaultTableModel) table2.getModel();

        JScrollPane scrollPane2 = new JScrollPane(table2);
        gbAdd(scrollPane2, 0, 4, 3, 1);

        JLabel bChangeFee = new JLabel("Change Fee");
        tfChangeFee = new JTextField(10);
        tfChangeFee.setText("50");
        tfChangeFee.setEditable(false);
        gbAdd(bChangeFee, 0, 5, 1, 1);
        gbAdd(tfChangeFee, 1, 5, 1, 1);

        JLabel bTotalCost = new JLabel("Updated Total Cost");
        int chargeBag = 0;
        int tempNum = Integer.parseInt(dtM.getBagNum(0));
        if (tempNum > 2) {
             chargeBag = (tempNum - 2);
        }
        double tempPrice = Double.parseDouble(dtM.getConfirmPrice(0)) + ((double) chargeBag * 30);
        updatePrice = String.format("%.2f", (tempPrice + 50));
        String currPrice = String.format("%.2f", tempPrice);
        tfTotalCost = new JTextField(10);
        tfTotalCost.setText(currPrice);
        tfTotalCost.setEditable(false);
        gbAdd(bTotalCost, 0, 6, 1, 1);
        gbAdd(tfTotalCost, 1, 6, 1, 1);

        JPanel bBtn = new JPanel();
        btnBack = new JButton("Back");
        bBtn.add(btnBack);
        gbAdd(bBtn, 0, 8, 1, 1);

        JPanel subBtn = new JPanel();
        btnSubmit = new JButton("Submit");
        subBtn.add(btnSubmit);
        gbAdd(subBtn, 2, 8, 1, 1);

        btnSearch.addActionListener(this);
        btnBack.addActionListener(this);
        btnSubmit.addActionListener(this);

        setSize(1200, 800);
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

    public static void main(String[] args) {
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new UpdateReservation(dtM.getUsername());
            dispose();
        }
        if (e.getSource() == btnSubmit) {
            DBConnector dbc = new DBConnector();
            dtM.setConfirmPrice(0, updatePrice);
            dbc.updateTotalPrice(dtM);
            boolean ok = dbc.updateReserve(dtM);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Updated.");
            } else {
                JOptionPane.showMessageDialog(this, "Something wrong happened");
            }
            new Functionality(dtM.getUsername());
            dispose();
        }
        if (e.getSource() == btnSearch) {
            String newDate = datePicker.getJFormattedTextField().getText();
            dtM.setNewDate(newDate);
            if (rowModel2.getRowCount() != 0) {
                rowModel2.removeRow(0);
            }
            rowModel2.addRow(new Object[]{dtM.getTrainNums(count), dtM.getNewDate() + " "
                    + dtM.getDepartureTime(count) + " ~ " + dtM.getArrivalTime(count) + " "
                    + dtM.getDuration(count), dtM.getDepartStation(count),
                    dtM.getArriveStation(count), dtM.getSeatClass(count), "$" + dtM.getConfirmPrice(count), dtM.getBagNum(count), dtM.getName(count)});
            tfTotalCost.setText(updatePrice);
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
