package phone.book.data;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by КСЮША on 28.01.2016.
 */
public class Contact implements Comparable {
    private final String lastName;
    private final String firstName;
    private final LocalDate birthday;
    private final String email;
    private final String address;
    private final Set<PhoneNumber> phoneNumbers;
    private final String creator;

    public String comparator = getLastName() + getFirstName() + getBirthday();

    public Contact(String lastName, String firstName, LocalDate birthday, String email, String address,
                   Set<PhoneNumber> phoneNumbers, String creator) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
        this.creator = creator;
        this.comparator = lastName + firstName + birthday;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "" +
                "\nlastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", bd=" + birthday +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                //", creator='" + creator + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
        return birthday != null ? birthday.equals(contact.birthday) : contact.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object obj) {
        return comparator.compareTo(((Contact)obj).comparator);
    }
}
