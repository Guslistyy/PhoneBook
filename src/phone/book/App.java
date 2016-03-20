package phone.book;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import phone.book.data.Contact;
import phone.book.data.PhoneNumber;
import phone.book.data.PhoneNumberType;
import phone.book.service.PhoneBookFunctions;
import phone.book.service.impl.PhoneBookFunctionsImpl;
import phone.book.storage.WorkWithFiles;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by КСЮША on 26.01.2016.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("phoneBookShow.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/phone/book/show/bookShow.fxml"));
        primaryStage.setTitle("Phone Book");
        //primaryStage.setMinHeight(600);
        //primaryStage.setMinWidth(900);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {

        PhoneBookFunctions phoneBookFunctions =  PhoneBookFunctionsImpl.getinstance();

        WorkWithFiles storage = new WorkWithFiles();
         try {storage.readList("PhoneBookList.json");
             // закоментил т.к. не получилось считать МАПУ с файла - выдает ошибку при считывании
              //storage.readMap("PhoneBookMap.json");
        }catch (IOException e){
            e.printStackTrace();
        }
        /*
        // блок для тестового наполнения тел. книги
        PhoneNumber number1 = new PhoneNumber("+100500", PhoneNumberType.MOB);
        PhoneNumber number2 = new PhoneNumber("+380662222222", PhoneNumberType.MOB);
        PhoneNumber number3 = new PhoneNumber("+380663333333", PhoneNumberType.MOB);
        PhoneNumber number4 = new PhoneNumber("+380664444444", PhoneNumberType.MOB);
        PhoneNumber number5 = new PhoneNumber("+380665555555", PhoneNumberType.MOB);
        PhoneNumber number6 = new PhoneNumber("+380666666666", PhoneNumberType.MOB);
        PhoneNumber number7 = new PhoneNumber("+380667777777", PhoneNumberType.MOB);
        PhoneNumber number8 = new PhoneNumber("+380668888888", PhoneNumberType.MOB);
        PhoneNumber number9 = new PhoneNumber("+380669999999", PhoneNumberType.MOB);

        Contact contact1 = new Contact("LN1", "FN1", LocalDate.of(1985,5,22), "SuperMan@gmail.com", "Dom1",
                new HashSet<>(Arrays.asList(number1)), "Al");

        Contact contact2 = new Contact("LN2", "FN2", LocalDate.of(1988,6,21), "BathMan@gmail.com", "Dom2",
                new HashSet<>(Arrays.asList(number2, number3, number4)), "Al");

        Contact contact3 = new Contact("LN3", "FN3", LocalDate.of(1980,1,12), "Flash@gmail.com", "Dom3",
                new HashSet<>(Arrays.asList(number5, number9)), "Al");

        Contact contact4 = new Contact("LN4", "FN4", LocalDate.of(1988,6,21), "DedPool@gmail.com", "Dom4",
                new HashSet<>(Arrays.asList(number7, number8)), "Al");

        phoneBookFunctions.add(contact1);
        System.out.println(phoneBookFunctions);

        phoneBookFunctions.add(contact2);
        System.out.println(phoneBookFunctions);

        phoneBookFunctions.add(contact3);
        System.out.println(phoneBookFunctions);

        phoneBookFunctions.add(contact4);
        System.out.println(phoneBookFunctions);

      //  phoneBookFunctions.remove(contact2);
       // System.out.println(phoneBookFunctions);

        phoneBookFunctions.add(contact1, number6);
        System.out.println(phoneBookFunctions);

       // phoneBookFunctions.remove(number6);
       // System.out.println(phoneBookFunctions);

        //phoneBookFunctions.remove(number1);
        //System.out.println(phoneBookFunctions);

        //System.out.println(phoneBookFunctions.searchByName("LN1"));

        //System.out.println(phoneBookFunctions.searchByPartOffName("1"));

       // System.out.println(phoneBookFunctions.searchByNumber("00"));

        //System.out.println(phoneBookFunctions.searchByAge(LocalDate.of(1982,5,22),LocalDate.of(1988,5,22)));
        */

        launch(args);

        try {
            storage.writeList("PhoneBookList.json", phoneBookFunctions.getAllContactsList());
            storage.writeMap("PhoneBookMap.json", phoneBookFunctions.getAllContactsMap());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}