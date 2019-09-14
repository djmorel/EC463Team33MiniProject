package team33.ec463.com.ec463team33miniproject;

import java.util.concurrent.TimeUnit;

public class Device {
    String name;
    String type;
    String idVal;
    String room;
    boolean isActive;
    static double data;

    public Device(){}

    public void setName(String newName){ name = newName; }

    public void setType(String newType) { type = newType; }

    public void setIdVal(String newID) { idVal = newID; }

    public void setRoom(String newRoom) { room = newRoom; }

    public String getName(){
        return name;
    }

    public String getIdVal(){
        return idVal;
    }

    public String getType(){
        return type;
    }

    public String getRoom() { return room; }

    public Device(String myName, String myType, String myIdVal, String myRoom){
        name = myName;
        type = myType;
        idVal = myIdVal;
        room = myRoom;
        isActive = true;
    }

    public void generateTempData() throws InterruptedException{
        double max = 75.0;
        double min = 70.0;
        while(isActive){
            data = (Math.random() * (max - min)) + min;
            TimeUnit.MINUTES.sleep(30);
        }
    }

    public void generateHumidData() throws InterruptedException{
        double max = 40.0;
        double min = 35.0;
        while(isActive){
            data = (Math.random() * (max - min)) + min;
            TimeUnit.MINUTES.sleep(30);
        }
    }

    public void endDevice(){
        isActive = false;
        data = 0;
    }
}
