import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Joon on 2016-04-20.
 */
public class ViewTrainSchedule extends JFrame implements ActionListener {

    private Vector v;
    private Vector cols;
    private DefaultTableModel model;
    private JTable jTable;
    private JScrollPane pane;
    private JPanel pbtn;
    private JButton btnBack;
    private String trNum;
    private String userId;

    /**
     * @param trainNum tarin number
     */
    public ViewTrainSchedule(String trainNum, String id){
        super("View Train Schedule");
        userId = id;
        trNum = trainNum;

        DBConnector dbc = new DBConnector();
        v = dbc.getTableList(trNum);
        System.out.println("v="+ v);
        cols = getColumn();

        model = new DefaultTableModel(v, cols);

        jTable = new JTable(model);
        pane = new JScrollPane(jTable);
        add(pane);

        pbtn = new JPanel();
        btnBack = new JButton("Back");
        pbtn.add(btnBack);
        add(pbtn, BorderLayout.SOUTH);

        btnBack.addActionListener(this); //회원가입버튼 리스너 등록

        setSize(600, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     *
     * @return return
     */
    public Vector getColumn(){
        Vector col = new Vector();
        col.add("Train\n" + "(Train Number)");
        col.add("Arrival Time");
        col.add("Departure Time");
        col.add("Station");

        return col;
    }


    /*
    public static void main(String[] args) {
        new ViewTrainSchedule();
    }
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        //버튼을 클릭하면
        if (e.getSource() == btnBack) {
            new Functionality(userId);
            dispose();
        }
    }
}