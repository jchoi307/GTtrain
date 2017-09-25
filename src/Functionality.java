import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joon on 2016-04-20.
 */
public class Functionality extends JFrame implements ActionListener{
    private JButton btnSchedule;
    private JButton btnNewReserv;
    private JButton btnUpReserv;
    private JButton btnCanReserv;
    private JButton btnVreview;
    private JButton btnGreview;
    private JButton btnSchoolInfo;

    private GridBagLayout gb;
    private GridBagConstraints gbc;
    private String id;

    /**
     *
     * @param username user id
     */
    public Functionality(String username){
        id = username;
        this.setTitle("Choose Functionality");
        gb = new GridBagLayout();
        setLayout(gb);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;


        JPanel pButton = new JPanel();
        btnSchedule = new JButton("View Train Schedule");
        JPanel qButton = new JPanel();
        btnNewReserv = new JButton("Make a new Reservation");
        JPanel rButton = new JPanel();
        btnUpReserv = new JButton("Update a Reservation");
        JPanel sButton = new JPanel();
        btnCanReserv = new JButton("Cancel a Reservation");
        JPanel tButton = new JPanel();
        btnVreview = new JButton("View Review");
        JPanel uButton = new JPanel();
        btnGreview = new JButton("Give Review");
        JPanel vButton = new JPanel();
        btnSchoolInfo = new JButton("Add School Information (Student Discount)");
        pButton.add(btnSchedule);
        qButton.add(btnNewReserv);
        rButton.add(btnUpReserv);
        sButton.add(btnCanReserv);
        tButton.add(btnVreview);
        uButton.add(btnGreview);
        vButton.add(btnSchoolInfo);
        gbAdd(pButton, 0, 1, 4, 1);
        gbAdd(qButton, 0, 2, 4, 1);
        gbAdd(rButton, 0, 3, 4, 1);
        gbAdd(sButton, 0, 4, 4, 1);
        gbAdd(tButton, 0, 5, 4, 1);
        gbAdd(uButton, 0, 6, 4, 1);
        gbAdd(vButton, 0, 7, 4, 1);

        btnSchedule.addActionListener(this);
        btnNewReserv.addActionListener(this);
        btnUpReserv.addActionListener(this);
        btnCanReserv.addActionListener(this);
        btnVreview.addActionListener(this);
        btnGreview.addActionListener(this);
        btnSchoolInfo.addActionListener(this);

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

    /*
    public static void main(String[] args) {
    }
    */


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSchedule){
            new SearchTrainSchedule(id);
            this.dispose();

        } else if (ae.getSource() == btnNewReserv) {
            DataTransfer dt = new DataTransfer();
            dt.setUsername(id);
            new SearchTrain(dt, 0);
            this.dispose();

        } else if (ae.getSource() == btnUpReserv) {
            new UpdateReservation(id);
            this.dispose();

        } else if (ae.getSource() == btnCanReserv) {
            new CancelReservation(id);
            this.dispose();

        } else if (ae.getSource() == btnVreview) {
            new ViewReview(id);
            this.dispose();

        } else if (ae.getSource() == btnGreview) {
            new GiveReview(id);
            this.dispose();

        } else if (ae.getSource() == btnSchoolInfo) {
            new SchoolInfo(id);
            System.out.println("school info");
            //this.dispose();
        }
    }

}
