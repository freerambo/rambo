package websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 *                 accessed via ws://localhost:8080/EchoChamber/echo Where
 *                 "localhost" is the address of the host, "EchoChamber" is the
 *                 name of the package and "echo" is the address to access this
 *                 class from the server
 *                
 */
@ServerEndpoint("/websocket")
public class EchoServer {

	private final static ConcurrentHashMap<String, EchoServer> sockets = new ConcurrentHashMap<String, EchoServer>();
	private Session session;
	private String uniqueId;

	
	/**
	 * 
	 * @return unique ID from this class' hash code
	 */
	private String getUniqueId() {
		// return Integer.toHexString(this.hashCode());
		// return the session id
		return this.session.getId();
	}

	/**
	 * sent mssage to client
	 * 
	 * @param str
	 */
	private void sendClient(String str) {
		try {
			if(this.session != null)
				this.session.getBasicRemote().sendText(str);
			else 
				System.out.println("Please open the session before sending a msg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private void sendError(String err) { this.sendClient(String.format(
	 * "{\"msg\": \"error\", \"error\": \"%s\"}", err)); }
	 */

	/**
	 * @OnOpen allows us to intercept the creation of a new session. The session
	 *         class allows us to send data to the user. In the method onOpen,
	 *         we'll let the user know that the handshake was successful.
	 */
	@OnOpen
	public void onOpen(Session session) {
		// save session so we can send
		this.session = session;
		// this unique ID
		this.uniqueId = this.getUniqueId();
		System.out.println(uniqueId + " has opened a connection");
		// map this unique ID to this connection
		this.sockets.put(this.getUniqueId(), this);
		this.sendClient(uniqueId + " Connection Established");

		for (EchoServer socket : sockets.values()) {
			if (socket != this)
				socket.sendClient(uniqueId + " Connection Established");
		}
		/*
		 * try { session.getBasicRemote().sendText("Connection Established"); }
		 * catch (IOException ex) { ex.printStackTrace(); }
		 */
	}

	/**
	 * When a user sends a message to the server, this method will intercept the
	 * message and allow us to react to it. For now the message is read as a
	 * String.
	 */
	@OnMessage
	public void onMessage(String message) {

		System.out.println("Message from " + session.getId() + ": " + message);
		this.sendClient("Message from server: session ID : " + session.getId()
				+ " you sent " + message);
		EchoServer socket = this.sockets.get(message);
		if (socket != null) {
			socket.sendClient(this.uniqueId + " send to " + socket.uniqueId
					+ " a message");
		}else{
			System.out.println("Invalid Message from " + session.getId());
		}

		/*
		 * try { session.getBasicRemote().sendText(
		 * "Message from server: session ID : " + session.getId() + " you sent "
		 * + message); } catch (IOException ex) { ex.printStackTrace(); }
		 */
	}

	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + this.uniqueId + " has ended");
		sockets.remove(this.uniqueId);
		for (EchoServer socket : sockets.values()) {
			/*if (socket != this){
				socket.sendClient(uniqueId + " Connection closed");				
			}*/
			socket.sendClient(uniqueId + " Connection closed");
		}
		this.session = null;
	}
}