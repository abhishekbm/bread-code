package shakestudios.traintimer.ValueObjects;


import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by abbm on 6/2/2016.
 */
public class FaresVO {


    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public static List<String> getFares(String from, String to, String line, Context context) {


        List<String> price = new LinkedList<String>();
        JSONObject json = null;
        try {
            boolean stop = false;
            if ("green".equalsIgnoreCase(line)) {
                String jsonLocation = AssetJSONFile("green.json", context);
                json = (new JSONObject(jsonLocation));
            } else if ("purple".equalsIgnoreCase(line)) {
                String jsonLocation = AssetJSONFile("purple.json", context);
                json = (new JSONObject(jsonLocation));
            }

            JSONArray array = json.optJSONArray("fares");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String station = obj.getString("station");
                if (from.equalsIgnoreCase(station)) {


                    JSONArray coinArray = obj.getJSONArray("cost");
                    for (int j = 0; j < coinArray.length(); j++) {
                        JSONObject coinObject = coinArray.getJSONObject(j);
                        String to_Station = coinObject.getString("station");
                        if (to.equalsIgnoreCase(to_Station)) {
                            String coin_price = coinObject.getString("coin_price");
                            String card_price = coinObject.getString("card_price");
                            price.add(coin_price);
                            price.add(card_price);
                            price.add(obj.getString("doors"));
                            stop = true;
                        }
                    }

                }
                if (stop) {
                    break;
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (line.equalsIgnoreCase("purple")) {
            Set<String> adapter = new LinkedHashSet<String>();

            adapter.add("Byappanhalli");
            adapter.add("Swami Vivekananda Road");
            adapter.add("Indiranagar");
            adapter.add("Halasuru");
            adapter.add("Trinity");
            adapter.add("Mahatma Gandhi Road");
            adapter.add("Cubbon Park");
            adapter.add("Vidhana Soudha");
            adapter.add("Sir M. Visveshwaraya");
            adapter.add("Majestic (Inter Change)");
            adapter.add("City Railway Station");
            adapter.add("Magadi Road");
            adapter.add("Hosahalli");
            adapter.add("Vijayanagar");
            adapter.add("Attiguppe");
            adapter.add("Deepanjali Nagar");
            adapter.add("Mysore Road");

            int i = 0, k = 0;


            Iterator forFrom = adapter.iterator();
            Iterator forTo = adapter.iterator();

            while (forFrom.hasNext()) {

                String next = (String) forFrom.next();
                i++;
                if (next.equalsIgnoreCase(from)) {
                    break;
                }
            }
            while (forTo.hasNext()) {

                String next = (String) forTo.next();
                k++;
                if (next.equalsIgnoreCase(to)) {
                    break;
                }
            }

            if (i > k) {
                price.add(String.valueOf(i - k));
            } else {
                price.add(String.valueOf(k - i));
            }

        } else if (line.equalsIgnoreCase("green")) {

            Set<String> adapter1 = new LinkedHashSet<String>();

            adapter1.add("Nagasandra");
            adapter1.add("Dasarahalli");
            adapter1.add("Jalahalli");
            adapter1.add("Peenya Industry");
            adapter1.add("Peenya");
            adapter1.add("Yeshwanthpur Industry");
            adapter1.add("Yeshwanthpur");
            adapter1.add("Yesvantpur railway station");
            adapter1.add("Sandal Soap Factory (Orion Mall)");
            adapter1.add("Mahalakshmi");
            adapter1.add("Rajajinagar");
            adapter1.add("Kuvempu Road");
            adapter1.add("Srirampura");
            adapter1.add("Mantri Square (Sampige Road)");


            int i = 0, k = 0;


            Iterator forFrom = adapter1.iterator();
            Iterator forTo = adapter1.iterator();

            while (forFrom.hasNext()) {

                String next = (String) forFrom.next();
                i++;
                if (next.equalsIgnoreCase(from)) {
                    break;
                }
            }
            while (forTo.hasNext()) {

                String next = (String) forTo.next();
                k++;
                if (next.equalsIgnoreCase(to)) {
                    break;
                }
            }

            if (i > k) {
                price.add(String.valueOf(i - k));
            } else {
                price.add(String.valueOf(k - i));
            }

        }


        return price;
    }

    /* public static void main(String args[]) {

         String from = "Hosahalli";
         String to = "Deepanjali Nagar";
         System.out.print(getFares(from, to, "purple"));
     }
 */
    public List<String> getStationDetails(Context context, String stationDet) {
        String station = null;
        try {
            station = AssetJSONFile("stationDetail.json", context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> details = new LinkedList<String>();
        try {
            JSONObject jsonobject = new JSONObject(station);
            JSONArray stationArray = jsonobject.getJSONArray("stations");
            for (int i = 0; i < stationArray.length(); i++) {
                JSONObject stationObject = stationArray.getJSONObject(i);
                String stationDetail = stationObject.getString("station");

                if (stationDet.equalsIgnoreCase(stationDetail)) {
                    JSONArray platform = stationObject.getJSONArray("platforms");
                    details.add(platform.getJSONObject(0).getString("platform_1"));
                    details.add(platform.getJSONObject(0).getString("platform_2"));
                    details.add(stationObject.getString("station"));
                    details.add(stationObject.getString("station_elevation"));
                    details.add(stationObject.getString("line"));
                    details.add(stationObject.getString("lift_escalator"));
                    details.add(stationObject.getString("Parking"));
                    details.add(stationObject.getString("Origin"));
                    details.add(stationObject.getString("Destination"));
                    details.add(stationObject.getString("Line_detail"));
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }

    public String getLatLong(Context context, String stationName) {

        String Json = null;
        String latlong = null;
        try {
            Json = AssetJSONFile("location.json", context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonobject = new JSONObject(Json);
            latlong = jsonobject.getString(stationName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latlong;
    }

}
