package utils;

import models.Operator;
import models.Publication;
import models.Subscription;

import java.util.Date;

public class Comparer {
    public static boolean match(Publication pub, Subscription sub) {
        boolean isMatch = false;
        if (sub.company != null) {
            isMatch = matchString(pub.company, sub.company.value, sub.company.operator);
        }
        if (sub.date != null) {
            isMatch = matchDate(pub.date, sub.date.value, sub.date.operator);
        }
        if (sub.drop != null) {
            isMatch = matchDouble(pub.drop, sub.drop.value, sub.drop.operator);
        }
        if (sub.value != null) {
            isMatch = matchDouble(pub.value, sub.value.value, sub.value.operator);
        }
        if (sub.variation != null) {
            isMatch = matchDouble(pub.variation, sub.variation.value, sub.variation.operator);
        }
        return isMatch;
    }

    private static boolean matchString(String s1, String s2, Operator op) {
        if (op == Operator.Equal) {
            return s1.equals(s2);
        } else {
            return !s1.equals(s2);
        }
    }

    private static boolean matchDouble(Double d1, Double d2, Operator op) {
        if (op == Operator.Equal) {
            return Double.compare(d1, d2) == 0;
        } else if (op == Operator.NotEqual) {
            return Double.compare(d1, d2) != 0;
        } else if (op == Operator.Greater) {
            return Double.compare(d1, d2) > 0;
        } else if (op == Operator.GreaterOrEqual) {
            return Double.compare(d1, d2) >= 0;
        } else if (op == Operator.Lower) {
            return Double.compare(d1, d2) < 0;
        } else if (op == Operator.LowerOrEqual) {
            return Double.compare(d1, d2) <= 0;
        } else {
            return false;
        }
    }

    private static boolean matchDate(Date dt1, Date dt2, Operator op) {
        if (op == Operator.Equal) {
            return dt1.equals(dt2);
        } else if (op == Operator.NotEqual) {
            return !dt1.equals(dt2);
        } else if (op == Operator.Greater) {
            return dt1.after(dt2);
        } else if (op == Operator.GreaterOrEqual) {
            return dt1.after(dt2) || dt1.equals(dt2);
        } else if (op == Operator.Lower) {
            return dt1.before(dt2);
        } else if (op == Operator.LowerOrEqual) {
            return dt1.before(dt2) || dt1.equals(dt2);
        } else {
            return false;
        }
    }
}
