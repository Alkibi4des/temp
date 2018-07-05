package org.testnh.myapplication22;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReservationItemView extends LinearLayout{
    TextView tv_orderNo, tv_orderDay, tv_roomType, tv_checkInDay, tv_checkOutDay, tv_roomNo, tv_price;
    Button btn_cancel;

    ReservationItemView(Context ctx){
        super(ctx);
        init(ctx);
    }


    public void init(Context ctx){
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.reservation_item_1, this, true);

        this.tv_orderNo = findViewById(R.id.tv_reservation_orderNo_HB);
        this.tv_orderDay = findViewById(R.id.tv_reservation_orderDay_HB);
        this.tv_roomType = findViewById(R.id.tv_reservation_roomType_HB);
        this.tv_checkInDay = findViewById(R.id.tv_reservation_checkInDay_HB);
        this.tv_checkOutDay = findViewById(R.id.tv_reservation_checkOutDay_HB);
        this.tv_roomNo = findViewById(R.id.tv_reservation_rooMo_HB);
        this.tv_price = findViewById(R.id.tv_reservation_amountPrice_HB);

        this.btn_cancel = findViewById(R.id.btn_reservation_cancel);

    }

    public void setOrderNo(int i){
        this.tv_orderNo.setText(String.valueOf(i));
    }
    public void setOrderDay(int i){
        this.tv_orderDay.setText(String.valueOf(i));
    }
    public void setRoomType(String str){
        this.tv_roomType.setText(str);
    }

    public void setCheckInDay(int i){
        this.tv_checkInDay.setText(String.valueOf(i));
    }
    public void setCheckOutDay(int i){
        this.tv_checkOutDay.setText(String.valueOf(i));
    }
    public void setRoomNo(int i){
        this.tv_roomNo.setText(String.valueOf(i)+"호");
    }
    public void setCancelBtnListener(OnClickListener listener){
        this.btn_cancel.setOnClickListener(listener);
    }

    public void setAmountPrice(int i){
        this.tv_price.setText(String.valueOf(i)+"원");
    }
}
