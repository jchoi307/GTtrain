import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class UpdateReservationNext extends JFrame implements ActionListener{
    private JButton btnBack;
    private JButton btnNext;
    private DataTransfer dtM;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private int count;

    public UpdateReservationNext(DataTransfer dt) {
        this.setTitle("Update Reservation");
        dtM = dt;
        count = dt.getReserveCount();
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bCurrent = new JLabel("Update Reservation");
        gbAdd(bCurrent, 0, 0, 1, 1);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Select");
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
        for (int j = 0; j < count; j++) {
            rowModel.addRow(new Object[]{j, dtM.getTrainNums(j), dtM.getDate(j) + " "
                    + dtM.getDepartureTime(j) + " ~ " + dtM.getArrivalTime(j) + " "
                    + dtM.getDuration(j), dtM.getDepartStation(j),
                    dtM.getArriveStation(j), dtM.getSeatClass(j), "$" + dtM.getConfirmPrice(j), dtM.getBagNum(j), dtM.getName(j)});
        }
        JScrollPane scrollPane = new JScrollPane(table);
        gbAdd(scrollPane, 0, 1, 4, 1);

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Select").setCellRenderer(buttonRenderer);
        table.getColumn("Select").setCellEditor(new ButtonEditor(new JCheckBox()));

        JPanel pbtn = new JPanel();
        btnBack = new JButton("Back");
        pbtn.add(btnBack);
        gbAdd(pbtn, 0, 10, 1, 1);

        JPanel nBtn = new JPanel();
        btnNext = new JButton("Next");
        nBtn.add(btnNext);
        gbAdd(nBtn, 3, 10, 1, 1);

        btnBack.addActionListener(this);
        btnNext.addActionListener(this);

        setSize(1200, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                for (int j = 0; j < count; j++) {
                    if (Integer.parseInt(label) == j) {
                        new UpdateReservationF(dtM, j);
                        dispose();
                    }
                }
            }
            isPushed = false;
            return new String(label) ;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
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
        if (e.getSource() == btnNext) {
            JOptionPane.showMessageDialog(this, "Click Number besides on Table instead.");
        }
    }
}
