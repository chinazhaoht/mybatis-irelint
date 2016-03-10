package com.irelint.mybatis.study.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author:zhaoht
 * date:2016/3/8 17:05
 */

public class Receiver implements MessageListener {

    private boolean stop = false;

    public static void main(String[] args){
      new Receiver().receive();
    }


    public void receive(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try{
            //Prompt for JNDI names

            System.out.println("Enter ConnectFactory name.");
            String  factoryName = reader.readLine();

            System.out.println("Enter Destination name.");

            String destinationName = reader.readLine();

            reader.close();

            //Look up administered objects
            InitialContext initialContext = new InitialContext();

            ConnectionFactory factory =
                    (ConnectionFactory)initialContext.lookup(factoryName);

            Destination destination = (Destination)initialContext.lookup(destinationName);

            initialContext.close();

            //Create JMS objects

            Connection connection = factory.createConnection();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            MessageConsumer receiver = session.createConsumer(destination);
            receiver.setMessageListener(this);

            //Wait for stop

            while(stop){
                Thread.sleep(1000);
            }


            //Exit

            System.out.println("Exiting...");

            connection.close();

            System.out.println("Goodbye!");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void onMessage(Message message) {

        try{
            String megText = ((TextMessage)message).getText();

            System.out.println(megText);
            if("stop".equals(megText)){
                stop = true;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
