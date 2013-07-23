package com.qsoft.bam.utils;

import java.util.Date;

/**
 * User: lent
 * Date: 7/23/13
 */
public class DateUtils
{
    public static boolean isAfterOrEquals(Date reference, Date from)
    {
        return from == null || reference.compareTo(from) >= 0;
    }

    public static boolean isBeforeOrEquals(Date reference, Date to)
    {
        return to == null || reference.compareTo(to) <= 0;
    }
}
