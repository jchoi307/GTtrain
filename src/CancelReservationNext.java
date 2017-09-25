import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class CancelReservationNext extends JFrame implements ActionListener{
    private JButton btnBack;
    private JButton btnNext;
    private JTextField tfTotalCost;
    private JTextField tfDateofC;
    private JTextField tfRefundAmount;
    private DataTransfer dtM;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private int count;
    private long diff;
    private long diffDays;
    private double beforeRefund = 0;
    private double refundAmount;
    private String refunds;
    private int alreadyCanceled;

    public CancelReservationNext(DataTransfer dt, int confirm) {
        this.setTitle("Cancel Reservation");
        dtM = dt;
        count = dt.getReserveCount();
        alreadyCanceled = confirm;
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bCurrent = new JLabel("Cancel Reservation");
        gbAdd(bCurrent, 0, 0, 4, 1);

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

        System.out.println(count);
        DefaultTableModel rowModel = (DefaultTableModel) table.getModel();
        for (int j = 0; j < count; j++) {
            rowModel.addRow(new Object[]{dtM.getTrainNums(j), dtM.getDate(j) + " "
                    + dtM.getDepartureTime(j) + " ~ " + dtM.getArrivalTime(j) + " "
                    + dtM.getDuration(j), dtM.getDepartStation(j),
                    dtM.getArriveStation(j), dtM.getSeatClass(j), "$" + dtM.getConfirmPrice(j), dtM.getBagNum(j), dtM.getName(j)});
        }
        JScrollPane scrollPane = new JScrollPane(table);
        gbAdd(scrollPane, 0, 1, 4, 1);

        JLabel bTotalCost = new JLabel("Total Cost of Reservation");
        tfTotalCost = new JTextField(10);
        double temp = 0;
        for (int j = 0; j < count; j++) {
            if (dtM.getTrainNums(j) != null) {
                temp += Double.parseDouble(dtM.getConfirmPrice(j));
            }
        }
        String cCost = String.format("%.2f", temp);
        tfTotalCost.setText(cCost);
        tfTotalCost.setEditable(false);
        gbAdd(bTotalCost, 0, 3, 1, 1);
        gbAdd(tfTotalCost, 1, 3, 1, 1);

        JLabel bDateofC = new JLabel("Date of Cancellation");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        String today = fmt.format(current);
        tfDateofC = new JTextField(20);
        if (alreadyCanceled == 0) {
            tfDateofC.setText(today);
        } else {
            tfDateofC.setText("Already Canceled");
        }
        tfDateofC.setEditable(false);
        gbAdd(bDateofC, 0, 4, 1, 1);
        gbAdd(tfDateofC, 1, 4, 1, 1);

        JLabel bRefundAmount = new JLabel("Amount of Refunded");
        for (int j = 0; j < count; j++) {
            if (dtM.getTrainNums(j) != null) {
                beforeRefund += Double.parseDouble(dtM.getConfirmPrice(j));
            }
        }

        try {
            Date dateReserved = fmt.parse(dtM.getDate(0));
            diff = dateReserved.getTime() - current.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (diffDays >= 8) {
            if (beforeRefund * 0.8 > 50) {
                refundAmount = beforeRefund * 0.8 - 50;
            } else {
                refundAmount = 0;
            }
            refunds = String.format("%.2f", refundAmount);
        } else if ((1 <= diffDays) && (diffDays < 8)) {
            if (beforeRefund * 0.5 > 50) {
                refundAmount = beforeRefund * 0.5 - 50;
            } else {
                refundAmount = 0;
            }
            refunds = String.format("%.2f", refundAmount);
        } else {
            refunds = "Cannot Refund.";
        }
        tfRefundAmount = new JTextField(20);
        if (alreadyCanceled == 0) {
            tfRefundAmount.setText(refunds);
        } else {
            tfDateofC.setText("Already Canceled");
        }
        tfRefundAmount.setEditable(false);
        gbAdd(bRefundAmount, 0, 5, 1, 1);
        gbAdd(tfRefundAmount, 1, 5, 1, 1);

        JPanel pbtn = new JPanel();
        btnBack = new JButton("Back");
        pbtn.add(btnBack);
        gbAdd(pbtn, 0, 6, 1, 1);

        JPanel nBtn = new JPanel();
        btnNext = new JButton("Submit");
        nBtn.add(btnNext);
        gbAdd(nBtn, 2, 6, 1, 1);

        btnBack.addActionListener(this);
        btnNext.addActionListener(this);

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
            new CancelReservation(dtM.getUsername());
            dispose();
        }
        if (e.getSource() == btnNext) {
            DBConnector dbc = new DBConnector();
            if (alreadyCanceled == 0) {
                System.out.println(diffDays);
                //if (!(diffDays > 1)) {
                    double leftOver = beforeRefund - refundAmount;
                    String total = String.format("%.2f", leftOver);
                    dtM.setConfirmPrice(0, total);
                    dbc.updateTotalPrice(dtM);
                    dbc.updateIsCanceled(dtM);
                    JOptionPane.showMessageDialog(this, "Cancellation Successful");
                    new Functionality(dtM.getUsername());
                    dispose();
                //} else {
                    //JOptionPane.showMessageDialog(this, "Cannot Cancel. Already Past.");
                //}
            } else {
                JOptionPane.showMessageDialog(this, "Already Canceled. Back to the menu");
                new Functionality(dtM.getUsername());
                dispose();
            }
        }
    }
}
