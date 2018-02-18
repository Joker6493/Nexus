/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

/**
 *
 * @author dboro
 */
public class Manufacturer {
    protected int manufacturerID;
    protected String manufacturerName;
    protected String manufacturerCountry;
    protected String manufacturerTelephone;
    protected String manufacturerEmail;
            
Manufacturer (int manufacturerID, String manufacturerName, String manufacturerCountry, String manufacturerTelephone, String manufacturerEmail){
        this.manufacturerName = manufacturerName;
        this.manufacturerCountry = manufacturerCountry;
        this.manufacturerTelephone = manufacturerTelephone;
        this.manufacturerEmail = manufacturerEmail;
    }
    //Get methods
    public int getManufacturerID (){
        return manufacturerID;
    }
    public String getManufacturerName (){
        return manufacturerName;
    }
    public String getManufacturerCountry (){
        return manufacturerCountry;
    }
    public String getManufacturerTelephone (){
        return manufacturerTelephone;
    }
    public String getManufacturerEmail (){
        return manufacturerEmail;
    }
    //Set methods
    public void setManufacturerID (){
        //In process    
    }
    public void setManufacturerName (String manufacturerName){
        this.manufacturerName = manufacturerName;
    }
    public void setManufacturerCountry (String manufacturerCountry){
        this.manufacturerCountry = manufacturerCountry;
    }
    public void setManufacturerTelephone (String manufacturerTelephone){
        this.manufacturerTelephone = manufacturerTelephone;
    }
    public void setManufacturerEmail (String manufacturerEmail){
        this.manufacturerEmail = manufacturerEmail;
    }
    
}
