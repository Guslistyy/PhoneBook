package phone.book.data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by КСЮША on 28.01.2016.
 */
public class PhoneNumber {
    private final String number;
    private final PhoneNumberType type;

    public PhoneNumber(String number, PhoneNumberType type) {
        this.number = number;
        this.type = type;
    }

    @Override
    public String toString() {
        return //"PhoneNumber{" +
                "\nnumber=" + number +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    public String getNumber() {
        return number;
    }

    public PhoneNumberType getType() {
        return type;
    }
}
