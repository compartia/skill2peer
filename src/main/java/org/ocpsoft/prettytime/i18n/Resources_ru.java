package org.ocpsoft.prettytime.i18n;

import java.util.ListResourceBundle;

import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;
import org.ocpsoft.prettytime.impl.TimeFormatProvider;
import org.ocpsoft.prettytime.units.Century;
import org.ocpsoft.prettytime.units.Day;
import org.ocpsoft.prettytime.units.Decade;
import org.ocpsoft.prettytime.units.Hour;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millennium;
import org.ocpsoft.prettytime.units.Millisecond;
import org.ocpsoft.prettytime.units.Minute;
import org.ocpsoft.prettytime.units.Month;
import org.ocpsoft.prettytime.units.Second;
import org.ocpsoft.prettytime.units.Week;
import org.ocpsoft.prettytime.units.Year;

/**
 * Created with IntelliJ IDEA.
 * User: Tumin Alexander
 * Date: 2012-12-13
 * Time: 03:33
 */
public class Resources_ru extends ListResourceBundle implements TimeFormatProvider
{
    private static class TimeFormatAided implements TimeFormat {
        private final String[] pluarls;

        public TimeFormatAided(final String... plurals) {
            if (plurals.length != russianPluralForms) {
                throw new IllegalArgumentException("Wrong plural forms number for russian language!");
            }
            this.pluarls = plurals;
        }

        @Override
        public String decorate(final Duration duration, final String time) {
            return performDecoration(
                    duration.isInPast(),
                    duration.isInFuture(),
                    duration.getQuantityRounded(tolerance),
                    time);
        }

        @Override
        public String decorateUnrounded(final Duration duration, final String time) {
            return performDecoration(
                    duration.isInPast(),
                    duration.isInFuture(),
                    duration.getQuantity(),
                    time);
        }

        @Override
        public String format(final Duration duration) {
            //            if (true) {
            //                throw new RuntimeException();
            //            }
            final long quantity = duration.getQuantityRounded(tolerance);
            final StringBuilder result = new StringBuilder();

            result.append(performDecoration(false, false, quantity, Long.toString(quantity)));
            return result.toString();
        }

        @Override
        public String formatUnrounded(final Duration duration) {
            final long quantity = duration.getQuantity();
            final StringBuilder result = new StringBuilder();
            result.append(performDecoration(false, false, quantity, Long.toString(quantity)));
            return result.toString();
        }

        private String performDecoration(final boolean past, final boolean future, final long n, final String time) {
            // a bit cryptic, yet well-tested
            // consider http://translate.sourceforge.net/wiki/l10n/pluralforms
            final int pluralIdx = (n % 10 == 1 && n % 100 != 11 ? 0 : n % 10 >= 2
                    && n % 10 <= 4
                    && (n % 100 < 10 || n % 100 >= 20) ? 1 : 2);
            if (pluralIdx > russianPluralForms) {
                // impossible happening
                throw new IllegalStateException("Wrong plural index was calculated somehow for russian language");
            }

            final StringBuilder result = new StringBuilder();

            if (future) {
                result.append("через ");
            }

            result.append(time);
            result.append(' ');
            result.append(pluarls[pluralIdx]);

            if (past) {
                result.append(" назад");
            }

            return result.toString();
        }
    }

    private static final Object[][] OBJECTS = new Object[0][0];

    private static final int tolerance = 50;

    // see http://translate.sourceforge.net/wiki/l10n/pluralforms
    private static final int russianPluralForms = 3;

    @Override
    public Object[][] getContents()
    {
        return OBJECTS;
    }

    @Override
    public TimeFormat getFormatFor(final TimeUnit t) {
        if (t instanceof JustNow) {
            return new TimeFormat() {
                @Override
                public String decorate(final Duration duration, final String time) {
                    return time;
                }

                @Override
                public String decorateUnrounded(final Duration duration, final String time) {
                    return time;
                }

                @Override
                public String format(final Duration duration) {
                    return performFormat(duration);
                }

                @Override
                public String formatUnrounded(final Duration duration) {
                    return performFormat(duration);
                }

                private String performFormat(final Duration duration) {
                    if (duration.isInFuture()) {
                        return "сейчас";
                    }
                    if (duration.isInPast()) {
                        return "только что";
                    }
                    return null;
                }
            };
        } else if (t instanceof Century) {
            return new TimeFormatAided("век", "века", "веков");
        } else if (t instanceof Day) {
            return new TimeFormatAided("день", "дня", "дней");
        } else if (t instanceof Decade) {
            return new TimeFormatAided("десятилетие", "десятилетия", "десятилетий");
        } else if (t instanceof Hour) {
            return new TimeFormatAided("час", "часа", "часов");
        } else if (t instanceof Millennium) {
            return new TimeFormatAided("тысячелетие", "тысячелетия", "тысячелетий");
        } else if (t instanceof Millisecond) {
            return new TimeFormatAided("миллисекунду", "миллисекунды", "миллисекунд");
        } else if (t instanceof Minute) {
            return new TimeFormatAided("минуту", "минуты", "минут");
        } else if (t instanceof Month) {
            return new TimeFormatAided("месяц", "месяца", "месяцев");
        } else if (t instanceof Second) {
            return new TimeFormatAided("секунду", "секунды", "секунд");
        } else if (t instanceof Week) {
            return new TimeFormatAided("неделю", "недели", "недель");
        } else if (t instanceof Year) {
            return new TimeFormatAided("год", "года", "лет");
        }
        return null; // error
    }
}
