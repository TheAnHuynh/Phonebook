package d14cqcp01.group5.phonebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContactActivity extends AppCompatActivity {

    private final static String REFERENCE = "MYCONTACTS";

    private EditText edtName, edtPhoneNumber;
    private Button btnSave, btnCancel;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);

        myRef = FirebaseDatabase.getInstance().getReference(REFERENCE);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtName = findViewById(R.id.edtName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();

                if(name.isEmpty() || phoneNumber.isEmpty()){
                    Toast.makeText(AddContactActivity.this,
                            "Điền thiếu thông tin",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(phoneNumber.length() < 10 || phoneNumber.length()>11){
                    Toast.makeText(AddContactActivity.this,
                            "Số điện thoại không hợp lệ",Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.child(phoneNumber).setValue(name);
                Toast.makeText(AddContactActivity.this,
                        "Thêm thành công",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddContactActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
