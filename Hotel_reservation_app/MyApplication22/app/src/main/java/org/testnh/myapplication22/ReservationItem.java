package org.testnh.myapplication22;

public class ReservationItem {
    int orderNo;
    int roomNo;
    int orderDay;
    int checkInDay;
    int checkOutDay;
    int amountPrice;
    String roomType;

    public ReservationItem(int oNo, String rType, int rNo, int cInDay, int cOutDay, int oDay){
        this.orderNo = oNo;
        this.orderDay = oDay;
        this.checkInDay = cInDay;
        this.checkOutDay = cOutDay;
        this.roomType = rType;
        this.roomNo = rNo;
        switch (roomType){
            case "스위트" : this.amountPrice=(checkOutDay-checkInDay) * 200000; break;
            case "디럭스" : this.amountPrice=(checkOutDay-checkInDay) * 150000; break;
            case "스탠다드" : this.amountPrice=(checkOutDay-checkInDay) * 100000; break;
            default : break;
        }

    }

    public int getOrderNo() {
        return orderNo;
    }

    public int getOrderDay() {
        return orderDay;
    }

    public int getCheckInDay() {
        return checkInDay;
    }

    public int getCheckOutDay() {
        return checkOutDay;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setOrderDay(int orderDay) {
        this.orderDay = orderDay;
    }

    public void setCheckInDay(int checkInDay) {
        this.checkInDay = checkInDay;
    }

    public void setCheckOutDay(int checkOutDay) {
        this.checkOutDay = checkOutDay;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }
}
