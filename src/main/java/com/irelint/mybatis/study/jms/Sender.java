package com.irelint.mybatis.study.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author:zhaoht
 * date:2016/3/8 16:55
 */

public class Sender {

    public static void main(String[] args){
        new Sender().send();
    }

    public void send(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Prompt for JNDI names

            System.out.println("Enter ConnectionFactory name.");

            String factoryName = reader.readLine();

            System.out.println("Enter Destination name.");

            String destinationName = reader.readLine();



            //Look up administered objects

            InitialContext initialContext = new InitialContext();

            ConnectionFactory factory =
                    (ConnectionFactory) initialContext.lookup(factoryName);
            Destination destination = (Destination) initialContext.lookup(destinationName);
            initialContext.close();

            //Create JMS objects

            Connection connection = factory.createConnection();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            MessageProducer sender = session.createProducer(destination);

            //Send message

            String messageText = null;

            while (true){
                System.out.println("Enter message to send or 'quit");

                messageText = reader.readLine();

                if("quit".equals(messageText)) {
                    break;

                }

                    TextMessage message = session.createTextMessage(messageText);

                    sender.send(message);

                //Exit

                System.out.println("Exiting");
                reader.close();
                connection.close();
                System.out.println("Goodbye!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
