/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storagefx;

/**
 *
 * @author d.borodin
 */
public class Stock {
    //Class for keeping info about stock
    private int stockID;
    private String stockName;
    public Stock(int stockID, String stockName) {
        this.stockID = stockID;
        this.stockName = stockName;
    }
    public Stock(String stockName) {
        this.stockName = stockName;
    }
    public int getStockID() {
        return stockID;
    }
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }
    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
