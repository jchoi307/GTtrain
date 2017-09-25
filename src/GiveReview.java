import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-24.
 */
public class GiveReview extends JFrame implements ActionListener {
    private JButton btnSubmit;
    private JTextField tfTrainNum;
    private JTextField tfComment;
    private JComboBox cbRating;
    private DataTransfer dt = new DataTransfer();
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String nTrainNum;
    private String rate;
    private String comment;

    public GiveReview(String username) {
        this.setTitle("Give Review");
        dt.setUsername(username);
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel bTrainNum = new JLabel("Train Number");
        tfTrainNum = new JTextField(15);
        gbAdd(bTrainNum, 0, 0, 1, 1);
        gbAdd(tfTrainNum, 1, 0, 2, 1);

        JLabel bRating = new JLabel("Rating");
        String[] arrRating = {"Good", "Neutral", "Bad"};
        cbRating = new JComboBox(arrRating);
        JPanel pRating = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pRating.add(cbRating);
        gbAdd(bRating, 0, 1, 1, 1);
        gbAdd(pRating, 1, 1, 2, 1);

        JLabel bComment = new JLabel("Comment");
        tfComment = new JTextField(30);
        gbAdd(bComment, 0, 2, 1, 1);
        gbAdd(tfComment, 1, 2, 2, 1);

        JPanel sBtn = new JPanel();
        btnSubmit = new JButton("Submit");
        sBtn.add(btnSubmit);
        gbAdd(sBtn, 0, 3, 3, 1);

        btnSubmit.addActionListener(this);

        setSize(400, 300);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            nTrainNum =  tfTrainNum.getText();
            if (nTrainNum.length() < 1) {
                JOptionPane.showMessageDialog(this, "you have to fill out train number");
            } else {
                dt.setTrainNum(nTrainNum);
            }
            rate = cbRating.getSelectedItem().toString();
            dt.setRate(0, rate);
            comment = tfComment.getText();
            dt.setComment(0, comment);
            DBConnector dbc = new DBConnector();
            boolean ok = dbc.setReview(dt);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Review Submitted");
                new Functionality(dt.getUsername());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Train number is not available");
                System.out.println("Something wroooooooooooooong");
            }
        }
    }
}
