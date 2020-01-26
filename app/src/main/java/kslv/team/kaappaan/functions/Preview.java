package kslv.team.kaappaan.functions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import kslv.team.kaappaan.R;

public class Preview extends AppCompatActivity {
    TextView tvdis,tvyear,tvfirno,tvps,tvacts,tvoooday,tvooodate,tvoootime,tvinfday,tvinfdate,tvinftime,tvpooaddr,tvinforname,tvinforfather;
    TextView tvinformobileno,tvinforaadhar,tvinforaddr,tvpredetail,tvinforsign,tvpolicesign,tvdesigpolice;

     String aadhaar,addr,date,day,details,father,fir,mobile,name,psname,time,year;

   // Bundle extras = getIntent().getExtras();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);


        TextView tv=(TextView)findViewById(R.id.tvfir);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        Intent intent = getIntent();
        aadhaar=pref.getString("date","");
        addr=pref.getString("addr","");
        date=pref.getString("date","");
        day=pref.getString("day","");
        Log.e("date",aadhaar);


        tvdis=(TextView)findViewById(R.id.tventerdistrict);
        tvyear=(TextView)findViewById(R.id.tventeryear);
        tvfirno=(TextView)findViewById(R.id.tventerfirno);
        tvps=(TextView)findViewById(R.id.tventerps);
        tvacts=(TextView)findViewById(R.id.tventeracts);
        tvoooday=(TextView)findViewById(R.id.tvoooenterday);
        tvooodate=(TextView)findViewById(R.id.tvoooenterdate);
        tvoootime=(TextView)findViewById(R.id.tvoooentertime);
        tvdis=(TextView)findViewById(R.id.tventerdistrict);
        tvinfday=(TextView)findViewById(R.id.tvirpsenterday);
        tvinfdate=(TextView)findViewById(R.id.tvirpsenterdate);
        tvinftime=(TextView)findViewById(R.id.tvirpsentertime);
        tvpooaddr=(TextView)findViewById(R.id.tvpooenteraddr);
        tvinforname=(TextView)findViewById(R.id.tv6entername);
        tvinforfather=(TextView)findViewById(R.id.tv6enterfather);
        tvinformobileno=(TextView)findViewById(R.id.tv6entermobileno);
        tvinforaadhar=(TextView)findViewById(R.id.tv6enteraadharno);
        tvinforaddr=(TextView)findViewById(R.id.tv6enteraddr);
        tvpredetail=(TextView)findViewById(R.id.tv7enterdetail);
        tvinforsign=(TextView)findViewById(R.id.tventersignofinformant);
        tvpolicesign=(TextView)findViewById(R.id.tventersignofofficer);
        tvdesigpolice=(TextView)findViewById(R.id.tventerdesignationofofficer);

        tvinforaadhar.setText(aadhaar);
        tvinforaddr.setText(addr);
        tvinfdate.setText(date);
        tvinfday.setText(day);
    }
}
