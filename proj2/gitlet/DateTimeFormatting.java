package gitlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.DoubleToIntFunction;

public class DateTimeFormatting {
    /** Get the formatted timestamp at the current moment. */
    public static String currentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return commitFormatter().format(currentDateTime);
    }

    /** Get the formatted epoch timestamp. */
    public static String epochDateTime() {
        Instant epochInstant = Instant.ofEpochSecond(0);
        return commitFormatter().format(epochInstant);
    }

    /** Generate the formatter for formatting the date and the time.
     * e.g. 00:00:00 UTC, Thursday, 1 January 1970 */
    private static DateTimeFormatter commitFormatter() {
        return DateTimeFormatter
                .ofPattern("HH:mm:ss 'UTC,' EEEE, d MMMM yyyy", Locale.ENGLISH)
                .withZone(ZoneId.of("UTC"));
    }
    /** Wed Dec 31 16:00:00 1969 -0800 */
    public static String logTimestampConversion(String timestamp) {
        try {
            // Define the input and output date formats
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss 'UTC,' EEEE, d MMMM yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
            // Set the input format's time zone to UTC
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            // Parse the input timestamp
            Date date = inputFormat.parse(timestamp);

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle any parsing errors here
            return null;
        }
    }

}
