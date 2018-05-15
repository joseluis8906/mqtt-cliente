package com.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.List;

public class MqttCliente extends MqttAsyncClient implements MqttCallback {

    private static String broker = "tcp://quickexpress.josecaceres.me:1883";
    private String clientId;
    private static Integer QoS = 2;
    private List<String> subscribedTopics;
    private MqttConnectOptions connectOptions;
    private IMqttToken token;
    private static MemoryPersistence memoryPersistence = new MemoryPersistence();

    public MqttCliente(String clientId) throws MqttException {
        super(broker, clientId, memoryPersistence);
        this.clientId = clientId;

        connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(true);
        connectOptions.setUserName("quickexpress");
        String password = "ADMIN12345";
        connectOptions.setPassword(password.toCharArray());

        token = this.connect(connectOptions);
        token.waitForCompletion(3000);
        setCallback(this);
    }

    public void connectionLost(Throwable error) {
        try{
            this.connect(connectOptions);
        }catch (MqttException exeption){
            System.out.println(exeption.getMessage());
        }
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String msg = new String(message.getPayload());
        switch (msg){
            case "Nuevo Pedido": {
                System.out.println("Voy a actualizar lista de pedidos");
                break;
            }

            case "Nuevo Estado": {
                System.out.println("Voy a actualizar estados");
                break;
            }

            default:
                break;
        }
    }

    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void sendMessage(String topic, String msj) {

        MqttMessage mqttMessage = new MqttMessage(msj.getBytes());
        mqttMessage.setQos(QoS);

        try {
            token = publish(topic, mqttMessage);
            token.waitForCompletion(1000);
        }
        catch (MqttException e) {
            System.out.println(e.getReasonCode());
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getCause());
        }
    }

    public void subscribeTopic(String topic){
        try{
            token = subscribe(topic, QoS);
            token.waitForCompletion(3000);
        }catch (MqttException exception){

        }

    }
}
