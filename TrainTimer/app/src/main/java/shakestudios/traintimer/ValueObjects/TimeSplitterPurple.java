package shakestudios.traintimer.ValueObjects;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by abbm on 5/28/2016.
 */
public class TimeSplitterPurple {


    public HashMap<String, Long> TimeSplitterPurple(Timestamp startTime) {

        HashMap<String, Long> timings = new HashMap<String, Long>();
        Timestamp byappanhalli = new Timestamp(calulateTime(startTime, 38, 00));
        Timestamp swami_vivekananda_road = new Timestamp(calulateTime(startTime, 25, 30));
        Timestamp indiranagar = new Timestamp(calulateTime(startTime, 31, 0));
        Timestamp halasuru = new Timestamp(calulateTime(startTime, 29, 0));
        Timestamp trinity = new Timestamp(calulateTime(startTime, 27, 0));
        Timestamp mahatma_gandhi_road = new Timestamp(calulateTime(startTime, 24, 00));
        Timestamp cubbon_park = new Timestamp(calulateTime(startTime, 21, 00));
        Timestamp vidhana_soudha = new Timestamp(calulateTime(startTime, 19, 55));
        Timestamp sir_m_visveshwaraya = new Timestamp(calulateTime(startTime, 17, 30));
        Timestamp majestic = new Timestamp(calulateTime(startTime, 15, 40));
        Timestamp city_railway_station = new Timestamp(calulateTime(startTime, 13, 20));
        Timestamp magadi_road = new Timestamp(calulateTime(startTime, 11, 00));
        Timestamp hosahalli = new Timestamp(calulateTime(startTime, 8, 0));
        Timestamp vijayanagar = new Timestamp(calulateTime(startTime, 5, 20));
        Timestamp attiguppe = new Timestamp(calulateTime(startTime, 3, 20));
        Timestamp deepanjali_nagar = new Timestamp(calulateTime(startTime, 1, 20));
        Timestamp mysore_road = new Timestamp(calulateTime(startTime, 0, 0));

        timings.put("1", byappanhalli.getTime());
        timings.put("2", swami_vivekananda_road.getTime());
        timings.put("3", indiranagar.getTime());
        timings.put("4", halasuru.getTime());
        timings.put("5", trinity.getTime());
        timings.put("6", mahatma_gandhi_road.getTime());
        timings.put("7", cubbon_park.getTime());
        timings.put("8", vidhana_soudha.getTime());
        timings.put("9", sir_m_visveshwaraya.getTime());
        timings.put("10", majestic.getTime());
        timings.put("11", city_railway_station.getTime());
        timings.put("12", magadi_road.getTime());
        timings.put("13", hosahalli.getTime());
        timings.put("14", vijayanagar.getTime());
        timings.put("15", attiguppe.getTime());
        timings.put("16", deepanjali_nagar.getTime());
        timings.put("17", mysore_road.getTime());
        return timings;
    }

    private long calulateTime(Timestamp startTime, int delaymin, int delaysec) {
        return startTime.getTime() + delaymin * 60 * 1000 + delaysec * 1000;
    }

}
