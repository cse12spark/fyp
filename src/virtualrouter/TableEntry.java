/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualrouter;
import data.DataDetails.TypeOfNetwork;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sesha
 */
public class TableEntry 
{
    public String destinationIP;
    public TypeOfNetwork networkType;
    public float expertRating;
    public String expertDomain;
    public TableEntry()
    {
        destinationIP = null;
        networkType=null;
        expertRating = (float)0.0;
        expertDomain = null;
    }
}
