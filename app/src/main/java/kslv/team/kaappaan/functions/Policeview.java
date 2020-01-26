package kslv.team.kaappaan.functions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kslv.team.kaappaan.R;

public class Policeview extends AppCompatActivity {

    Spinner  pvdistrict,pvps;
    ListView lv;

    ArrayList<String> dis=new ArrayList<>();
    ArrayList<String> ps=new ArrayList<>();
    ArrayList<String> list=new ArrayList<>();

    String dist,police,madhya,selnumber;
    String aadhaar,addr,date,day,details,father,fir,mobile,name,psname,time,year;

    ArrayAdapter<String> disadap,psadap,listadap;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeview);


        TextView tvpoliceview=(TextView)findViewById(R.id.tvpoliceview);
        TextView tvpvdistrict=(TextView)findViewById(R.id.tvpvdistrict);
        TextView tvpvpolicestation=(TextView)findViewById(R.id.tvpvpolicestation);
        pvdistrict=(Spinner)findViewById(R.id.spinnerpvdistrict);
        pvps=(Spinner)findViewById(R.id.spinnerpvpolicestation);

        lv=findViewById(R.id.lv);

        myRef.child("fir-data").addValueEventListener(new ValueEventListener() {
                                                          @Override
                                                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                              Log.e("fircount",dataSnapshot.getChildrenCount()+"yes");
                                                              for(DataSnapshot d1:dataSnapshot.getChildren())
                                                              {
                                                                  String obj=d1.getKey();
                                                                  // train.add(obj);


                                                                  Log.e("dis",dis+"  "+obj);
                                                                  if(obj.contains("madhya pradesh"))
                                                                  {

                                                                      Log.e("fircount1",dataSnapshot.child(obj).getChildrenCount()+"yes");
                                                                      for(DataSnapshot d2:dataSnapshot.child(obj).getChildren())
                                                                      {
                                                                          String obj1=d2.getKey();
                                                                          dis.add(obj1);

                                                                      }
                                                                  }
                                                                  // Log.e("msg",obj);

                                                              }
                                                              //String data=dataSnapshot.getValue().toString();
                                                              //subcatdata=data.split(",");

                                                              disadap = new ArrayAdapter<String>
                                                                      (Policeview.this,android.R.layout.simple_spinner_dropdown_item,dis);
                                                              pvdistrict.setAdapter(disadap);
                                                          }

                                                          @Override
                                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                                          }
                                                      });


        pvdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dist=pvdistrict.getSelectedItem().toString();
                ps.clear();
                myRef.child("fir-data").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("fircount",dataSnapshot.getChildrenCount()+"yes");
                        for(DataSnapshot d1:dataSnapshot.getChildren())
                        {
                            String obj1=d1.getKey();
                            // train.add(obj);


                            Log.e("dis",dist);
                            if(obj1.contains("madhya pradesh"))
                            {
                                madhya=obj1;
                                Log.e("madhya",madhya);
                                for(DataSnapshot d2:dataSnapshot.child(obj1).child(dist).getChildren())
                                {
                                    String obj2=d2.getKey();
                                    Log.e("ps",obj2);
                                    ps.add(obj2);
                                }
                            }
                            // Log.e("msg",obj);

                        }
                        //String data=dataSnapshot.getValue().toString();
                        //subcatdata=data.split(",");

                        psadap = new ArrayAdapter<String>
                                (Policeview.this,android.R.layout.simple_spinner_dropdown_item,ps);
                        pvps.setAdapter(psadap);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pvps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                police=pvps.getItemAtPosition(i).toString();
                list.clear();
                myRef.child("fir-data").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       // Log.e("fircount",dataSnapshot.getChildrenCount()+"yes");
                        for(DataSnapshot d1:dataSnapshot.getChildren())
                        {
                            String obj1=d1.getKey();
                            // train.add(obj);


                            //Log.e("dis",dist);
                            if(obj1.contains("madhya pradesh"))
                            {
                                for(DataSnapshot d2:dataSnapshot.child(obj1).child(dist).child(police).child("fir").getChildren())
                                {

                                    String obj2=d2.getKey();
                                    if(!obj2.equals("location") && !obj2.equals("title"))
                                    {
                                        Log.e("num", obj2);
                                        list.add(obj2);
                                    }
                                }
                            }
                            // Log.e("msg",obj);

                        }
                        //String data=dataSnapshot.getValue().toString();
                        //subcatdata=data.split(",");

                        listadap = new ArrayAdapter<String>
                                (Policeview.this,android.R.layout.simple_spinner_dropdown_item,list);
                        lv.setAdapter(listadap);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selnumber=lv.getItemAtPosition(i).toString();



                myRef=myRef.child("fir-data").child(madhya).child(dist).child(police).child("fir").child(selnumber);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                       // Log.e("cur",dataSnapshot.toString());
                        // String aadhaar,addr,date,day,details,father,fir,mobile,name,psname,time,year;
                        Intent intent=new Intent(Policeview.this,Preview.class);

                        aadhaar=dataSnapshot.child("aadhaar").getValue().toString();
                        addr=dataSnapshot.child("address").getValue(String.class).toString();
                        date=dataSnapshot.child("date").getValue(String.class).toString();
                        day=dataSnapshot.child("day").getValue(String.class).toString();
                        details=dataSnapshot.child("details").getValue(String.class).toString();
                        father=dataSnapshot.child("father").getValue(String.class).toString();
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        final SharedPreferences.Editor editor = pref.edit();
                        editor.putString("aadhaar",aadhaar);
                        editor.putString("addr",addr);
                        editor.putString("date",date);
                        editor.putString("day",day);
                        editor.commit();
                        startActivity(intent);

                        Log.e("date",aadhaar);
//                        for(DataSnapshot d1:dataSnapshot.getChildren())
//                        {
//                            String obj=d1.getKey();
//                            Log.e("date", obj);
//                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


                //intent.putExtra("date",aadhaar);

            }
        });


    }
}
