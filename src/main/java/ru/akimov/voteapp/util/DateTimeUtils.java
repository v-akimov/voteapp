package ru.akimov.voteapp.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

/**
 * Created by z003cptz on 06.12.2015.
 */
@UtilityClass
public class DateTimeUtils {

    public static int getCurrentHours() {
        return LocalDateTime.now().getHour();
    }
}
