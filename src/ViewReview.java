import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-24.
 */
public class ViewReview extends JFrame implements ActionListener {
    private JButton btnBack;
    private JButton btnNext;
    private JTextField tfTrainNum;
    private DataTransfer dt = new DataTransfer();
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String nTrainNum;
    private int count;

    public ViewReview(String username) {
        this.setTitle("View Review");
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
        gbAdd(tfTrainNum, 1, 0, 1, 1);

        JPanel rBtn = new JPanel();
        btnBack = new JButton("Back");
        rBtn.add(btnBack);
        gbAdd(rBtn, 0, 3, 1, 1);

        JPanel nBtn = new JPanel();
        btnNext = new JButton("Next");
        nBtn.add(btnNext);
        gbAdd(nBtn, 2, 3, 1, 1);

        btnBack.addActionListener(this);
        btnNext.addActionListener(this);

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
        if (e.getSource() == btnNext) {
            nTrainNum =  tfTrainNum.getText();
            dt.setTrainNum(nTrainNum);
            boolean ok = getReview();
            if (ok) {
                new ViewReviewF(dt);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Wrong Input.");
            }
        }
        if (e.getSource() == btnBack) {
            new Functionality(dt.getUsername());
            dispose();
        }
    }

    private boolean getReview() {
        DBConnector dbc = new DBConnector();
        boolean ok = dbc.getReview(dt);
        return ok;
    }

}
