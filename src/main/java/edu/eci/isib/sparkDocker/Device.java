package edu.eci.isib.sparkDocker;

public class Device {
    String id;
    String address;

    public Device() {
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Device [address=" + address + ", id=" + id + "]";
    }
}
