/**
 * Created by Joon on 2016-04-14.
 */
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBConnector {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Team_84?autoReconnect=true";
    private static final String USER = "cs4400_Team_84";
    private static final String PASS = "PZWSZLsT";

    /** connect to DB */
    public Connection getConn() {
        Connection con = null;
        try {
            Class.forName(DRIVER).newInstance();
            con = DriverManager.getConnection(URL, USER, PASS);
            if (!con.isClosed())
                System.out.println("Successfully connected to " +
                        "MySQL server using TCP/IP...");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return con;
    }

    /**@param username  username */
    public DataTransfer getCustomerInfo(String username) {
        DataTransfer dt = new DataTransfer();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConn();
            String sql = "SELECT * FROM CUSTOMER where Username=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                dt.setUsername(rs.getString("Username"));
                dt.setPwd(rs.getString("Password"));
                dt.setIsStudent(rs.getInt("isStudent"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return dt;
    }

    public boolean isUserExist(String username) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM CUSTOMER WHERE Username=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("User exist");
                ok = true;
            } else {
                System.out.println("no user exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public boolean isEmailExist(String email) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM CUSTOMER WHERE Email=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Email exist");
                ok = true;
            } else {
                System.out.println("Email can be used");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    /** Register Customer
     * @param dt getter/setter
     * */
    public boolean registerCustomer(DataTransfer dt) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "insert into CUSTOMER (Username, isStudent, Email, Password) VALUES(?, 0, ?, ?)";
            ps = con.prepareStatement(sql);
            System.out.println(dt.getUsername());
            System.out.println(dt.getEmail());
            System.out.println(dt.getPwd());
            System.out.println("ccc");
            ps.setString(1, dt.getUsername());
            ps.setString(2, dt.getEmail());
            ps.setString(3, dt.getPwd());
            System.out.println("bbb");
            int r = 0;
            r = ps.executeUpdate();
            System.out.println("testing3");
            if (r > 0) {
                System.out.println("Register Successful");
                ok = true;
            } else {
                System.out.println("Register Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;

    }

    /**
     *
     * @param dt getter/setter
     * @return a
     */
    public boolean submitEmail(DataTransfer dt) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "update CUSTOMER set isStudent=? where Username=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, "1");
            ps.setString(2, dt.getUsername());
            int r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("Student Email Update Successful");
                ok = true;
            } else {
                System.out.println("Update Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    /**
     *
     * @param dt getter/setter
     * @return return
     */
    public boolean viewTrainSchedule(DataTransfer dt) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM STOP where TrainNumber=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getTrainNum());
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("TrainNumber").equals(dt.getTrainNum())) {
                    System.out.println("Train available");
                    ok = true;
                }
            } else {
                System.out.println("getting result Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    /**
     * @param trNum train Number
     * @return return
     */
    public Vector getTableList(String trNum){

        Vector data = new Vector();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{

            con = getConn();
            String sql = "select * from STOP where TrainNumber=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, trNum);
            rs = ps.executeQuery();


            while (rs.next()){
                String trainNumber = rs.getString("TrainNumber");
                String arrivalTime = rs.getString("ArrivalTime");
                String departureTime = rs.getString("DepartureTime");
                String stationName = rs.getString("StationName");
                Vector row = new Vector();
                row.add(trainNumber);
                row.add(arrivalTime);
                row.add(departureTime);
                row.add(stationName);
                data.add(row);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return data;
    }

    public boolean selectDepart(DataTransfer dt, int i) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM STOP WHERE DepartureTime IS NOT NULL AND StationName=?";
            String depart = dt.getDepartStation(i);
            String arrive = dt.getArriveStation(i);
            String date = dt.getDate(i);
            String departSub = depart.substring(depart.length() - 4, depart.length() - 1);
            ps = con.prepareStatement(sql);
            ps.setString(1, departSub);
            rs = ps.executeQuery();
            while (rs.next()) {
                dt.setTrainNums(i, rs.getString("TrainNumber"));
                dt.setDepartureTime(i, rs.getTime("DepartureTime"));
                dt.setDate(i, date);
                dt.setDepartStation(i, depart);
                dt.setArriveStation(i, arrive);
                getArrival(dt, i);
                getDuration(dt, i);
                getPrice(dt, i);
                ok = true;
                i++;
            }
            if (ok == false){
                System.out.println("Update Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        dt.setCount(i);
        return ok;
    }

    /**
     *
     * @param dt
     * @param i
     */
    private void getArrival(DataTransfer dt, int i) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rss = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM STOP WHERE ArrivalTime IS NOT NULL AND StationName=? AND TrainNumber=?";
            String arrive = dt.getArriveStation(i);
            String arriveSub = arrive.substring(arrive.length() - 4, arrive.length() - 1);
            ps = con.prepareStatement(sql);
            ps.setString(1, arriveSub);
            ps.setString(2, dt.getTrainNums(i));
            rss = ps.executeQuery();
            if (rss.next()) {
                dt.setArrivalTime(i, rss.getTime("ArrivalTime"));
                ok = true;
            }
            if (!ok){
                System.out.println("get arrival Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        //return ok;
    }

    /**
     *
     * @param dt
     * @param i
     */
    private void getDuration(DataTransfer dt, int i) throws Exception {
        //long c = dt.getArrivalTime(i).getTime() - dt.getDepartureTime(i).getTime();
        //Time diff = new Time(c);
        String arrive = dt.getArrivalTime(i).toString();
        String depart = dt.getDepartureTime(i).toString();
        String returnTime = getElapsedTime2(depart, arrive, null);
        dt.setDuration2(i, returnTime);
    }

    String getElapsedTime2(final String beginTime, final String endTime, String dateFormatStr) throws Exception {
        if (dateFormatStr == null) dateFormatStr = "HH:mm:ss";
        Calendar calendar = Calendar.getInstance();
        DateFormat stringFormat = new SimpleDateFormat(dateFormatStr);

        //long beginTime = 0;
        try {
            Date beginDate = stringFormat.parse(beginTime);
            Date endDate = stringFormat.parse(endTime);

            long gap = (endDate.getTime() - beginDate.getTime()) / 1000;

            long hourGap = gap / 60 / 60;
            long remainder = ((long) (gap / 60)) % 60;
            long minuteGap = remainder;
            long secondGap = gap % 60;

            if (hourGap > 99) {
                hourGap = (long) 99;
            }

            String returnTime = hourGap + "hrs " + minuteGap + "Min";

            return returnTime;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     *
     * @param dt
     */
    public void getPrice(DataTransfer dt, int i) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rsp = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM TRAINROUTE WHERE TrainNumber=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getTrainNums(i));
            rsp = ps.executeQuery();
            if (rsp.next()) {
                dt.setFirstPrice(i, rsp.getString("1stClassPrice"));
                dt.setSecondPrice(i, rsp.getString("2ndClassPrice"));
                ok = true;
            }
            if (ok == false){
                System.out.println("get price Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
    }

    public void getCardInfo(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int i = 0;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            con = getConn();
            String sql = "SELECT * FROM PAYMENTINFO WHERE PaymentUsername=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getUsername());
            rs = ps.executeQuery();
            while (rs.next()) {
                dt.setCardNum(i, rs.getString("CardNumber"));
                dt.setCvv(i, rs.getInt("CVV"));
                String tDate = fmt.format(rs.getDate("Expiration"));
                dt.setExpiration(i, tDate);
                ok = true;
                i++;
            }
            if (ok == false){
                System.out.println("get price Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        dt.setCount2(i);
    }

    public boolean registerCard(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        int cardCount = dt.getCount2();
        try {
            con = getConn();
            String sql = "insert into PAYMENTINFO (CardNumber, Expiration, CVV, NameOnCard, PaymentUsername) VALUES(?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            //System.out.println(dt.getCardNum(cardCount));
            //System.out.println(dt.getExpiration(cardCount));
            //System.out.println(dt.getCvv(cardCount));
            //System.out.println(dt.getName(cardCount));
            //System.out.println(dt.getUsername());
            ps.setString(1, dt.getCardNum(cardCount));
            ps.setString(2, dt.getExpiration(cardCount));
            ps.setInt(3, dt.getCvv(cardCount));
            ps.setString(4, dt.getName(cardCount));
            ps.setString(5, dt.getUsername());

            int r = 0;
            r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("Register Successful");
                cardCount++;
                dt.setCount2(cardCount);
                ok = true;
            } else {
                System.out.println("Register Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public boolean deleteCard(DataTransfer dt, String cNum) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        int cardCount = dt.getCount2();
        try {
            con = getConn();
            String sql = "DELETE FROM PAYMENTINFO WHERE CardNumber=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, cNum);
            int r = 0;
            r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("Card Deletion Successful");
                cardCount--;
                dt.setCount2(cardCount);
                ok = true;
            } else {
                System.out.println("Register Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public boolean makeReservation(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        int count = dt.getSelDepCount();
        try {
            con = getConn();
            String sql = "insert into RESERVES (Class, DepartureDate, PassengerName, NumberofBags, " +
                    "DepartsFrom, ArrivesAt, ReservationID, TrainNumber, ArrivalTime, " +
                    "DepartureTime, TotalPrice) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            int r = 0;
            for (int j = 0; j < count; j++) {
                ps.setString(1, dt.getSeatClass(j));
                ps.setString(2, dt.getDate(j));
                ps.setString(3, dt.getName(j));
                ps.setString(4, dt.getBagNum(j));
                ps.setString(5, dt.getDepartStation(j));
                ps.setString(6, dt.getArriveStation(j));
                ps.setString(7, dt.getReservationID());
                ps.setString(8, dt.getTrainNums(j));
                ps.setTime(9, dt.getArrivalTime(j));
                ps.setTime(10, dt.getDepartureTime(j));
                ps.setString(11, dt.getConfirmPrice(j));

                r = ps.executeUpdate();
            }
            if (r > 0) {
                System.out.println("Reserves Successful");
                ok = true;
            } else {
                System.out.println("Reserves Register Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public void setReservation(DataTransfer dt) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String reservationID = null;
        try {
            con = getConn();
            String sql = "insert into RESERVATION (IsCanceled, CardNumber, Username) VALUES(0, ?, ?)";
            ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, dt.getConfirmCardNum());
            ps.setString(2, dt.getUsername());

            int r = 0;
            r = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (r > 0) {
                if (rs.next()) {
                    int newReservationID = (int) rs.getInt(1);
                    reservationID = String.valueOf(newReservationID);
                }
                System.out.println("Reservation Successful");
                dt.setReservationID(reservationID);
            } else {
                System.out.println("Reservation Register Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
    }

    public void checkIsCanceled(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM RESERVATION WHERE ReservationID=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getReservationID());
            rs = ps.executeQuery();
            if (rs.next()) {
                dt.setIsCanceled(rs.getInt("IsCanceled"));
                System.out.println("Getting Reserves Successful");
                ok = true;
            }
            if (!ok){
                System.out.println("Getting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
    }

    public void updateIsCanceled(DataTransfer dt) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConn();
            String sql = "update RESERVATION set isCanceled=1 where ReservationID=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getReservationID());
            int r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("Update Cancellation Successful");
            } else {
                System.out.println("Update Cancellation Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
    }

    public boolean reservedUserCheck(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM RESERVATION WHERE ReservationID=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getReservationID());
            rs = ps.executeQuery();
            if (rs.next()) {
                if (dt.getUsername().equals(rs.getString("Username"))) {
                    ok = true;
                }
            }
            if (!ok){
                System.out.println("Username doesn't match");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public boolean getReservation(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = getConn();
            String sql = "SELECT * FROM RESERVES WHERE ReservationID=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getReservationID());
            rs = ps.executeQuery();
            while (rs.next()) {
                dt.setSeatClass(count, rs.getString("Class"));
                dt.setDate(count, rs.getString("DepartureDate"));
                dt.setName(count, rs.getString("PassengerName"));
                dt.setBagNum(count, rs.getString("NumberofBags"));
                dt.setDepartStation(count, rs.getString("DepartsFrom"));
                dt.setArriveStation(count, rs.getString("ArrivesAt"));
                dt.setTrainNums(count, rs.getString("TrainNumber"));
                dt.setArrivalTime(count, rs.getTime("ArrivalTime"));
                dt.setDepartureTime(count, rs.getTime("DepartureTime"));
                dt.setConfirmPrice(count, rs.getString("TotalPrice"));
                System.out.println("Getting Reserves Successful");
                count++;
                ok = true;
            }
            if (!ok){
                System.out.println("Getting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        dt.setReserveCount(count);
        return ok;
    }

    public boolean updateReserve(DataTransfer dt) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "update RESERVES set DepartureDate=? where ReservationID=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getNewDate());
            ps.setString(2, dt.getReservationID());
            int r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("New Date updated Successful");
                ok = true;
            } else {
                System.out.println("New Date Update Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public void updateTotalPrice(DataTransfer dt) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConn();
            String sql = "update RESERVES set TotalPrice=? where ReservationID=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getConfirmPrice(0));
            ps.setString(2, dt.getReservationID());
            int r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("total price updated Successful");
            } else {
                System.out.println("total price Update Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
    }

    public boolean getReview(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = getConn();
            String sql = "SELECT * FROM REVIEW WHERE TrainNumber=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getTrainNum());
            rs = ps.executeQuery();
            while (rs.next()) {
                String comment = rs.getString("COMMENT");
                dt.setComment(count, comment);
                String rate = rs.getString("Rating");
                dt.setRate(count, rate);
                count++;
                ok = true;
            }
            if (!ok){
                System.out.println("Getting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        dt.setCount(count);
        return ok;
    }

    public boolean setReview(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConn();
            String sql = "INSERT INTO REVIEW (COMMENT, Rating, Username, TrainNumber) VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, dt.getComment(0));
            ps.setString(2, dt.getRate(0));
            ps.setString(3, dt.getUsername());
            ps.setString(4, dt.getTrainNum());
            int r = ps.executeUpdate();
            if (r > 0) {
                ok = true;
            }
            if (!ok){
                System.out.println("Setting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public void getRevenue(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int jan = 0;
        int feb = 0;
        int mar = 0;
        double totalJan = 0;
        double totalFeb = 0;
        double totalMar = 0;
        try {
            con = getConn();
            String sql = "SELECT * FROM RESERVES";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                cal.setTime(rs.getDate("DepartureDate"));
                double tempPrice = Double.parseDouble(rs.getString("TotalPrice"));
                int month = cal.get(Calendar.MONTH);
                if (month == 0) {
                    jan++;
                    totalJan += tempPrice;
                } else if (month == 1) {
                    feb++;
                    totalFeb += tempPrice;
                } else if (month == 2) {
                    mar++;
                    totalMar += tempPrice;
                }
                ok = true;
            }
            if (!ok){
                System.out.println("Getting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        String totJan = String.format("%.2f", totalJan);
        String totFeb = String.format("%.2f", totalFeb);
        String totMar = String.format("%.2f", totalMar);

        dt.setMonth(0, "January");
        dt.setMonthCount(0, jan);
        dt.setRevenue(0, totJan);
        dt.setMonth(1, "February");
        dt.setMonthCount(1, feb);
        dt.setRevenue(1, totFeb);
        dt.setMonth(2, "March");
        dt.setMonthCount(2, mar);
        dt.setRevenue(2, totMar);
    }

    public boolean getManagerInfo(String id, String pwd) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            String sql = "SELECT * FROM MANAGER";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                if (username.equals(id) && password.equals(pwd)) {
                    ok = true;
                }
            }
            if (!ok){
                System.out.println("Getting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        return ok;
    }

    public void getPopularity(DataTransfer dt) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int[] express2163 = new int[12];
        int[] express1234 = new int[12];
        int[] express5678 = new int[12];
        int[] express9219 = new int[12];
        int[] express7837 = new int[12];
        int[] regional456 = new int[12];
        int[] express5345 = new int[12];
        int[] express2912 = new int[12];
        int[] regional853 = new int[12];
        try {
            con = getConn();
            String sql = "SELECT * FROM RESERVES";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                cal.setTime(rs.getDate("DepartureDate"));
                String trainNum = rs.getString("TrainNumber");
                int month = cal.get(Calendar.MONTH);
                if (month == 0) {
                    if (trainNum.equals("2163 Express")) { express2163[0] += 1; }
                    if (trainNum.equals("1234 Express")) { express1234[0] += 1; }
                    if (trainNum.equals("5678 Express")) { express5678[0] += 1; }
                    if (trainNum.equals("9219 Express")) { express9219[0] += 1; }
                    if (trainNum.equals("7837 Express")) { express7837[0] += 1; }
                    if (trainNum.equals("456 Regional")) { regional456[0] += 1; }
                    if (trainNum.equals("5345 Express")) { express5345[0] += 1; }
                    if (trainNum.equals("2912 Express")) { express2912[0] += 1; }
                    if (trainNum.equals("853 Regional")) { regional853[0] += 1; }
                } else if (month == 1) {
                    if (trainNum.equals("2163 Express")) { express2163[1] += 1; }
                    if (trainNum.equals("1234 Express")) { express1234[1] += 1; }
                    if (trainNum.equals("5678 Express")) { express5678[1] += 1; }
                    if (trainNum.equals("9219 Express")) { express9219[1] += 1; }
                    if (trainNum.equals("7837 Express")) { express7837[1] += 1; }
                    if (trainNum.equals("456 Regional")) { regional456[1] += 1; }
                    if (trainNum.equals("5345 Express")) { express5345[1] += 1; }
                    if (trainNum.equals("2912 Express")) { express2912[1] += 1; }
                    if (trainNum.equals("853 Regional")) { regional853[1] += 1; }
                } else if (month == 2) {
                    if (trainNum.equals("2163 Express")) { express2163[2] += 1; }
                    if (trainNum.equals("1234 Express")) { express1234[2] += 1; }
                    if (trainNum.equals("5678 Express")) { express5678[2] += 1; }
                    if (trainNum.equals("9219 Express")) { express9219[2] += 1; }
                    if (trainNum.equals("7837 Express")) { express7837[2] += 1; }
                    if (trainNum.equals("456 Regional")) { regional456[2] += 1; }
                    if (trainNum.equals("5345 Express")) { express5345[2] += 1; }
                    if (trainNum.equals("2912 Express")) { express2912[2] += 1; }
                    if (trainNum.equals("853 Regional")) { regional853[2] += 1; }
                }
                ok = true;
            }
            if (!ok){
                System.out.println("Getting Reserves Failed by Some Reason");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ex) {}
            if (ps != null) try { ps.close(); } catch (SQLException ex) {}
            if (con != null) try { con.close(); } catch (SQLException ex) {}
        }
        List<Train> trains1 = getCreateTrains(express2163[0], express1234[0], express5678[0], express9219[0], express7837[0], regional456[0], express5345[0], express2912[0], regional853[0]);
        Collections.sort(trains1, new NoDescCompare());

        String janRank1 = trains1.get(0).toString();
        String janRank2 = trains1.get(1).toString();
        String janRank3 = trains1.get(2).toString();
        dt.setMonth(0, "January");
        dt.setMonthlyPop1(0, janRank1);
        dt.setMonthlyPop1(1, janRank2);
        dt.setMonthlyPop1(2, janRank3);

        List<Train> trains2 = getCreateTrains(express2163[1], express1234[1], express5678[1], express9219[1], express7837[1], regional456[1], express5345[1], express2912[1], regional853[1]);
        Collections.sort(trains2, new NoDescCompare());

        String febRank1 = trains2.get(0).toString();
        String febRank2 = trains2.get(1).toString();
        String febRank3 = trains2.get(2).toString();
        dt.setMonth(1, "February");
        dt.setMonthlyPop2(0, febRank1);
        dt.setMonthlyPop2(1, febRank2);
        dt.setMonthlyPop2(2, febRank3);

        List<Train> trains3 = getCreateTrains(express2163[2], express1234[2], express5678[2], express9219[2], express7837[2], regional456[2], express5345[2], express2912[2], regional853[2]);
        Collections.sort(trains3, new NoDescCompare());

        String marRank1 = trains3.get(0).toString();
        String marRank2 = trains3.get(1).toString();
        String marRank3 = trains3.get(2).toString();
        dt.setMonth(2, "March");
        dt.setMonthlyPop3(0, marRank1);
        dt.setMonthlyPop3(1, marRank2);
        dt.setMonthlyPop3(2, marRank3);
    }

    private List<Train> getCreateTrains(int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        List<Train> trains = new ArrayList<Train>();

        Train train = new Train();
        train.setName("2163 Express");
        train.setNo(i);
        trains.add(train);

        train = new Train();
        train.setName("1234 Express");
        train.setNo(j);
        trains.add(train);

        train = new Train();
        train.setName("5678 Express");
        train.setNo(k);
        trains.add(train);

        train = new Train();
        train.setName("9219 Express");
        train.setNo(l);
        trains.add(train);

        train = new Train();
        train.setName("7837 Express");
        train.setNo(m);
        trains.add(train);

        train = new Train();
        train.setName("456 Regional");
        train.setNo(n);
        trains.add(train);

        train = new Train();
        train.setName("5345 Express");
        train.setNo(o);
        trains.add(train);

        train = new Train();
        train.setName("2912 Express");
        train.setNo(p);
        trains.add(train);

        train = new Train();
        train.setName("853 Regional");
        train.setNo(q);
        trains.add(train);

        return trains;
    }

    static class NoDescCompare implements Comparator<Train> {
        public int compare(Train arg0, Train arg1) {
            return arg0.no > arg1.no ? -1 : arg0.no < arg1.no ? 1 : 0;
        }
    }

    public class Train {
        private String name;
        private int no;
        public int getNo() {
            return no;
        }
        public void setNo(int no) {
            this.no = no;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            StringBuilder str = new StringBuilder();
            str.append("TN=").append(name).append(", ");
            str.append("Num=").append(no);

            return str.toString();
        }
    }
}
