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

public class ViewPopular extends JFrame implements ActionListener{
    private JButton btnBack;
    private DataTransfer dtM;
    private GridBagLayout gb;
    private GridBagConstraints gbc;

    public ViewPopular(DataTransfer dt) {
        this.setTitle("View Popular Route Report");
        dtM = dt;
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bCurrent = new JLabel("View Popular Route Report");
        gbAdd(bCurrent, 0, 0, 1, 1);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Month");
        model.addColumn("Train number");
        model.addColumn("#of Reservations");
        JTable table = new JTable(model);

        DefaultTableModel rowModel = (DefaultTableModel) table.getModel();

        String jan1 = dtM.getMonthlyPop1(0);
        String jan2 = dtM.getMonthlyPop1(1);
        String jan3 = dtM.getMonthlyPop1(2);
        rowModel.addRow(new Object[]{dtM.getMonth(0), jan1.substring(3, 15), jan1.substring(21, jan1.length())});
        rowModel.addRow(new Object[]{"", jan2.substring(3, 15), jan2.substring(21, jan2.length())});
        rowModel.addRow(new Object[]{"", jan3.substring(3, 15), jan3.substring(21, jan3.length())});
        String feb1 = dtM.getMonthlyPop2(0);
        String feb2 = dtM.getMonthlyPop2(1);
        String feb3 = dtM.getMonthlyPop2(2);
        rowModel.addRow(new Object[]{dtM.getMonth(1), feb1.substring(3, 15), feb1.substring(21, feb1.length())});
        rowModel.addRow(new Object[]{"", feb2.substring(3, 15), feb2.substring(21, feb2.length())});
        rowModel.addRow(new Object[]{"", feb3.substring(3, 15), feb3.substring(21, feb3.length())});
        String mar1 = dtM.getMonthlyPop3(0);
        String mar2 = dtM.getMonthlyPop3(1);
        String mar3 = dtM.getMonthlyPop3(2);
        rowModel.addRow(new Object[]{dtM.getMonth(2), mar1.substring(3, 15), mar1.substring(21, mar1.length())});
        rowModel.addRow(new Object[]{"", mar2.substring(3, 15), mar2.substring(21, mar2.length())});
        rowModel.addRow(new Object[]{"", mar3.substring(3, 15), mar3.substring(21, mar3.length())});


        JScrollPane scrollPane = new JScrollPane(table);
        gbAdd(scrollPane, 0, 1, 4, 1);


        JPanel bBtn = new JPanel();
        btnBack = new JButton("Back");
        bBtn.add(btnBack);
        gbAdd(bBtn, 0, 3, 4, 1);

        btnBack.addActionListener(this);

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
            new ManagerFunction(dtM.getUsername());
            dispose();
        }
    }
}
