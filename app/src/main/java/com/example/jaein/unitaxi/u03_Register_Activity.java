package com.example.jaein.unitaxi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.jaein.unitaxi.u02_Login_Activity.db_member;

public class u03_Register_Activity extends AppCompatActivity {

    EditText id,name,passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u03_register);

        init();
    }

    private void init() {
        id = (EditText)findViewById(R.id.JoinId);
        name = (EditText)findViewById(R.id.JoinName);
        passwd = (EditText)findViewById(R.id.JoinPass);
    }

//    private void goto_Login() {
//        Intent i_Login = new Intent(u03_Register_Activity.this, u02_Login_Activity.class);
//        startActivity(i_Login);
//    }
    public void checkId(View view){
        final String str_id = id.getText().toString();

        Query id_query = db_member.orderByChild("member_id").equalTo(str_id);
        id_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {//사용가능
                    Toast.makeText(u03_Register_Activity.this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(u03_Register_Activity.this, "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void checkName(View view){
        final String str_name = id.getText().toString();

        Query id_query = db_member.orderByChild("member_name").equalTo(str_name);
        id_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {//사용가능
                    Toast.makeText(u03_Register_Activity.this, "사용가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(u03_Register_Activity.this, "이미 존재하는 닉네임입니다", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void RegisterToLogin(View view) {
        final String str_id = id.getText().toString();
        final String str_pw = passwd.getText().toString();
        final String str_name = name.getText().toString();

        Query id_query = db_member.orderByChild("member_id").equalTo(str_id);
        id_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){//사용가능
                    Query name_query = db_member.orderByChild("member_name").equalTo(str_name);
                    name_query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()==null) {//사용가능한 닉네임
                                member m = new member(str_id,str_pw,"건국대학교",str_name,"w",0,0,0);
                                db_member.child(str_id).setValue(m);

                                Toast.makeText(getApplicationContext(), "디비저장 완료", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                            else{
                                Toast.makeText(u03_Register_Activity.this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(u03_Register_Activity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}