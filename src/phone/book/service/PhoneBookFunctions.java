package phone.book.service;

import phone.book.data.Contact;
import phone.book.data.PhoneNumber;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

/**
 * Created by КСЮША on 26.01.2016.
 */
public interface PhoneBookFunctions {
    boolean add(Contact contact);
    boolean edit(Contact contact, Contact editedContact);
    boolean remove(Contact contact);

    boolean add(Contact contact, PhoneNumber phoneNumber);
    //boolean edit(PhoneNumber phoneNumber);
    boolean remove(PhoneNumber phoneNumber);

    Contact getContact (Contact contact);
    Contact getContactByNumber (PhoneNumber phoneNumber);

    Collection<Contact> getAllContactsList();
    Map<PhoneNumber, Contact> getAllContactsMap();
    Map<PhoneNumber, Contact> copyToMap();

    Collection<PhoneNumber> getAllNumbers(Contact contact);
    Collection<Contact> searchByName(String name);
    Collection<Contact> searchByPartOffName(String partOffName);
    Collection<Contact> searchByNumber (String phoneNumber);
    Collection<Contact> searchByAge(LocalDate from, LocalDate to);

}
