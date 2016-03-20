package phone.book.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import phone.book.data.Contact;
import phone.book.data.PhoneNumber;
import phone.book.exception.WrongDestinationException;
import phone.book.service.PhoneBookFunctions;
import phone.book.service.impl.PhoneBookFunctionsImpl;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by КСЮША on 02.03.2016.
 */
public class WorkWithFiles {

    public void readList(String path) throws IOException {
        String str="";
        File file=new File(path);
        if(file.isDirectory()){
            throw  new WrongDestinationException ("Can not read file "+path);
        }
        try (
                FileReader fr=new FileReader(file);
                BufferedReader br=new BufferedReader(fr);
                Scanner scanner=new Scanner(br);
        ){
            str=scanner.nextLine();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<Collection<Contact>>() {}.getType();

        Collection<Contact> p = gson.fromJson(str, listType);
        System.out.println("READ");
        System.out.println(p);

        PhoneBookFunctions phoneBookFunctions =  PhoneBookFunctionsImpl.getinstance();
        phoneBookFunctions.getAllContactsList().addAll(p);

        // копирую в MAP т.к. не получилось считать МАПУ с файла - выдает ошибку при считывании
        phoneBookFunctions.copyToMap();
    }

    public void readMap(String path) throws IOException {
        String str="";
        File file=new File(path);
        if(file.isDirectory()){
            throw  new WrongDestinationException ("Can not read file "+path);
        }
        try (
                FileReader fr=new FileReader(file);
                BufferedReader br=new BufferedReader(fr);
                Scanner scanner=new Scanner(br);
        ){
            str=scanner.nextLine();
        }
        Gson gson = new Gson();
        Type mapType = new TypeToken <Map<PhoneNumber, Contact>>() {}.getType();

        Map<PhoneNumber, Contact> p = gson.fromJson(str, mapType);
        System.out.println("READ");
        System.out.println(p);

        PhoneBookFunctions phoneBookFunctions =  PhoneBookFunctionsImpl.getinstance();
        phoneBookFunctions.getAllContactsMap().putAll(p);
    }

    //---------------------------------------------------------------------

    public void writeList(String path, Collection<Contact> collections)throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            throw new WrongDestinationException("Can not write file " + path);
        }
        try (
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
        ) {
            pw.print(contactToGson(collections));
            pw.flush();
        }
    }

    public String contactToGson(Collection<Contact> collections) {
        Gson gson = new Gson();
        String json = gson.toJson(collections);
        return json;
    }

    public void writeMap(String path, Map<PhoneNumber, Contact> map)throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            throw new WrongDestinationException("Can not write file " + path);
        }
        try (
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
        ) {
            pw.print(numbersToGson(map));
            pw.flush();
        }
    }

    public String numbersToGson(Map<PhoneNumber, Contact> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map/*, HashMap.class */);
        return json;
    }
}
