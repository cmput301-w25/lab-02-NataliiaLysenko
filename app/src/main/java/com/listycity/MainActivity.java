package com.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //Declaring variables for referencing later
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText editCity;
    Button addButton;
    Button deleteButton;
    int listItemPos= -1;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //connect activity to the layout file using setContentView
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
        });

        //refer to the layout's UI widget with findViewById
        cityList = findViewById(R.id.city_list);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        editCity = findViewById(R.id.edit_city);
        editCity.setVisibility(View.GONE);
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        //adapts the content of the array list to the view list to display
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        //assign the adapter to the list view
        cityList.setAdapter(cityAdapter);

        //list items selection for delete function
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItemPos = position;
            }
        });

        addButton.setOnClickListener(this::addCity);
        deleteButton.setOnClickListener(this::deleteCity);
    }

    public void addCity(View v){
        if (editCity.getVisibility() == View.GONE) {
            editCity.setVisibility(View.VISIBLE);
            addButton.setText("CONFIRM");
        } else {
            String cityName = editCity.getText().toString();
            if (!cityName.isEmpty()) {
                dataList.add(cityName);
                cityAdapter.notifyDataSetChanged();
                editCity.setText("");
                editCity.setVisibility(View.GONE);
                addButton.setText("ADD CITY");
            } else {
                // reset if user doesn't do anything
                editCity.setVisibility(View.GONE);
                addButton.setText("ADD CITY");
            }
        }

    }

    public void deleteCity(View v){
      if (listItemPos != -1){
          dataList.remove(listItemPos);
          cityAdapter.notifyDataSetChanged();
          listItemPos = -1;
      }
    }
}