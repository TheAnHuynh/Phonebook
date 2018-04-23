package d14cqcp01.group5.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtSearchBox;
    private ImageButton btnAddContact, btnSearch;
    private ListView lvContacts;
    private ArrayList<Contact> contactArrayList;
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> seachedList;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRef = FirebaseDatabase.getInstance().getReference("MYCONTACTS");

        seachedList = new ArrayList<>();
        addControls();
        addEvents();

    }

    private void addControls() {
        edtSearchBox = findViewById(R.id.edtSearchBox);
        btnAddContact = findViewById(R.id.btnAddContact);
        lvContacts = findViewById(R.id.lvContacts);
        btnSearch= findViewById(R.id.btnSearch);
    }

    private void addEvents() {

        contactArrayList = new ArrayList<>();
        contactAdapter = new ContactAdapter(MainActivity.this,R.layout.listview_item,contactArrayList);
        lvContacts.setAdapter(contactAdapter);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddContactActivity.class);
                startActivity(intent);
                finish();
            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = (String) dataSnapshot.getValue();
                String phone = dataSnapshot.getKey();

                contactArrayList.add(new Contact(name,phone));
                contactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.getKey();
                for(int i = 0; i < contactArrayList.size(); i++){
                    if(contactArrayList.get(i).getPhoneNumber().equals(phone)){
                        contactArrayList.remove(i);
                        contactAdapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = edtSearchBox.getText().toString();
                if(key.isEmpty()){
                    contactAdapter.setContactArrayList(contactArrayList);
                }else{
                    seachedList = new ArrayList<>();
                    for(Contact contact:contactArrayList){
                        if(contact.getName().contains(key) ||contact.getPhoneNumber().contains(key)){
                            seachedList.add(contact);
                        }
                    }
                    contactAdapter.setContactArrayList(seachedList);
                }
            }
        });
    }
}
