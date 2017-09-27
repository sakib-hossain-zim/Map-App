package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;
    private StopManager stopManager;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the file and add all stops to stop manager.
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException            when JSON data does not have expected format
     * @throws StopDataMissingException when
     *                                  <ul>
     *                                  <li> JSON data is not an array </li>
     *                                  <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     *                                  </ul>
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {
        // TODO: Task 4: Implement this method
        JSONArray stops = new JSONArray(jsonResponse);
        for (int index = 0; index < stops.length(); index++) {
            JSONObject stop = stops.getJSONObject(index);
            parseStop(stop);

        }

           }


    private void parseStop(JSONObject stop) throws JSONException,StopDataMissingException {

        if((!stop.has("Name"))||(!stop.has("StopNo"))||(!stop.has("Latitude"))||(!stop.has("Longitude"))){
            throw new StopDataMissingException("Missing info");
        }





        if ((!stop.has("Name")) || (!stop.has("StopNo")) || (!stop.has("Latitude")) || (!stop.has("Longitude"))) {
            throw new StopDataMissingException("Missing info");
        }


        String name = stop.getString("Name");
        System.out.println();
        int stopNo = stop.getInt("StopNo");
        System.out.println();

        Double latitude = stop.getDouble("Latitude");
        System.out.println();
        Double longitude = stop.getDouble("Longitude");
        System.out.println();

        LatLon latlon = new LatLon(latitude, longitude);

        String routes = stop.getString("Routes");

        String[] stringRoutes = routes.split(",");
        System.out.println();

        Stop stop2 = stopManager.getInstance().getStopWithId(stopNo, name, latlon);
        Stop st = StopManager.getInstance().getStopWithId(stopNo);

        for (int i = 0; i < stringRoutes.length; i++) {

            stringRoutes[i] = stringRoutes[i].trim();

            if (stringRoutes[i].length() > 0) {


                Route route = RouteManager.getInstance().getRouteWithNumber(stringRoutes[i]);
                System.out.println();
                stop2.addRoute(route);


            }

        }
    }
}






