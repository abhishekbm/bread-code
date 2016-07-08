package shakestudios.traintimer.ValueObjects;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by abbm on 5/31/2016.
 */
public class TimeSplitterGreen {


    public HashMap<String, Long> TimeSplitterGreen(Timestamp startTime) {

        HashMap<String, Long> timings = new HashMap<String, Long>();
        Timestamp Nagasandra = new Timestamp(calulateTime(startTime, 38, 00));
        Timestamp Dasarahalli = new Timestamp(calulateTime(startTime, 25, 30));
        Timestamp Jalahalli = new Timestamp(calulateTime(startTime, 31, 0));
        Timestamp Peenya_Industry  = new Timestamp(calulateTime(startTime, 29, 0));
        Timestamp Peenya = new Timestamp(calulateTime(startTime, 27, 0));
        Timestamp Yeshwanthpur_Industry = new Timestamp(calulateTime(startTime, 24, 00));
        Timestamp Yeshwanthpur = new Timestamp(calulateTime(startTime, 21, 00));
        Timestamp Yesvantpur_railway_station = new Timestamp(calulateTime(startTime, 19, 55));
        Timestamp Sandal_Soap_Factory  = new Timestamp(calulateTime(startTime, 17, 30));
        Timestamp Mahalakshmi = new Timestamp(calulateTime(startTime, 15, 40));
        Timestamp Rajajinagar = new Timestamp(calulateTime(startTime, 13, 20));
        Timestamp Kuvempu_Road = new Timestamp(calulateTime(startTime, 11, 00));
        Timestamp Srirampura = new Timestamp(calulateTime(startTime, 8, 0));
        Timestamp Sampige_Road = new Timestamp(calulateTime(startTime, 0, 0));

        timings.put("1", Nagasandra.getTime());
        timings.put("2", Dasarahalli.getTime());
        timings.put("3", Jalahalli.getTime());
        timings.put("4", Peenya_Industry .getTime());
        timings.put("5", Peenya.getTime());
        timings.put("6", Yeshwanthpur_Industry.getTime());
        timings.put("7", Yeshwanthpur.getTime());
        timings.put("8", Yesvantpur_railway_station.getTime());
        timings.put("9", Sandal_Soap_Factory .getTime());
        timings.put("10", Mahalakshmi.getTime());
        timings.put("11", Rajajinagar.getTime());
        timings.put("12", Kuvempu_Road.getTime());
        timings.put("13", Srirampura.getTime());
        timings.put("14", Sampige_Road.getTime());
        return timings;
    }

    private long calulateTime(Timestamp startTime, int delaymin, int delaysec) {
        return startTime.getTime() + delaymin * 60 * 1000 + delaysec * 1000;
    }
}
