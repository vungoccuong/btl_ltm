/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Server;
import Server.entities.Room;
import Server.exceptions.ReadPacketException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import Server.interfaces.Middleware;
import Server.interfaces.Packet;
import Server.interfaces.User;
import java.io.ObjectOutputStream;

/**
 *
 * @author hirosume
 */
public class Client {

	private final Server server;
	private final Socket socket;
	private final ObjectInputStream in;
	private final ObjectOutputStream out;
	private User user;
	private Set<Room> rooms = new HashSet<>();
	private Packet packet;

	public Client(Socket socket, Server server) throws IOException {
		this.socket = socket;
		this.server = server;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	public void listen() throws IOException, ReadPacketException {

		while (!socket.isClosed()) {
			try {
				Packet packet = (Packet) in.readObject();
				boolean f = this.shouldContinue(packet);
				if (!f) {
					continue;
				}
				this.packet = packet;
				this.execute();
			} catch (IOException ex) {
				throw new ReadPacketException();
			} catch (ClassNotFoundException ex) {
				throw new ReadPacketException();
			}
		}

	}

	private void execute() {
		for (String name : this.server.getHandlers().keySet()) {
			if (name.equals(packet.getType())) {
				this.server.getHandlers().get(name).execute(this, server);
				break;
			}
		}
	}

	private boolean shouldContinue(Packet packet) {
		boolean f = true;
		for (Middleware m : this.server.getMiddlewares()) {
			f = m.execute(this, server, packet);
			if (!f) {
				return f;
			}
		}
		return f;
	}

	public void send(Packet packet) {
		try {
			out.writeObject(packet);
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public void close() throws IOException {
		for(Room r : rooms) {
			r.leave(this);
		}
		this.socket.close();
	}

	//get and set
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Packet getPacket() {
		return packet;
	}

}
