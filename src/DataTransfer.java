/**
 * Created by Joon on 2016-04-14.
 */
import java.sql.Time;

public class DataTransfer {
    private String username;
    private String email;
    private String pwd;
    private String school;
    private String trainNum;
    private String[] trainNums = new String[20];
    private Time[] arrivalTime = new Time[20];
    private Time[] departureTime = new Time[20];
    private String[] departStation = new String[20];
    private String[] arriveStation = new String[20];
    private Time[] duration = new Time[20];
    private String[] firstPrice = new String[20];
    private String[] secondPrice = new String[20];
    private String[] seatClass = new String[20];
    private String[] confirmPrice = new String[20];
    private String confirmCardNum;
    private String[] name = new String[20];
    private String[] cardNum = new String[20];
    private int[] cvv = new int[20];
    private String[] expiration = new String[20];
    private String reservationID;
    private String[] date = new String[20];
    private String newDate;
    private String[] comment = new String[20];
    private String[] rate = new String[20];
    private String[] bagNum = new String[20];
    private int count;
    private int count2;
    private int selDepCount;
    private int reserveCount;
    private int isCanceled;
    private int[] monthCount = new int[12];
    private String[] month = new String[12];
    private String[] revenue = new String[12];
    private String[] monthlyPop1 = new String[3];
    private String[] monthlyPop2 = new String[3];
    private String[] monthlyPop3 = new String[3];
    private int isStudent;
    private String[] setDuration2 = new String[20];

    public String getDuration2(int i) { return setDuration2[i]; }
    public void setDuration2(int i, String setDuration2) { this.setDuration2[i] = setDuration2; }
    public String getMonthlyPop1(int i) {
        return monthlyPop1[i];
    }
    public void setMonthlyPop1(int i, String month) {
        this.monthlyPop1[i] = month;
    }
    public String getMonthlyPop2(int i) {
        return monthlyPop2[i];
    }
    public void setMonthlyPop2(int i, String month) {
        this.monthlyPop2[i] = month;
    }
    public String getMonthlyPop3(int i) {
        return monthlyPop3[i];
    }
    public void setMonthlyPop3(int i, String month) {
        this.monthlyPop3[i] = month;
    }
    public int getSelDepCount() {
        return selDepCount;
    }
    public void setSelDepCount(int selDepCount) {
        this.selDepCount = selDepCount;
    }
    public int getIsStudent() {
        return isStudent;
    }
    public void setIsStudent(int isStudent) {
        this.isStudent = isStudent;
    }
    public String getMonth(int i) {
        return month[i];
    }
    public void setMonth(int i, String month) {
        this.month[i] = month;
    }
    public int getMonthCount(int i) {
        return monthCount[i];
    }
    public void setMonthCount(int i, int month) {
        this.monthCount[i] = month;
    }
    public String getRevenue(int i) {
        return revenue[i];
    }
    public void setRevenue(int i, String revenue) {
        this.revenue[i] = revenue;
    }
    public String getRate(int i) {
        return rate[i];
    }
    public void setRate(int i, String rate) {
        this.rate[i] = rate;
    }
    public String getComment(int i) {
        return comment[i];
    }
    public void setComment(int i, String comment) {
        this.comment[i] = comment;
    }
    public int getIsCanceled() {
        return isCanceled;
    }
    public void setIsCanceled(int isCanceled) {
        this.isCanceled = isCanceled;
    }
    public String getNewDate() {
        return newDate;
    }
    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }
    public int getReserveCount() {
        return reserveCount;
    }
    public void setReserveCount(int reserveCount) {
        this.reserveCount = reserveCount;
    }
    public String getConfirmCardNum() {
        return confirmCardNum;
    }
    public void setConfirmCardNum(String confirmCardNum) {
        this.confirmCardNum = confirmCardNum;
    }
    public String getSeatClass(int i) {
        return seatClass[i];
    }
    public void setSeatClass(int i, String seatClass) {
        this.seatClass[i] = seatClass;
    }
    public String getBagNum(int i) {
        return bagNum[i];
    }
    public void setBagNum(int i, String bagNum) {
        this.bagNum[i] = bagNum;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount2() {
        return count2;
    }
    public void setCount2(int count2) {
        this.count2 = count2;
    }
    public String getConfirmPrice(int i) {
        return confirmPrice[i];
    }
    public void setConfirmPrice(int i, String confirmPrice) {
        this.confirmPrice[i] = confirmPrice;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getTrainNum() {
        return trainNum;
    }
    public void setTrainNum(String trainNum) { this.trainNum = trainNum;}
    public String getTrainNums(int i) {
        return trainNums[i];
    }
    public void setTrainNums(int i, String number) {
        this.trainNums[i] = number;
    }
    public String getDepartStation(int i) {
        return departStation[i];
    }
    public void setDepartStation(int i, String station) {
        this.departStation[i] = station;
    }
    public String getArriveStation(int i) {
        return arriveStation[i];
    }
    public void setArriveStation(int i, String station) {
        this.arriveStation[i] = station;
    }
    public Time getDuration(int i) {
        return duration[i];
    }
    public void setDuration(int i, Time duration) {
        this.duration[i] = duration;
    }
    public String getFirstPrice(int i) {
        return firstPrice[i];
    }
    public void setFirstPrice(int i, String firstPrice) {
        this.firstPrice[i] = firstPrice;
    }
    public String getSecondPrice(int i) {
        return secondPrice[i];
    }
    public void setSecondPrice(int i, String secondPrice) {
        this.secondPrice[i] = secondPrice;
    }
    public String getName(int i) {
        return name[i];
    }
    public void setName(int i, String name) {
        this.name[i] = name;
    }
    public String getCardNum(int i) {
        return cardNum[i];
    }
    public void setCardNum(int i, String cardNum) {
        this.cardNum[i] = cardNum;
    }
    public int getCvv(int i) {
        return cvv[i];
    }
    public void setCvv(int i, int cvv) {
        this.cvv[i] = cvv;
    }
    public String getExpiration(int i) {
        return expiration[i];
    }
    public void setExpiration(int i, String expiration) {
        this.expiration[i] = expiration;
    }
    public String getReservationID() {
        return reservationID;
    }
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    public String getDate(int i) {
        return date[i];
    }
    public void setDate(int i, String date) {
        this.date[i] = date;
    }
    public Time getArrivalTime(int i) {
        return arrivalTime[i];
    }
    public void setArrivalTime(int i, Time arrivalTime) {
        this.arrivalTime[i] = arrivalTime;
    }
    public Time getDepartureTime(int i) {
        return departureTime[i];
    }
    public void setDepartureTime(int i, Time departureTime) {
        this.departureTime[i] = departureTime;
    }
}
