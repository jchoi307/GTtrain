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

public class ViewReviewF extends JFrame implements ActionListener{
    private JButton btnBack;
    private DataTransfer dtM;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private int count;

    public ViewReviewF(DataTransfer dt) {
        count = dt.getCount();
        this.setTitle("View Review");
        dtM = dt;
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bCurrent = new JLabel("View Review");
        gbAdd(bCurrent, 0, 0, 1, 1);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Rating");
        model.addColumn("Comment");
        JTable table = new JTable(model);

        DefaultTableModel rowModel = (DefaultTableModel) table.getModel();
        for (int j = 0; j < count; j++) {
            rowModel.addRow(new Object[]{dtM.getComment(j), dtM.getRate(j)});
        }
        JScrollPane scrollPane = new JScrollPane(table);
        gbAdd(scrollPane, 0, 1, 4, 1);


        JPanel bBtn = new JPanel();
        btnBack = new JButton("Back to Choose Functionality");
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
            new Functionality(dtM.getUsername());
            dispose();
        }
    }
}
