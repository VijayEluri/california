package com.tomkp.california.coercion;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCoercer implements Coercer<Date> {

    private static final Logger LOG = Logger.getLogger(DateCoercer.class);

    private SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public Date coerce(String value, String format) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Date parse = null;
        SimpleDateFormat dateFormat = DEFAULT_FORMAT;
        try {
            if (format != null && format.length() > 0) {
                dateFormat = new SimpleDateFormat(format);
            }
            if (LOG.isDebugEnabled()) LOG.debug("parse date with format: '" + dateFormat.toPattern() + "'");
            parse = dateFormat.parse(value);
        } catch (Exception e) {
            LOG.warn("error parsing date '" + value + "', with format '" + dateFormat.toPattern() + "'", e);
            throw new RuntimeException("error parsing date '" + value + "', with format '" + dateFormat.toPattern() + "'", e);
        }
        return parse;
    }


}
