package com.rest.fds.util;

import java.util.Random;

public class AppHelper {

    public static boolean validateLong(String longitude) {
        boolean isValid = false;
        if (longitude != null) {
            longitude = longitude.trim(); // Remove extra spaces
            String regexValidateLongitude = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";
            isValid = longitude.matches(regexValidateLongitude);
        }
        return isValid;
    }

    public static boolean validateLat(String latitude) {
        boolean isValid = false;
        if (latitude != null) {
            latitude = latitude.trim(); // Remove extra spaces
            String regexValidateLatitude = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$";
            isValid = latitude.matches(regexValidateLatitude);
        }
        return isValid;
    }

    public static String generateReferralCode() {
        long timestamp = System.currentTimeMillis();
        int randomPart = new Random().nextInt(900) + 100; // Random 3-digit number
        return ""   +timestamp + randomPart;
    }
}
