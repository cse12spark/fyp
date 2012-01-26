/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nametobekept;
import bluetoothpackage.DeviceDiscovery;
import data.DataDetails.TypeOfNetwork;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.bluetooth.DiscoveryAgent;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import org.hsqldb.jdbcDriver;
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
    public NodeSetup() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        LocalDevice device = null;
        File f = new File("LocalDevice.data");
        if(!f.exists())
        {
            //First time. Set the properties and write to the file
            Vector<String> domainNames = getAvailabledomainNames();
            //Domain Name got... can be used to register
            Iterator it = domainNames.iterator();
            while(it.hasNext())
            {
                String domain = (String)it.next();
                System.out.println(domain);
            }
            if(f.createNewFile())
            {
               //device = new LocalDevice("Name", "Age");
               device = new LocalDevice("1290",TypeOfNetwork.BLUETOOTH,"something");
               device.domains = domainNames;
               FileOutputStream fos = new FileOutputStream(f);
               ObjectOutputStream oos = new ObjectOutputStream(fos);
               Cipher c = Cipher.getInstance("DES");
               oos.writeObject(device);
               oos.close();
               fos.close();
               
               f.setWritable(false);
               Process p = Runtime.getRuntime().exec("attrib +H " + f.getPath());
            }
        }
        else
        {
            //Not the first time... so just set the localdevice property..just take to UI..
            System.out.println("File Already Exists..Deleting");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            try
            {
                device =(LocalDevice)ois.readObject();
                //System.out.println("Name:"+device.name+" Age: "+device.age);
                fis.close();
                ois.close();
                f.delete();
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println(ex.toString());
            }
            
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
        Properties props=new Properties();
        props.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
        props.setProperty("javax.jdo.option.ConnectionDriverName", "org.hsqldb.jdbcDriver");
        props.setProperty("javax.jdo.option.ConnectionURL", "jdbc:hsqldb:mem:nucleus1");
        props.setProperty("javax.jdo.option.ConnectionUserName", "sa");
        props.setProperty("javax.jdo.option.ConnectionPassword", "");
        props.setProperty("javax.jdo.option.Mapping","hsql");
        props.setProperty("datanucleus.autoCreateSchema", "true");
        props.setProperty("datanucleus.validateTables", "false");
        props.setProperty("datanucleus.validateConstraints", "false");
        pmf = JDOHelper.getPersistenceManagerFactory(props);
        PersistenceManager manager = pmf.getPersistenceManager();
        manager.currentTransaction().begin();
        Query query=manager.newQuery("select LocalDevice.class.getName()");
        LocalDevice device = null;
        device = new LocalDevice("My Device","2");
        Collection products;
            products=(Collection)query.execute();
            Iterator iterator = products.iterator();
            if(!iterator.hasNext())
            {
                System.out.println("No product found...Adding to persistent storage");
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
/*
        
 */