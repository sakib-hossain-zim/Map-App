package ca.ubc.cs.cpsc210.translink.util;

/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {
    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param point     the point in question
     * @return true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint(LatLon northWest, LatLon southEast, LatLon point) {
        // TODO: Task 5: Implement this method

        return ((between(northWest.getLongitude(), southEast.getLongitude(), point.getLongitude())) && between(southEast.getLatitude(), northWest.getLatitude(), point.getLatitude()));
    }

    /**
     * Return true if the rectangle intersects the line
     *
     * @param northWest the coordinate of the north west corner of the rectangle
     * @param southEast the coordinate of the south east corner of the rectangle
     * @param src       one end of the line in question
     * @param dst       the other end of the line in question
     * @return true if any point on the line is on the boundary or inside the rectangle
     */
    public static boolean rectangleIntersectsLine(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {
        LatLon northEast = new LatLon(northWest.getLatitude(), southEast.getLongitude());
        LatLon southWest = new LatLon(southEast.getLatitude(), northWest.getLongitude());

        if (twoLinesIntersect(northWest, northEast, src, dst) ||
                twoLinesIntersect(northEast, southEast, src, dst) ||
                twoLinesIntersect(southWest, southEast, src, dst) ||
                twoLinesIntersect(southWest, northWest, src, dst)) {
            return true;

        } else {
            if ((northWest.getLatitude() == src.getLatitude() || northWest.getLatitude() == dst.getLatitude()) ||
                    (northWest.getLongitude() == src.getLongitude() || northWest.getLongitude() == dst.getLongitude()) ||
                    (southWest.getLatitude() == src.getLatitude() || southWest.getLatitude() == dst.getLatitude()) ||
                    (southEast.getLongitude() == src.getLongitude() || southEast.getLongitude() == dst.getLongitude())) {
                return true;
            } else {
                if (rectangleContainsPoint(northWest, southEast, src) && rectangleContainsPoint(northWest, southEast, dst)) {
                    return true;
                }

            }
        }
        return false;
    }

    private static boolean twoLinesIntersect(LatLon a, LatLon b, LatLon c, LatLon d ) {

        double aX = a.getLongitude();
        double aY = a.getLatitude();

        double bX = b.getLongitude();
        double bY = b.getLatitude();

        double cX = c.getLongitude();
        double cY = c.getLatitude();

        double dX = d.getLongitude();
        double dY = d.getLatitude();


        double xAB = bX - aX;
        double yAB = bY - aY;

        double xBC = cX - bX;
        double yBC = cY - bY;

        double xBD = dX - bX;
        double yBD = dY - bY;

        double xCD = dX - cX;
        double yCD = dY - cY;

        double xDA = aX - dX;
        double yDA = aY - dY;

        double xDB = bX - dX;
        double yDB = bY - dY;


        double crossAB_BC = (xAB * yBC) - (yAB * xBC);
        double crossAB_BD = (xAB * yBD) - (yAB * xBD);
        double crossCD_DA = (xCD * yDA) - (yCD * xDA);
        double crossCD_DB = (xCD * yDB) - (yCD * xDB);

        return (crossAB_BC * crossAB_BD < 0) && (crossCD_DA * crossCD_DB < 0);

    }


    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     * @param lwb      the lower boundary
     * @param upb      the upper boundary
     * @param x         the value in question
     * @return          true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return lwb <= x && x <= upb;
    }
}
