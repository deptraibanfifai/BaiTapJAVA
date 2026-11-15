package com.library.management.system.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Utility class xử lý các thao tác với ngày tháng
 */
public class DateUtils {

    private DateUtils() {}

    /**
     * Tính số ngày giữa 2 mốc thời gian
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Kiểm tra ngày có quá hạn không
     */
    public static boolean isOverdue(LocalDate dueDate) {
        return dueDate != null && dueDate.isBefore(LocalDate.now());
    }

    /**
     * Tính số ngày quá hạn
     */
    public static int getOverdueDays(LocalDate dueDate) {
        if (dueDate == null || !dueDate.isBefore(LocalDate.now())) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }
}