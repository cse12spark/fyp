package virtualrouter;

import Data.DataDetails.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

class TableEntry
{
    String destinationIP;
    TypeOfNetwork networkType;
    float expertRating;
    String expertDomain;
}

public abstract class RoutingTable
{
    private static HashMap<String, TableEntry> IPMapper=new HashMap<String, TableEntry>();
    //IPMapper Maps IP to the TableEntry class
    public static void addTableEntry(String sourceIP,TableEntry entry)
    {
        if(!IPMapper.containsKey(sourceIP))
        {
            IPMapper.put(sourceIP, entry);
        }
    }
    public static void updateTableEntry(String sourceIP,TableEntry newEntry)
    {
        if(IPMapper.containsKey(sourceIP))
        {
            IPMapper.remove(sourceIP);
            IPMapper.put(sourceIP, newEntry);
        }
    }
    public static void removeEntry(String sourceIP)
    {
        if(IPMapper.containsKey(sourceIP))
            IPMapper.remove(sourceIP);
    }
}
