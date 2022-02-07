package com.demo;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Publisher{
	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageProducer sender;
	private ActiveMQConnectionFactory connectionFactory;

	private String brokerUrl;
	private String username;
	private String password;
	private String queueName;

	public Publisher()

	{
		this.brokerUrl = "tcp://localhost:61616";
		this.username = "admin";
		this.password = "admin";
		this.queueName = "HelloWorldQ";

		connectionFactory = new ActiveMQConnectionFactory(this.username, this.password, this.brokerUrl);

	}

	public void initQueueConnection() throws Exception {
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue(queueName);
		sender = session.createProducer(queue);
	}

	public void sendMsgToQueue(String serviceInput) throws Exception {
		initQueueConnection();
		TextMessage requestTextMessage = session.createTextMessage(serviceInput);
		sender.send(requestTextMessage);
		sender.close();
		session.close();
		connection.close();

	}

	public static void main(String[] args)

	{
		Publisher pub = new Publisher();
		//System.out.println("In here");
		try {
			pub.sendMsgToQueue("Hi I did it");
		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

}
