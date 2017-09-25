import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MakeReservation extends JFrame implements ActionListener{
    private JButton btnBack;
    private JButton btnSubmit;
    private JTextField tfCost;
    private JComboBox cbCard;
    private JButton addCard;
    private JButton addTrain;
    private DataTransfer dtM;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String totalCost;
    private int count;

    public MakeReservation(DataTransfer dt) {
        this.setTitle("Make Reservation");
        dtM = dt;
        count = dt.getSelDepCount();
        System.out.println("Last resNum: " + count);
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bCurrent = new JLabel("Currently Selected");
        gbAdd(bCurrent, 0, 0, 1, 1);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Train (Train Number)");
        model.addColumn("Time");
        model.addColumn("Departs From");
        model.addColumn("Arrives At");
        model.addColumn("Class");
        model.addColumn("Price");
        model.addColumn("# of Baggage");
        model.addColumn("Passenger Name");
        model.addColumn("Remove");
        JTable table = new JTable(model);

        DefaultTableModel rowModel = (DefaultTableModel) table.getModel();
        for (int j = 0; j < count; j++) {
            if (dtM.getTrainNums(j) != null) {
                // Tried "<html>" + dtM.getDate(j) + "<br> " + dtM.getDepartureTime(j) + " ~ " + dtM.getArrivalTime(j) + " <br> " + dtM.getDuration(j) + "</html>"
                // to make new line inside the table cell,
                // but the table cell doesn't expand it's height, so maybe i have to make new table model to improve this.

                rowModel.addRow(new Object[]{dtM.getTrainNums(j), dtM.getDate(j) + " "
                        + dtM.getDepartureTime(j) + " ~ " + dtM.getArrivalTime(j) + " "
                        + dtM.getDuration(j), dtM.getDepartStation(j),
                        dtM.getArriveStation(j), dtM.getSeatClass(j), "$"
                        + dtM.getConfirmPrice(j), dtM.getBagNum(j), dtM.getName(j), j});
            }
        }
        JScrollPane scrollPane = new JScrollPane(table);
        gbAdd(scrollPane, 0, 1, 4, 1);

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Remove").setCellRenderer(buttonRenderer);
        table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox()));

        //
        // School E-mail Confirm
        getCustomerInfo();
        JLabel bDiscount = new JLabel("Not a Student.");
        if (dt.getIsStudent() == 1) {
            bDiscount = new JLabel("Student Discount Applied.");
        }
        gbAdd(bDiscount, 0, 2, 1, 1);

        JLabel bCost = new JLabel("Total Cost");
        tfCost = new JTextField(15);
        gbAdd(bCost, 0, 3, 1, 1);
        gbAdd(tfCost, 1, 3, 3, 1);
        tfCost.setEditable(false);
        //
        // Number of Bag Calculation
        // School E-mail Calculation
        double tempCost = 0;
        for (int j = 0; j < count; j++) {
            if (dtM.getTrainNums(j) != null) {
                tempCost += Double.parseDouble(dtM.getConfirmPrice(j));
            }
        }
        //double tempCost = Double.parseDouble(dtM.getConfirmPrice());
        double costCalc = 0;

        for (int j = 0; j < count; j++) {
            if (dtM.getTrainNums(j) != null) {
                int numOfBag = 0;
                if (dt.getBagNum(j) != null) {
                    numOfBag = Integer.parseInt(dt.getBagNum(j));
                }
                if (numOfBag > 2) {
                    tempCost += ((numOfBag - 2) * 30);
                }
            }
        }
        if (dt.getIsStudent() == 1) {
            costCalc = tempCost * 0.8;
        } else {
            costCalc = tempCost;
        }
        totalCost = String.format("%.2f", costCalc);
        tfCost.setText(totalCost);
        JLabel bCard = new JLabel("Use Card");
        String[] stringArray = getPaymentInfo();
        cbCard = new JComboBox(stringArray);
        JPanel pCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCard.add(cbCard);
        JPanel cBtn = new JPanel();
        addCard = new JButton("Add Card");
        cBtn.add(addCard);
        gbAdd(bCard, 0, 4, 1, 1);
        gbAdd(pCard, 1, 4, 1, 1);
        gbAdd(cBtn, 2, 4, 1, 1);

        JPanel anBtn = new JPanel();
        addTrain = new JButton("Continue Adding a Train");
        anBtn.add(addTrain);
        gbAdd(anBtn, 0, 5, 1, 1);

        JPanel pbtn = new JPanel();
        btnBack = new JButton("Back");
        pbtn.add(btnBack);
        gbAdd(pbtn, 0, 10, 1, 1);

        JPanel sBtn = new JPanel();
        btnSubmit = new JButton("Submit");
        sBtn.add(btnSubmit);
        gbAdd(sBtn, 3, 10, 1, 1);

        addCard.addActionListener(this);
        addTrain.addActionListener(this);
        btnBack.addActionListener(this);
        btnSubmit.addActionListener(this);

        setSize(1200, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    private String[] getPaymentInfo() {
        DBConnector dbc = new DBConnector();
        dbc.getCardInfo(dtM);
        System.out.println(dtM.getCount2());
        List<String> temp = new ArrayList<String>();
        System.out.println("Getting Card Info");
        for (int j = 0; j < dtM.getCount2(); j++) {
            String tmp = dtM.getCardNum(j);
            temp.add(tmp.substring(tmp.length() - 4, tmp.length()));
        }
        String[] arr = new String[temp.size()];
        temp.toArray(arr);
        return arr;
    }

    private void getCustomerInfo() {
        DBConnector dbc = new DBConnector();
        dbc.getCustomerInfo(dtM.getUsername());
    }


    private static class JTableButtonRenderer extends JButton implements TableCellRenderer {
        public JTableButtonRenderer() {
            setOpaque(true);
        }
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    public class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String    label;
        private boolean   isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else{
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed)  {
                int a = Integer.parseInt(label);
                deleteRow(a);
                dtM.setSelDepCount(count);
                new MakeReservation(dtM);
                dispose();
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    private void deleteRow(int i) {
        dtM.setTrainNums(i, null);
        dtM.setDate(i, null);
        dtM.setDepartureTime(i, null);
        dtM.setArrivalTime(i, null);
        dtM.setDuration(i, null);
        dtM.setDepartStation(i, null);
        dtM.setArriveStation(i, null);
        dtM.setSeatClass(i, null);
        dtM.setConfirmPrice(i, null);
        dtM.setBagNum(i, null);
        dtM.setName(i, null);
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
            new SearchTrain(dtM,  count - 1);
            dispose();
        }
        if (e.getSource() == addCard) {
            new PaymentInformation(dtM);
            dispose();
        }
        if (e.getSource() == addTrain) {
            new SearchTrain(dtM, count);
            dispose();
        }
        if (e.getSource() == btnSubmit) {
            dtM.setConfirmPrice((count - 1), totalCost);
            String cNum = "";
            if (cbCard.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "You need payment info.");
            } else {
                cNum = cbCard.getSelectedItem().toString();
            }
            String cNumber = null;
            System.out.println("getting cardnumber: " + cNum);
            if (cNum.length() < 4) {
                JOptionPane.showMessageDialog(this, "You need vaild cardNum.");
            } else {
                for (int j = 0; j < dtM.getCount2(); j++) {
                    if (cNum.equals(dtM.getCardNum(j).substring(12, 16))) {
                        cNumber = dtM.getCardNum(j);
                    }
                }
                dtM.setConfirmCardNum(cNumber);
                new Confirmation(dtM);
                dispose();
            }
        }
    }
}
