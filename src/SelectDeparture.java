import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class SelectDeparture extends JFrame implements ActionListener{
    private JPanel pbtn;
    private JButton btnBack;
    private JButton btnNext;
    private DataTransfer dtp;
    private int w;

    public SelectDeparture(DataTransfer dt) {
        super("View Train Schedule");
        dtp = dt;
        int i = dt.getCount();
        w = dt.getSelDepCount();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Train (Train Number)");
        model.addColumn("Time");
        model.addColumn("1st Class Price");
        model.addColumn("2nd Class Price");
        JTable table = new JTable(model);

        DefaultTableModel rowModel = (DefaultTableModel) table.getModel();
        for (int j = w; j < i; j++) {
            rowModel.addRow(new Object[]{dt.getTrainNums(j), dt.getDate(j) + " "
                    + dt.getDepartureTime(j) + " ~ " + dt.getArrivalTime(j) + " "
                    + dt.getDuration2(j), dt.getFirstPrice(j), dt.getSecondPrice(j)});
        }



        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("1st Class Price").setCellRenderer(buttonRenderer);
        table.getColumn("1st Class Price").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("2nd Class Price").setCellRenderer(buttonRenderer);
        table.getColumn("2nd Class Price").setCellEditor(new ButtonEditor(new JCheckBox()));


        pbtn = new JPanel();
        btnBack = new JButton("Back");
        pbtn.add(btnBack);
        add(pbtn, BorderLayout.SOUTH);

        btnBack.addActionListener(this);

        setSize(800, 600);
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
                int count = dtp.getCount();
                DataTransfer dtn = dtp;
                for (int j = 0; j < count; j++) {
                    if (label.equals(dtp.getFirstPrice(j))) {
                        //DataTransfer dtn = dtp;
                        dtn.setDepartureTime(w, dtp.getDepartureTime(j));
                        dtn.setArrivalTime(w, dtp.getArrivalTime(j));
                        dtn.setDuration(w, dtp.getDuration(j));
                        dtn.setTrainNums(w, dtp.getTrainNums(j));
                        dtn.setFirstPrice(w, dtp.getFirstPrice(j));
                        dtn.setSecondPrice(w, dtp.getSecondPrice(j));
                        dtn.setConfirmPrice(w, dtp.getFirstPrice(j));
                        dtn.setSeatClass(w, "1st Class");
                        //w++;
                        //dtn.setSelDepCount(w);
                        System.out.println("selectDeparture resNum: " + w);
                        //new TravelExtras(dtn);
                        //dispose();
                    } else if (label.equals(dtp.getSecondPrice(j))) {
                        //DataTransfer dtn = dtp;
                        dtn.setDepartureTime(w, dtp.getDepartureTime(j));
                        dtn.setArrivalTime(w, dtp.getArrivalTime(j));
                        dtn.setDuration(w, dtp.getDuration(j));
                        dtn.setTrainNums(w, dtp.getTrainNums(j));
                        dtn.setFirstPrice(w, dtp.getFirstPrice(j));
                        dtn.setSecondPrice(w, dtp.getSecondPrice(j));
                        dtn.setConfirmPrice(w, dtp.getSecondPrice(j));
                        dtn.setSeatClass(w, "2nd Class");
                        //w++;
                        //dtn.setSelDepCount(w);
                        //new TravelExtras(dtn);
                        //dispose();
                    }
                }
                w++;
                dtn.setSelDepCount(w);
                new TravelExtras(dtn);
                dispose();
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

    public static void main(String[] args) {
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new SearchTrain(dtp, w);
            dispose();
        }
    }
}
