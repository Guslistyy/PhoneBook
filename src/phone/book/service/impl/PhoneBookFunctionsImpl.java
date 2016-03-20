package phone.book.service.impl;

import phone.book.data.Contact;
import phone.book.data.PhoneNumber;
import phone.book.service.PhoneBookFunctions;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by КСЮША on 26.01.2016.
 */
public class PhoneBookFunctionsImpl implements PhoneBookFunctions {

    private final static PhoneBookFunctionsImpl instance = new PhoneBookFunctionsImpl();

    public static PhoneBookFunctions getinstance (){
        return instance;
    }

    private PhoneBookFunctionsImpl (){
    }

    //private Map<Contact, Set<PhoneNumber>> phoneNumbersByContact = new HashMap<>();
    private Map<PhoneNumber, Contact> numbers = new HashMap<>();
    private ArrayList<Contact> contacts = new ArrayList<>();

    @Override
    public Collection<Contact> getAllContactsList() {
        return contacts;
    }

    @Override
    public Map<PhoneNumber, Contact> getAllContactsMap() {
        return numbers;
    }

    @Override
    public Contact getContact (Contact contact){
        if (contacts.contains(contact)) {
            int i = contacts.indexOf(contact);
            return contacts.get(i);
        }
        return contact;
    }

    @Override
    public Map<PhoneNumber, Contact> copyToMap() {
        for (Contact contact : contacts) {
            Set<PhoneNumber> tempNumbers = contact.getPhoneNumbers();
            for (PhoneNumber tempNumber : tempNumbers){
                if (!numbers.containsKey(tempNumber)){
                numbers.put(tempNumber, contact);
                }
            }
        }
        return numbers;
    }


    @Override
    public Contact getContactByNumber (PhoneNumber phoneNumber){
        return numbers.get(phoneNumber);

        //if (numbers.containsKey(phoneNumber)) {
        //}
        //return contact;
    }

    @Override
    public Collection<PhoneNumber> getAllNumbers(Contact contact) {
            Set<PhoneNumber> tempNumbers = contact.getPhoneNumbers();
        return tempNumbers;
    }

