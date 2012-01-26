/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wifiBroadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raj
 */
public class broadcastServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException
    {
        try {
            // TODO code application logic here
            int port = 4002;
            DatagramSocket socket = null;
            //InetAddress address = InetAddress.getByName("localhost");
            socket = new DatagramSocket(port);
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            try {
                socket.receive(packet);
                packet.getAddress().getHostAddress();
                if(packet.getLength()>0)
                {
                    String received = new String(packet.getData(),0,packet.getLength());
                    System.out.print("Received : " + received);
                    socket.close();
                }
                else
                    System.out.println("Nothing to be displayed");
            } catch (IOException ex) {
                Logger.getLogger(broadcastServer.class.getName()).log(Level.SEVERE, null, ex);
            }


        } catch (SocketException ex) {
            Logger.getLogger(broadcastServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
