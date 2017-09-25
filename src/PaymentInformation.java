import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * Created by Joon on 2016-04-23.
 */
public class PaymentInformation extends JFrame implements ActionListener {

    private JTextField tfPassengerName;
    private JTextField tfCardNum;
    private JTextField tfCvv;
    private String expirationDate;
    private JButton btnSubmit;
    private JButton btnDelete;
    private JComboBox cbCard;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private JLabel noValid = new JLabel();
    private JFrame frame = new JFrame();
    private JDatePickerImpl datePicker;
    private DataTransfer dti;

    /** Registration UI */
    public PaymentInformation(DataTransfer dt) {
        dti = dt;
        this.setTitle("New User Registration");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        JLabel bAddCard = new JLabel("Add Card");
        gbAdd(bAddCard, 0, 0, 1, 1);
        JLabel bName = new JLabel("Name on Card : ");
        tfPassengerName = new JTextField(15);
        //passengerName.setText(dti.getName());
        gbAdd(bName, 0, 1, 1, 1);
        gbAdd(tfPassengerName, 1, 1, 1, 1);

        JLabel bCardNum = new JLabel("Card Number : ");
        tfCardNum = new JTextField(20);
        //cardNum.setText(dti.getCardNum());
        gbAdd(bCardNum, 0, 2, 1, 1);
        gbAdd(tfCardNum, 1, 2, 1, 1);

        JLabel bCVV = new JLabel("CVV : ");
        tfCvv = new JPasswordField(3);
        gbAdd(bCVV, 0, 3, 1, 1);
        gbAdd(tfCvv, 1, 3, 1, 1);

        JLabel bDate = new JLabel("Expiration Date : ");
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

        JPanel pSubmitBtn = new JPanel();
        btnSubmit = new JButton("Submit");
        pSubmitBtn.add(btnSubmit);
        gbAdd(pSubmitBtn, 1, 6, 1, 1);

        JLabel bLine = new JLabel("================================================================");
        JLabel bDelete = new JLabel("Delete Card");
        JLabel bCardNumber = new JLabel("Card Number");
        String[] arrCard = getPaymentInfo();
        cbCard = new JComboBox(arrCard);
        JPanel pCard = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pCard.add(cbCard);
        JPanel pDelete = new JPanel();
        btnDelete = new JButton("Delete");
        pDelete.add(btnDelete);
        gbAdd(bLine, 0, 8, 1, 1);
        gbAdd(bDelete, 0, 9, 1, 1);
        gbAdd(bCardNumber, 0, 10, 1, 1);
        gbAdd(pCard, 1, 10, 1, 1);
        gbAdd(pDelete, 1, 12, 1, 1);

        btnSubmit.addActionListener(this);
        btnDelete.addActionListener(this);

        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private String[] getPaymentInfo() {
        DBConnector dbc = new DBConnector();
        dbc.getCardInfo(dti);
        //System.out.println(dti.getCount2());
        java.util.List<String> temp = new ArrayList<String>();

        for (int j = 0; j < dti.getCount2(); j++) {
            String tmp = dti.getCardNum(j);
            temp.add(tmp.substring(tmp.length() - 4, tmp.length()));
        }
        String[] arr = new String[temp.size()];
        temp.toArray(arr);
        return arr;
    }

    /** gridbag Layout */
    private void gbAdd(JComponent c, int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gb.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }
    /**
     * @param args .
     */
    public static void main(String[] args) {
        new Registration();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DBConnector dbc = new DBConnector();
        int cardCount = dti.getCount2();
        String passName = tfPassengerName.getText();
        String cardNum = tfCardNum.getText();
        String cVVinput = tfCvv.getText();
        int cVV = 0;

        expirationDate = datePicker.getJFormattedTextField().getText();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        String today = fmt.format(current);
        int compare = expirationDate.compareTo(today);
        if (e.getSource() == btnSubmit) {
            if (passName.length() <= 0) {
                JOptionPane.showMessageDialog(this, "Need to write down Name.");
            } else if (cardNum.length() <= 15) {
                JOptionPane.showMessageDialog(this, "Need Valid Card Number.");
            } else if (cVVinput.length() <= 2) {
                JOptionPane.showMessageDialog(this, "Need Valid CVV Number");
            } else  if (compare < 0) {
                JOptionPane.showMessageDialog(this, "Date Expired");
            } else {
                cVV = Integer.parseInt(cVVinput);
                dti.setName(cardCount, passName);
                dti.setCardNum(cardCount, cardNum);
                dti.setCvv(cardCount, cVV);
                dti.setExpiration(cardCount, expirationDate);
                //cardCount++;
                dti.setCount2(cardCount);
                registerCard();
            }
        } else if (e.getSource() == btnDelete) {
            String cNum = cbCard.getSelectedItem().toString();
            String cNumber = null;
            System.out.println("deleting card number: " + cNum);
            for (int j = 0; j < dti.getCount2(); j++) {
                if (cNum.equals(dti.getCardNum(j).substring(12, 16))) {
                    cNumber = dti.getCardNum(j);
                }
            }
            deleteCard(cNumber);
        }
    }

    /** */
    private void registerCard() {
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.registerCard(dti);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Register Successful");
            new MakeReservation(dti);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Register Failed by some reason");
        }
    }

    private void deleteCard(String cNum) {
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.deleteCard(dti, cNum);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Deletion Successful");
            new MakeReservation(dti);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Card Deletion Failed by some reason");
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