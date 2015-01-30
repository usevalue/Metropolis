package net.usevalue.metropolis.plots;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.towns.Town;

public class TownPlot implements Plot {

    private ProtectedRegion region;
    private Town town;

    private String type = "residence";
    private boolean buyable = false;
    private boolean rentable = false;
    private double buyPrice = 0;
    private double rentPrice = 0;

    public TownPlot(ProtectedRegion region, Town town) {
        this.region=region;
        this.town=town;
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public Town getTown() {
        return town;
    }

    public String getType() {
        return type;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable=buyable;
    }

    public boolean isRentable() {
        return rentable;
    }

    public void setRentable(boolean rentable) {
        this.rentable=rentable;
    }

    public double getPrice() {
        return buyPrice;
    }

    public boolean setPrice(double price) {
        if(price>=0) {
            buyPrice = price;
            return true;
        }
        return false;
    }

    public double getRent() {
        return rentPrice;
    }

    public boolean setRent(double rent) {
        if(rent>=0) {
            rentPrice=rent;
            return true;
        }
        return false;
    }

}