/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nametobekept;
import bluetoothpackage.DeviceDiscovery;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.bluetooth.DiscoveryAgent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

/**
 *
 * @author Sesha
 */
public final class NodeSetup 
{
    PersistenceManagerFactory pmf=null;
    public Vector<String> getAvailabledomainNames()
    {
        try
        {
            Vector<String> domainNames = new Vector<String>();
            URL url = new URL("http://localhost/fyp/domains.php?method=getDomainNames");
            URLConnection con = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp;
            while((temp=br.readLine())!=null)
            {
                domainNames.add(temp);
            }
            return domainNames;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        return null;
    }
    public NodeSetup() throws IOException
    {
        File f = new File("localdevice.properties");
        if(!f.exists())
        {
            //First time. Set the properties and write to the file
            Vector<String> domainNames = getAvailabledomainNames();
            Iterator it = domainNames.iterator();
            while(it.hasNext())
            {
                String domain = (String)it.next();
                System.out.println(domain);
            }
        }
        else
        {
            //Not the first time... so just set the localdevice property..just take to UI..
            
        }
        //System.out.println("Searching for Devices....");
        //DeviceDiscovery ob = new DeviceDiscovery(DiscoveryAgent.GIAC);
        //ob.discoverDevices();
    }
    public static void main(String[] args) throws IOException 
    {
        // TODO code application logic here
        System.out.println("Code Started");
        NodeSetup ob = new NodeSetup();
    }
}

/*
        Properties props = new Properties();
        props.setProperty("javax.jdo.PersistenceManagerFactoryClass","javax.jdo.JDOHelper");
        pmf = JDOHelper.getPersistenceManagerFactory(props);
        PersistenceManager manager = pmf.getPersistenceManager();
        manager.currentTransaction().begin();
        Query query=manager.newQuery(LocalDevice.class);
        Collection products = (Collection)query.execute();
        Iterator iterator = products.iterator();
        if(!iterator.hasNext())
        {
            System.out.println("No product found...Adding to persistent storage");
            LocalDevice device = new LocalDevice("My Device");
            Transaction tx=manager.currentTransaction();
            tx.begin();
            manager.makePersistent(device);
            tx.commit();
        }
        else
        {
            System.out.println("Product found");
        }

 */