package com.example.rashi.googlemaps;

import java.util.ArrayList;

/**
 * Created by Rashi on 3/26/2018.
 */

public class AppResponse {
    ArrayList<Points> points = new ArrayList<>();

    public static class Points{
        double latitude;
        double longitude;

        @Override
        public String toString() {
            return "Points{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "points=" + points +
                '}';
    }
}
