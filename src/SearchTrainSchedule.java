import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-20.
 */
public class SearchTrainSchedule extends JFrame implements ActionListener {

    private JTextField tfTrainNum;
    private JButton btnSearch;
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private JLabel noValid = new JLabel();
    private JFrame frame = new JFrame();
    private String trainNum;
    private String userId;
    /**
     *
     */
    public SearchTrainSchedule(String id) {
        userId = id;
        this.setTitle("View Train Schedule");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        JLabel bTrainNum = new JLabel("Train Number : ");
        tfTrainNum = new JTextField(20);
        gbAdd(bTrainNum, 0, 1, 1, 1);
        gbAdd(tfTrainNum, 1, 1, 3, 1);

        JPanel bButton = new JPanel();
        btnSearch = new JButton("Search");
        bButton.add(btnSearch);
        gbAdd(bButton, 0, 5, 1, 1);

        btnSearch.addActionListener(this);

        setSize(350, 500);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

    /*
    public static void main(String[] args) {
        new Registration();
    }
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        //DBConnector dbc = new DBConnector();
        trainNum = tfTrainNum.getText();
        if (e.getSource() == btnSearch) {
            if (tfTrainNum.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "Not Valid Input");

            } else {
                viewTrainSchedule();
            }
        }
    }

    /** */
    private void viewTrainSchedule() {
        DataTransfer dt = getData();
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.viewTrainSchedule(dt);

        if (ok) {
            //JOptionPane.showMessageDialog(this, "");
            new ViewTrainSchedule(trainNum, userId);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong Input. Please Try Again.");
        }
    }

    /** */
    public DataTransfer getData() {
        DataTransfer dt = new DataTransfer();
        //String  = Login.username;
        String trainNum = tfTrainNum.getText();
        dt.setTrainNum(trainNum);

        return dt;
    }
}
