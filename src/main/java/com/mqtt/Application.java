package  com.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Scanner;

public class Application {
    public static void main(String[] args){
        System.out.println("iniciando");

        try{
            MqttCliente mqttCliente = new MqttCliente("quicker1@quickexpress.com.co");
            mqttCliente.subscribeTopic("/quickexpress/pedidos");
        }catch (MqttException e) {
            System.out.println(e.getMessage());
        }

        Scanner input = new Scanner(System.in);
        String salir = "No";
        while(!salir.equals("Si")){
            System.out.println("salir");
            salir = input.next();
        }
    }
}