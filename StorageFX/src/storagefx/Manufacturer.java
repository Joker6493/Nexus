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
            
    public void Manufacturer (int manufacturerID, String manufacturerName, String manufacturerCountry, String manufacturerTelephone, String manufacturerEmail){
        this.manufacturerName = manufacturerName;
        this.manufacturerCountry = manufacturerCountry;
        this.manufacturerTelephone = manufacturerTelephone;
        this.manufacturerEmail = manufacturerEmail;
    }
    //Get methods
    public int get_manufacturerID (){
        return manufacturerID;
    }
    public String get_manufacturerName (){
        return manufacturerName;
    }
    public String get_manufacturerCountry (){
        return manufacturerCountry;
    }
    public String get_manufacturerTelephone (){
        return manufacturerTelephone;
    }
    public String get_manufacturerEmail (){
        return manufacturerEmail;
    }
    //Set methods
    public void set_manufacturerID (){
        //In process    
    }
    public void set_manufacturerName (String manufacturerName){
        this.manufacturerName = manufacturerName;
    }
    public void set_manufacturerCountry (String manufacturerCountry){
        this.manufacturerCountry = manufacturerCountry;
    }
    public void set_manufacturerTelephone (String manufacturerTelephone){
        this.manufacturerTelephone = manufacturerTelephone;
    }
    public void set_manufacturerEmail (String manufacturerEmail){
        this.manufacturerEmail = manufacturerEmail;
    }
    
}
