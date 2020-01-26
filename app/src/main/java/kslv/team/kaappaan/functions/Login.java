package kslv.team.kaappaan.functions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kslv.team.kaappaan.R;
import kslv.team.kaappaan.home;
//this file is edited by lakshmi narayanan

public class Login extends AppCompatActivity {

    EditText etname,etfather,etmobile,etaddr,etaadhaar;
    String name;
    String father,mobile,addr,aadhaar;

    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etname=findViewById(R.id.etusername);
        etfather=findViewById(R.id.etfathername);
        etmobile=findViewById(R.id.etmobileno);
        etaddr=findViewById(R.id.etaddr);
        etaadhaar=findViewById(R.id.etaadhaar);

        next=findViewById(R.id.next);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               name= etname.getText().toString();
               father=etfather.getText().toString();
               mobile=etmobile.getText().toString();
               addr=etaddr.getText().toString();
               aadhaar=etaadhaar.getText().toString();


               editor.putString("name",name);
               editor.putString("father",father);
               editor.putString("mobile",mobile);
               editor.putString("addr",addr);
               editor.putBoolean("login",true);
               editor.putString("aadhaar",aadhaar);
               editor.commit();


                myRef.child("user-data").child(aadhaar).child("aadhaar").setValue(aadhaar);
                myRef.child("user-data").child(aadhaar).child("name").setValue(name);

                myRef.child("user-data").child(aadhaar).child("father").setValue(father);
                myRef.child("user-data").child(aadhaar).child("mobile").setValue(mobile);


                myRef.child("user-data").child(aadhaar).child("addr").setValue(addr);



                Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
               Intent i=new Intent(Login.this, home.class);
               startActivity(i);
               finish();


            }
        });


    }
}
