package d14cqcp01.group5.phonebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Activity activity;
    private int layout;
    private ArrayList<Contact> contactArrayList;


    public ContactAdapter(@NonNull Activity activity, int resource, @NonNull ArrayList<Contact> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.layout = resource;
        this.contactArrayList = objects;
    }

    @Override
    public int getCount() {
        return contactArrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(layout,null);

        TextView txtName = view.findViewById(R.id.txtName);
        final TextView txtPhone = view.findViewById(R.id.txtPhoneNumber);
        final Button btnEdit = view.findViewById(R.id.btnEdit);
        final Button btnDelete = view.findViewById(R.id.btnDelete);

        final Contact contact = contactArrayList.get(position);
        txtName.setText(contact.getName());
        txtPhone.setText(contact.getPhoneNumber());


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                Intent intent = new Intent(activity,EditContactActivity.class);
                intent.putExtra("NAME",contact.getName());
                intent.putExtra("PHONE",contact.getPhoneNumber());
                activity.startActivity(intent);
                activity.finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("MYCONTACTS");
                myRef.child(contact.getPhoneNumber()).removeValue();
                Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return view;
    }

    public void setContactArrayList(ArrayList<Contact> list){
        contactArrayList = list;
        this.notifyDataSetChanged();
    }
}
