/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.interfaces;

import Server.Client;
import Server.Server;

/**
 *
 * @author hirosume
 */
public interface Middleware {
	boolean execute(Client client, Server server, Packet packet);
}
