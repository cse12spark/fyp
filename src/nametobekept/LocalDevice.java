/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nametobekept;

import data.DataDetails.TypeOfNetwork;
import java.io.Serializable;
import java.util.Stack;
import java.util.Vector;
import javax.jdo.annotations.PersistenceCapable;

/**
 *
 * @author Sesha
 */
public class LocalDevice implements Serializable
{
    private String address=null;
    private String hardwareAddress=null;
    public TypeOfNetwork networkType;
    public Vector<String> domains=null;
    public Vector<Double> expertLevel=null;
    public LocalDevice(String address,TypeOfNetwork networkType,String hardwareAddress)
    {
        
        domains = new Vector<String>();
        expertLevel = new Vector<Double>();
        this.address = address;
        this.networkType = networkType;
        this.hardwareAddress = hardwareAddress;
    }
    public String getAddress() { return address; }
    public String getHardwareAddress() { return hardwareAddress; }
}
