package kslv.team.kaappaan.functions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kslv.team.kaappaan.R;
import kslv.team.kaappaan.home;

public class complaint extends AppCompatActivity {

    //editext
    EditText date,time;
    EditText details;

    //autocomplete
    AutoCompleteTextView place,district,ps;

    String dated,timed,detailsd,placed,districtd,psd,comd,subcomd;


    List<Address> addresses;
    Geocoder geocoder;
    Double lat,lng;
    String city;

    ArrayList<String> catdata=new ArrayList<>();
    ArrayList<String> pols=new ArrayList<>();
    //ArrayList<String> subcatdata=new ArrayList<>();
    String[] subcatdata;

    private FusedLocationProviderClient client;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Button next;



    //spinner
    Spinner cat,subcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        //edittext
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        details=findViewById(R.id.details);

        //autocomplete
        place=findViewById(R.id.place);
        district=findViewById(R.id.district);
        ps=findViewById(R.id.ps);

        //spinner
        cat=findViewById(R.id.cat);
        subcat=findViewById(R.id.subcat);

        next=findViewById(R.id.next);



        Date currentTime = Calendar.getInstance().getTime();



        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdf2= new SimpleDateFormat("hh:mm a", Locale.getDefault());
//You can change "yyyyMMdd_HHmmss as per your requirement

        String currentdate = sdf.format(new Date());
        String currenttime = sdf2.format(new Date());


        date.setText(currentdate);
        time.setText(currenttime);

        client = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        // editadd.setText("Tracing Location,Please Wait");
        client.getLastLocation()
                .addOnSuccessListener(complaint.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            try {
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                                Log.e("latlong",lat+"yes"+lng);
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                String address=addresses.get(0).getAddressLine(0);
                                city = addresses.get(0).getSubAdminArea();
                                //int spinnerPosition = spinnerArrayAdapter.getPosition(city);
                                //spinner.setSelection(spinnerPosition);
                                //Log.e("city is", city);
                                place.setText(address);
                                district.setText(city);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


       // myRef.setValue("Hello, World!");

        district.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {




            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
               // field2.setText("");
                pols.clear();
                final String dis=s.toString();
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
                                if (obj1.contains(dis)) {
                                    Log.e("fircount2",dataSnapshot.child(obj).child(obj1).getChildrenCount()+"yes");
                                    for (DataSnapshot d3 : dataSnapshot.child(obj).child(obj1).getChildren()) {
                                        String obj2 = d3.getKey();
                                        obj2 = obj2.split(",")[0];
                                        Log.e("ps",obj2);
                                        pols.add(obj2);
                                    }
                                }
                                }
                            }
                            // Log.e("msg",obj);

                        }
                        //String data=dataSnapshot.getValue().toString();
                        //subcatdata=data.split(",");

                        ArrayAdapter<String> adapterpols = new ArrayAdapter<String>
                                (complaint.this,android.R.layout.simple_spinner_dropdown_item,pols);
                        ps.setAdapter(adapterpols);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        int i=0;
        myRef.child("category-data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("count",dataSnapshot.getChildrenCount()+"yes");
                for(DataSnapshot d1:dataSnapshot.getChildren())
                {
                    String obj=d1.getKey();
                   // train.add(obj);

                   catdata.add(obj);
                  // Log.e("msg",obj);

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (complaint.this,android.R.layout.simple_spinner_dropdown_item,catdata);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //Setting the ArrayAdapter data on the Spinner
                cat.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("count",cat.getSelectedItem().toString());
                myRef.child("category-data").child(cat.getSelectedItem().toString()).child("category").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("count",dataSnapshot.getChildrenCount()+"yes");
//                        for(DataSnapshot d1:dataSnapshot.getChildren())
//                        {
//                            String obj=d1.getKey();
//                            // train.add(obj);
//
//                            subcatdata.add(obj);
//                            // Log.e("msg",obj);
//
//                        }
                        String data=dataSnapshot.getValue().toString();
                        subcatdata=data.split(",");

                        ArrayAdapter<String> adaptersub = new ArrayAdapter<String>
                                (complaint.this,android.R.layout.simple_spinner_dropdown_item,subcatdata);
                        subcat.setAdapter(adaptersub);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner


        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dated=date.getText().toString();
                timed=time.getText().toString();
                placed=place.getText().toString();

                districtd=district.getText().toString();
                psd=ps.getText().toString();
//                comd=cat.getSelectedItem().toString();
//
//                subcomd=subcat.getSelectedItem().toString();
                detailsd=details.getText().toString();

//                editor.putString("date",dated);
//                editor.putString("time",timed);
//                editor.putString("place",placed);
//                editor.putString("district",districtd);
//                editor.putString("ps",psd);
//                editor.putString("cat",comd);
//                editor.putString("subcat",subcomd);
//                editor.putString("details",detailsd);
//                editor.apply();

                Intent intent=new Intent(complaint.this, Preview.class);
                startActivity(intent);
            }
        });



    }
}
