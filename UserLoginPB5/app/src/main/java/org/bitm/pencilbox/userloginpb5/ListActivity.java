package org.bitm.pencilbox.userloginpb5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.bitm.pencilbox.userloginpb5.Database.EmployeeDataSource;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<BaseSalariedEmployee>bse = new ArrayList<>();
    private EmployeeAdapter adapter;
    private EmployeeDataSource source;
    private EmployeeRecyclerViewAdapter recyclerViewAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        source = new EmployeeDataSource(this);
        bse = source.getAllEmployees();
        //adapter = new EmployeeAdapter(this,bse);
        //recyclerView.setAdapter(adapter);
        recyclerViewAdapter = new EmployeeRecyclerViewAdapter(bse);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("search by name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });


        GridLayoutManager glm = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(recyclerViewAdapter);
        registerForContextMenu(recyclerView);


    }

    public void addEmployee(View view) {
        startActivity(new Intent(this,HomeActivity.class));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.emp_list_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (item.getItemId()){
            case R.id.edit:
                int empId = bse.get(position).getEmpId();
                startActivity(new Intent(ListActivity.this,HomeActivity.class).putExtra("empId",empId));
                break;
            case R.id.delete:
                int rowId = bse.get(position).getEmpId();
                boolean status = source.deleteEmployee(rowId);
                if(status){

                }else{
                    Toast.makeText(ListActivity.this, "could not delete", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
}
