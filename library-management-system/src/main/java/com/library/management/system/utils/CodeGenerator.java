package com.library.management.system.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class để sinh mã tự động cho các entity
 */
public class CodeGenerator {

    private CodeGenerator() {}

    public static String generateMemberCode(long sequence) {
        return "MB" + String.format("%05d", sequence);
    }

    public static String generateBookCode(long sequence) {
        return "BK" + String.format("%05d", sequence);
    }

    public static String generateReceiptCode(long sequence) {
        return "PM" + String.format("%05d", sequence);
    }
}