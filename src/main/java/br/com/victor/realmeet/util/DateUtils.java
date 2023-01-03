package br.com.victor.realmeet.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class DateUtils {

    public static final ZoneOffset DEFAULT_TIMEZONE = ZoneOffset.of("-03:00");

    private DateUtils() {
    }

    public static OffsetDateTime now() {
        return OffsetDateTime.now(DEFAULT_TIMEZONE);
    }

    public static boolean isOverlapping(
            OffsetDateTime startArt1,
            OffsetDateTime endAt1,
            OffsetDateTime startArt2,
            OffsetDateTime endAt2) {

        return startArt1.compareTo(endAt2) < 0 && endAt1.compareTo(startArt2) > 0;
    }
}
