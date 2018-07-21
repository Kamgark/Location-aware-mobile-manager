package app.com.kamgar.myapplication;

/**
 * Created by Faysal Ahmed on 24-Jun-17.
 * This class has just the variables, of whose objects ki array list banegi, and accessible items mamlaat.
 */

public class WifiScheduleData {
    public int id;
    public String DayName;
    public int Hours;
    public int Minutes;
    public int WifiStatus;
    public int ReqCode;

    public WifiScheduleData(int id,String DayName, int Hours, int Minutes, int WifiStatus,int ReqCode){
        this.id = id;
        this.DayName = DayName;
        this.Hours = Hours;
        this.Minutes = Minutes;
        this.WifiStatus = WifiStatus;
        this.ReqCode = ReqCode;

    }
}
