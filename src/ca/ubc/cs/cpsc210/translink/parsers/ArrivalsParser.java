package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {
    private static int nummberofArrvals;


    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop         stop to which parsed arrivals are to be added
     * @param jsonResponse the JSON response produced by Translink
     * @throws JSONException                when JSON response does not have expected format
     * @throws ArrivalsDataMissingException when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {
        // TODO: Task 4: Implement this method

        StopManager.getInstance().clearStops();
        RouteManager.getInstance().clearRoutes();


        JSONArray arrivals = new JSONArray(jsonResponse);
        nummberofArrvals = 0;

        for (int index = 0; index < arrivals.length(); index++) {
            JSONObject searchRoute = arrivals.getJSONObject(index);
            parseRoute(searchRoute, stop);
        }
        if (nummberofArrvals == 0) {
            throw new ArrivalsDataMissingException();
        }

    }


    private static void parseRoute(JSONObject searchRoute, Stop stop) throws JSONException, ArrivalsDataMissingException {

        if (searchRoute.has("Schedules") && searchRoute.has("RouteNo")) {
            String routeName = searchRoute.getString("RouteName");
            System.out.print(routeName);
            String routeNo = searchRoute.getString("RouteNo");
            System.out.println();

            Route route = RouteManager.getInstance().getRouteWithNumber(routeNo, routeName);

            JSONArray arrivalSchedules = searchRoute.getJSONArray("Schedules");
            for (int index = 0; index < arrivalSchedules.length(); index++) {
                JSONObject arrivalSchedule = arrivalSchedules.getJSONObject(index);
                System.out.println();
                parseArrivalSchedule(arrivalSchedule, route, stop);

            }
        }
    }

    private static void parseArrivalSchedule(JSONObject arrivalSchedule, Route route, Stop stop) throws JSONException, ArrivalsDataMissingException {
        if ((!arrivalSchedule.has("Destination")) || (!arrivalSchedule.has("ExpectedCountdown")) || (!arrivalSchedule.has("ScheduleStatus"))) {
            throw new ArrivalsDataMissingException("Missing pattern info");
        }
        nummberofArrvals++;
        String destinationOfBus = arrivalSchedule.getString("Destination");
        System.out.println();
        int expectedCountdown = arrivalSchedule.getInt("ExpectedCountdown");
        System.out.println();
        String scheduleStatus = arrivalSchedule.getString("ScheduleStatus");
        System.out.println();

        Arrival arrival = new Arrival(expectedCountdown, destinationOfBus, route);

        arrival.setStatus(scheduleStatus);

        stop.addArrival(arrival);


    }
}














