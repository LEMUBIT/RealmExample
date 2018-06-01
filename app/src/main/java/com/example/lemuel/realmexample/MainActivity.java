package com.example.lemuel.realmexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    //used ButterKnife library to reduce boilerplate code
    @BindView(R.id.txt_name)
    EditText name_txt;
    @BindView(R.id.txt_age)
    EditText age_txt;
    @BindView(R.id.btn_add)
    Button add_btn;
    @BindView(R.id.txt_filter)
    TextView filter_txt;
    @BindView(R.id.txt_ageFilter_number)
    EditText age_filter_number_txt;
    @BindView(R.id.btn_filter)
    Button filter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //todo (4) Initialize Realm (just once per application)


        //todo (5) Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        add_btn.setOnClickListener(v -> {
            // write operations in Realm must be wrapped in transactions.
            realm.beginTransaction();
            User user = realm.createObject(User.class); //todo  Create a new object
            user.setName(name_txt.getText().toString()); //todo get user name from Edittext  and store in user object
            user.setAge(Integer.valueOf(age_txt.getText().toString())); //todo get user age from Edittext  and store in user object
            realm.commitTransaction();
            // commit transaction

            Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
            clearText();
        });


        //query when filter button is clicked
        filter_btn.setOnClickListener(v -> {
                    //todo perform realm query
                    RealmResults<User> result = realm.where(User.class)
                            //find all user whose age is greater than filter specified
                            .lessThan("age", Integer.parseInt(age_filter_number_txt.getText().toString()))
                            //return all result that reach criteria
                            .findAll();

                    //loop through realm result and append the string builder
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < result.size(); i++) {
                        stringBuilder.append(result.get(i).getName() + "  ");
                    }

                    //set text of the filtered-text Textview to that of the realm results
                    filter_txt.setText(stringBuilder.toString());
                }

        );


    }

    //clear Textviews
    private void clearText() {
        name_txt.setText("");
        age_txt.setText("");
    }
}
