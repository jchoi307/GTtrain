import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-20.
 */
public class ManagerFunction extends JFrame implements ActionListener{
    private JButton btnRreport;
    private JButton btnPreport;
    private DataTransfer dt = new DataTransfer();
    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String id;

    /**
     *
     * @param username user id
     */
    public ManagerFunction(String username) {
        dt.setUsername(username);
        this.setTitle("Choose Functionality");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel pButton = new JPanel();
        btnRreport = new JButton("View Revenue Report");
        JPanel qButton = new JPanel();
        btnPreport = new JButton("View Popular route Report");
        pButton.add(btnRreport);
        qButton.add(btnPreport);
        gbAdd(pButton, 0, 2, 4, 1);
        gbAdd(qButton, 0, 3, 4, 1);

        btnRreport.addActionListener(this);
        btnPreport.addActionListener(this);

        setSize(350, 500);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     *
     * @param c
     * @param x
     * @param y
     * @param w
     * @param h
     */
    private void gbAdd(JComponent c, int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gb.setConstraints(c, gbc);
        gbc.insets = new Insets(2, 2, 2, 2);
        add(c, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnRreport){
            getRevenue();
            new ViewRevenue(dt);
            this.dispose();
        } else if (ae.getSource() == btnPreport) {
            getPopularity();
            new ViewPopular(dt);
            this.dispose();
        }
    }

    private void getRevenue() {
        DBConnector dbc = new DBConnector();
        dbc.getRevenue(dt);
    }

    private void getPopularity() {
        DBConnector dbc = new DBConnector();
        dbc.getPopularity(dt);
    }
}
