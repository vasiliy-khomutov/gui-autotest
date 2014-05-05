package model;


import org.testng.annotations.Test;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class Email {

    private static String outlookConnect(String orderId){

        String POP_AUTH_USER = "sysadmin@payonline.ru";
        String POP_AUTH_PWD = "1qaz!QAZ";
        String FOLDER_INDOX = "INBOX"; // имя папки "Входящие"
        Properties pop3Props = new Properties();

        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port", "25");
        pop3Props.setProperty("mail.pop3.socketFactory.port", "110");

        URLName url = new URLName("pop3", "192.168.88.240", 110, "", POP_AUTH_USER, POP_AUTH_PWD);
        Session session = Session.getInstance(pop3Props, null);
        Store store = null;
        try {
            store = session.getStore(url);
            store.connect();
            Folder folder = store.getFolder(FOLDER_INDOX);
            try {
                folder.open(Folder.READ_WRITE);
                try {
                    Message[] messages = folder.getMessages();
                    for(Message message : messages){
                        if(message.getContent().toString().contains(orderId)){
                            System.out.print(message.getContent().toString()+"==============\n");
                            return message.getContent().toString();
                        }
                        System.out.print("NOOOOOOOOOOOOOOOOO"+"==============\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.print("^( \n");
                }
                store.close();
            } catch (MessagingException ex) {
                folder.open(Folder.READ_ONLY);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkEmail(String orderId){
        outlookConnect(orderId);
        return false;
    }




}
