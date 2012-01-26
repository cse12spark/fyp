/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wifiBroadcast;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raj
 */
class bListener extends Thread{
    int port = 4002;
    public void display(String s)
    {
        System.out.println(s);
    }
    bListener()
    {
        display("Constructor");
        
    }
    public void run()
    {
        try
        {
        for (final Enumeration< NetworkInterface > interfaces = NetworkInterface.getNetworkInterfaces( );interfaces.hasMoreElements( );)
        {
            final NetworkInterface cur = interfaces.nextElement( );
            if ( cur.isLoopback( ) )
            {
                continue;
            }
            System.out.println( "interface " + cur.getName( ) );
            for ( final InterfaceAddress addr : cur.getInterfaceAddresses( ) )
            {
                final InetAddress inet_addr = addr.getAddress( );
                if ( !( inet_addr instanceof Inet4Address ) )
                {
                    continue;
                }
               System.out.println("  address: " + inet_addr.getHostAddress( ) +"/" + addr.getNetworkPrefixLength( ));
               byte [] mac = cur.getHardwareAddress();
               StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		}
		System.out.println(sb.toString());
               System.out.println("  broadcast address: " +addr.getBroadcast( ).getHostAddress( ));
               DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            buf = "Sending to server".getBytes();
            DatagramPacket packet = new DatagramPacket(buf,buf.length,InetAddress.getByName(inet_addr.getHostAddress()),port);
            socket.send(packet);
            socket.close();
            }
        }

        }catch(Exception asd)
        {
            display(asd.toString());
        }
}
}

public class broadcastListener
{
    bListener bl;
    broadcastListener()
    {
        bl = new bListener();
        bl.start();
    }
    public static void main(String args[])
    {
        new broadcastListener();
    }

}
