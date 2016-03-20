package phone.book.show;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import phone.book.data.Contact;
import phone.book.data.PhoneNumber;
import phone.book.data.PhoneNumberType;
import phone.book.service.PhoneBookFunctions;
import phone.book.service.impl.PhoneBookFunctionsImpl;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by КСЮША on 06.02.2016.
 */
public class Controller implements Initializable {

    private PhoneBookFunctions phoneBookFunctions =  PhoneBookFunctionsImpl.getinstance();

    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private DatePicker birthday;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private TextField creator;
    @FXML
    private TextField number;
    @FXML
    private ComboBox<PhoneNumberType> type;
    @FXML
    private ListView<PhoneNumber> phoneNumbers;
    @FXML
    private ListView<Contact> contacts;

    private ObservableList <Contact> list;
    private ObservableList<PhoneNumber> list2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(phoneBookFunctions.getAllContactsList());
        contacts.setItems(list);
        list2 = FXCollections.observableArrayList();
        phoneNumbers.setItems(list2);
        type.setItems(FXCollections.observableArrayList(PhoneNumberType.values()));
    }

    @FXML
    private void selectContact() {
        Contact c = contacts.getSelectionModel().getSelectedItem();
        list2.clear();
        list2.addAll(c.getPhoneNumbers());
        firstName.setText(c.getFirstName());
        lastName.setText(c.getLastName());
        birthday.setValue(c.getBirthday());
        dateTo.setValue(c.getBirthday());
        email.setText(c.getEmail());
        creator.setText(c.getCreator());
        address.setText(c.getAddress());
    }

    @FXML
    private void selectNumber() {
        PhoneNumber p = phoneNumbers.getSelectionModel().getSelectedItem();
        number.setText(p.getNumber());
        type.getSelectionModel().select(p.getType());
    }

    @FXML
    private void addContact (){
        PhoneNumber numberTmp = new PhoneNumber(number.getText(), type.getValue());

        Contact contactTmp = new Contact(lastName.getText(), firstName.getText(), birthday.getValue(), email.getText(),
                address.getText(),new HashSet<>(Arrays.asList(numberTmp)), creator.getText());

        if (phoneBookFunctions.add(contactTmp)){
            list.add(contactTmp);
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("CONTACT is already exist");
            alert.show();
        }
        //Contact contact = new Contact();
        //String name = firstName.getText();
        //System.out.println(name);
    }

    @FXML
    private void addNumber (){
        PhoneNumber editedNumber = new PhoneNumber(number.getText(), type.getValue());

        Contact contactTmp = new Contact(lastName.getText(), firstName.getText(), birthday.getValue(), email.getText(),
                address.getText(),new HashSet<>(Arrays.asList(editedNumber)), creator.getText());

        if (phoneBookFunctions.add(contactTmp, editedNumber)){
            if (list.contains(contactTmp)){
                list2.add(editedNumber);
                int i = list.indexOf(contactTmp);
                list.remove(contactTmp);
                list.add(i, phoneBookFunctions.getContact(contactTmp));
                contacts.getSelectionModel().select(i);
            }
            else {
                list.add(contactTmp);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("NUMBER is already exist");
            alert.show();
        }
    }

    @FXML
    private void editContact (){
        PhoneNumber editedNumber = new PhoneNumber(number.getText(), type.getValue());

        Contact c = contacts.getSelectionModel().getSelectedItem();
        Set<PhoneNumber> listTmp = new HashSet<>(c.getPhoneNumbers());
        PhoneNumber p = phoneNumbers.getSelectionModel().getSelectedItem();
        listTmp.remove(p);
        listTmp.add(editedNumber);

        Contact contactTmp = new Contact(lastName.getText(), firstName.getText(), birthday.getValue(), email.getText(),
                address.getText(),listTmp, creator.getText());

        if (phoneBookFunctions.edit(c, contactTmp)){
            int i = list.indexOf(c);
            list.remove(c);
            list.add(i, contactTmp);
            contacts.getSelectionModel().select(i);

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("CONTACT is already exist");
            alert.show();
        }
    }

    @FXML
    private void removeСontact (){
        PhoneNumber numberTmp = new PhoneNumber(number.getText(), type.getValue());

        Contact contactTmp = new Contact(lastName.getText(), firstName.getText(), birthday.getValue(), email.getText(),
                address.getText(),new HashSet<>(Arrays.asList(numberTmp)), creator.getText());

        if (phoneBookFunctions.remove(contactTmp)){
            list.remove(contactTmp);
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("The contact was not found");
            alert.show();
        }
    }

    @FXML
    private void removeNumber (){
        PhoneNumber editedNumber = new PhoneNumber(number.getText(), type.getValue());
        Contact searchContact = phoneBookFunctions.getContactByNumber(editedNumber);
        if (phoneBookFunctions.remove(editedNumber)){
            list2.remove(editedNumber);
            Set<PhoneNumber> listTmp = searchContact.getPhoneNumbers();
            int i = listTmp.size();
            if (i == 0) {
                list.removeAll(searchContact);
            }else {
                canselFilters();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("The NUMBER was not found");
            alert.show();
        }
    }

    @FXML
    private void searchByName (){
        String s = firstName.getText();
        if (s.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("Do not enter a firstName");
            alert.show();
        }else {
            Collection<Contact> searchContact = phoneBookFunctions.searchByName(s);
            if (searchContact.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ATTENTION");
                alert.setContentText("The contact was not found");
                alert.show();
                ;
            }else {
                list.clear();
                //clineFild();
                list.addAll(searchContact);
            }
        }
    }

    @FXML
    private void searchByPartOffName (){
        String s = firstName.getText();
        if (s.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ATTENTION");
            alert.setContentText("Do not enter a firstName");
            alert.show();
        }else {
            Collection<Contact> searchContact = phoneBookFunctions.searchByPartOffName(s);
            if (searchContact.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ATTENTION");
                alert.setContentText("The contact was not found");
                alert.show();
                ;
            }else {
                list.clear();
                //clineFild();
                list.addAll(searchContact);
            }
        }
    }

    @FXML
    private void searchByNumber (){
        String s = number.getText();
        Collection<Contact> searchContact = phoneBookFunctions.searchByNumber(s);
        list.clear();
        //clineFild();
        list.addAll(searchContact);
    }

    @FXML
    private void searchByAge (){
        LocalDate fom = birthday.getValue();
        LocalDate to = dateTo.getValue();
        Collection<Contact> searchContact = phoneBookFunctions.searchByAge(fom, to);
        list.clear();
        //clineFild();
        list.addAll(searchContact);
    }

    @FXML
    private void clineFild (){
        list2.clear();
        firstName.clear();
        lastName.clear();
        birthday.setValue(null);
        dateTo.setValue(null);
        email.clear();
        creator.clear();
        address.clear();
        number.clear();
        type.getSelectionModel().clearSelection();
        contacts.getSelectionModel().clearSelection();
    }

    @FXML
    private void canselFilters (){
        list.clear();
        list.addAll((phoneBookFunctions.getAllContactsList()));
    }
}
