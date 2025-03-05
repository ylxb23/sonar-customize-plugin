package java;

import java.math.BigDecimal;

public class BigDecimalEquals {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("1.23");
        BigDecimal b = new BigDecimal("1.230");
        if(a.equals(b)) {   // Noncompliant
            System.out.println("BigDecimal.equals 比较a=1.23 和 b=1.230结果相等");
        }

        if(a.compareTo(b) == 0) {   // Compliant
            System.out.println("BigDecimal.compareTo 比较a=1.23 和 b=1.230结果相等");
        }
    }
}