    @Override
    public boolean add(Contact contact) {
        boolean flagIsAdd = false;
        for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            Contact contacts1 = numbers.get(phoneNumber);
            if (contacts1 == null) {
                numbers.put(phoneNumber, contact);
                if (contacts.contains(contact)) {
                    int i = contacts.indexOf(contact);
                    Set<PhoneNumber> tempNumbers = contacts.get(i).getPhoneNumbers();
                    tempNumbers.add(phoneNumber);
                } else {
                    contacts.add(contact);
                }
                flagIsAdd = true;
            }
        }
        return flagIsAdd;
    }

    @Override
    public boolean edit(Contact contact, Contact editedContact) {
        remove(contact);
        return add(editedContact);
    }

    @Override
    public boolean remove(Contact contact) {
        boolean flagIsAdd = false;
        for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
//            Contact contacts1 = numbers.containsValue(contact);
            if (numbers.containsKey(phoneNumber)) {
                numbers.remove(phoneNumber);
                flagIsAdd = true;
            }
        }
        if (contacts.contains(contact)) {
            contacts.remove(contact);
            flagIsAdd = true;
        }
        return flagIsAdd;
    }

    @Override
    public boolean add(Contact contact, PhoneNumber phoneNumber) {
        boolean flagIsAdd = false;
        Contact contacts1 = numbers.get(phoneNumber);
        if (contacts1 == null) {
                numbers.put(phoneNumber, contact);
                if (contacts.contains(contact)) {
                    int i = contacts.indexOf(contact);
                    Set<PhoneNumber> tempNumbers = contacts.get(i).getPhoneNumbers();
                    tempNumbers.add(phoneNumber);
                } else {
                    contacts.add(contact);
                    int i = contacts.indexOf(contact);
                    Set<PhoneNumber> tempNumbers = contacts.get(i).getPhoneNumbers();
                    tempNumbers.add(phoneNumber);
                }
                flagIsAdd = true;
            }
        return flagIsAdd;
    }

    @Override
    public boolean remove(PhoneNumber phoneNumber) {
        boolean flagIsAdd = false;
        for (Contact contact : contacts) {
            Set<PhoneNumber> tempNumbers = contact.getPhoneNumbers();
            if (tempNumbers.remove(phoneNumber)){
                flagIsAdd = true;
            }
            if (tempNumbers.isEmpty()){
                contacts.remove(contact);
                flagIsAdd = true;
                break;
            }
        }
        Contact contacts1 = numbers.get(phoneNumber);
        if (contacts1 != null) {
            numbers.remove(phoneNumber);
            /*if (contacts.contains(contacts1)) {
                int i = contacts.indexOf(contacts1);
                Set<PhoneNumber> tempNumbers = contacts.get(i).getPhoneNumbers();
                tempNumbers.remove(phoneNumber);
            }*/
            flagIsAdd = true;
        }
        return flagIsAdd;
    }

    @Override
    public Collection<Contact> searchByName(String name) {
        ArrayList<Contact> contacts2 = new ArrayList<>();;
       //Contact contact1 = new Contact("NotFound", "NotFound", LocalDate.of(1,1,1), "", "", new HashSet<>(Arrays.asList()), "");
        for (Contact contact : contacts) {
            String tempFName = contact.getFirstName();
            String tempLName = contact.getLastName();
            if (tempFName.equals(name) | tempLName.equals(name)) {
                //contact1 = contact;
                contacts2.add(contact);
            }
        }
        return contacts2;
    }

    @Override
    public Collection<Contact> searchByPartOffName(String partOffName) {
        ArrayList<Contact> contacts2 = new ArrayList<>();;
        //Contact contact1 = new Contact("NotFound", "NotFound", LocalDate.of(1,1,1), "", "", new HashSet<>(Arrays.asList()), "");
        for (Contact contact : contacts) {
            String tempFName = contact.getFirstName();
            String tempLName = contact.getLastName();
            if (tempFName.contains(partOffName) | tempLName.contains(partOffName)) {
                //contact1 = contact;
                contacts2.add(contact);
            }
        }
        return contacts2;
    }

   /* @Override
    public Collection<Contact> searchByNumber(PhoneNumber phoneNumber) {
        ArrayList<Contact> contacts2 = new ArrayList<>();
        Contact contact1 = numbers.get(phoneNumber);
        if (contact1 != null) {
            //numbers.get(phoneNumber);
            contacts2.add(contact1);
        }

        for (Contact contact : contacts) {
            Set<PhoneNumber> tempNumbers = contact.getPhoneNumbers();
            if (tempNumbers.contains(phoneNumber)) {
                contacts2.add(contact);
            }
        }
        return contacts2;
    }*/

    @Override
    public Collection<Contact> searchByNumber(String phoneNumber) {
        ArrayList<Contact> contacts2 = new ArrayList<>();

        /*Contact contact1 = numbers.get(phoneNumber);
        if (contact1 != null) {
            //numbers.get(phoneNumber);
            contacts2.add(contact1);
        }*/

        for (Contact contact : contacts) {
            Set<PhoneNumber> tempNumbers = contact.getPhoneNumbers();
            for (PhoneNumber tempNumber : tempNumbers){
                if (tempNumber.getNumber().contains(phoneNumber)) {
                    contacts2.add(contact);
                }
            }
        }
        return contacts2;
    }

    @Override
    public Collection<Contact> searchByAge(LocalDate from, LocalDate to) {
        ArrayList<Contact> contacts2 = new ArrayList<>();;
        for (Contact contact : contacts) {
            LocalDate tempBirthday = contact.getBirthday();
            if (tempBirthday.isAfter(from) & tempBirthday.isBefore(to) ) {
                //contact1 = contact;
                contacts2.add(contact);
            }
        }
        return contacts2;
    }

    @Override
    public String toString() {
        return "MAP=" + numbers +
               "\nTreeSet=" + contacts +
                '}';
    }

    public Map<PhoneNumber, Contact> getNumbers() {
        return numbers;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}
