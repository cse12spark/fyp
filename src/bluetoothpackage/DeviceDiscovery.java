package bluetoothpackage;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.*;
import data.DataDetails.TypeOfNetwork;
import virtualrouter.RoutingTable;
import virtualrouter.TableEntry;
public class DeviceDiscovery implements DiscoveryListener,Runnable
{
    Vector<RemoteDevice> devicesFound = new Vector<RemoteDevice>();
    LocalDevice myDevice;
    DiscoveryAgent agent;
    final static Object syncObject = new Object();
    int code;
    private void exception(String ex)
    {
        System.out.println("Got Exception"+ex.toString());
    }
    
    public DeviceDiscovery(int code)
    {
        Thread t = new Thread(this);
        t.start();
        this.code = code;
    }
    public void discoverDevices()
    {
        try
        {
            synchronized(syncObject)
            {
                syncObject.wait();
                if(devicesFound.size()>0)
                {
                    for(Enumeration device=devicesFound.elements();device.hasMoreElements();)
                    {
                        RemoteDevice rd=(RemoteDevice)device.nextElement();
                        if(rd!=null)
                        {
                            System.out.println("Found device:"+rd.getBluetoothAddress()+" "+rd.getFriendlyName(true));
                            TableEntry entry = new TableEntry();
                            
                        }
                    }
                }
            }
        }
        catch(Exception ex)
        {
            exception(ex.toString());
        }
    }
    
    public static void main(String[] args) 
    {
        // TODO code application logic here
        System.out.println("Searching for Devices....");
        DeviceDiscovery ob = new DeviceDiscovery(DiscoveryAgent.GIAC);
        ob.discoverDevices();
    }

    @Override
    public void deviceDiscovered(RemoteDevice rd, DeviceClass dc) 
    {
        devicesFound.add(rd);
        //Can be added to table here...
        //addToTable(rd);
    }

    @Override
    public void servicesDiscovered(int i, ServiceRecord[] srs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void serviceSearchCompleted(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void inquiryCompleted(int i) 
    {
        synchronized(syncObject)
        {
            syncObject.notifyAll();
        }
    }

    @Override
    public void run() 
    {
        try
        {
            myDevice = LocalDevice.getLocalDevice();
            agent = myDevice.getDiscoveryAgent();
            RemoteDevice[] cachedDevices = agent.retrieveDevices(DiscoveryAgent.CACHED);
            for(int i=0;i<cachedDevices.length;i++)
            {
                System.out.println("Found the devices as cached:"+cachedDevices[i].getBluetoothAddress()+" and friendly name:" + cachedDevices[i].getFriendlyName(true));
            }
            agent.startInquiry(code, this);
        }
        catch(BluetoothStateException ex)
        {
            exception(ex.toString());
        }
        catch(Exception ex)
        {
            exception(ex.toString());
        }
    }
}
