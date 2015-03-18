package il.ac.huji.todolist;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        EditText edtNewItem;
        edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        ListView lv;
        lv = (ListView) findViewById(R.id.lstTodoItems);

        String newTask = edtNewItem.getText().toString();

        ArrayList<String> newList = new ArrayList<String>();
        int numOfItems = lv.getCount();

        // Updating the list with all items
        for(int i=0; i<numOfItems; i++) {
            String selectedFromList = (lv.getItemAtPosition(i).toString());
            newList.add(selectedFromList);
        }

        // Adding the new item to the head of the list
        newList.add(0,newTask);

        // Clean the editText after item was added
        edtNewItem.setText("");


        ArrayAdapter<String> todoArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, newList){
            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                // Controls the colors of list items

                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if ((position % 2) == 0) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.BLUE);
                }
                return view;
            }
        };

        ListView list = (ListView)findViewById(R.id.lstTodoItems);
        list.setAdapter(todoArrayAdapter);

        // Enable the long click context menu option
        registerForContextMenu(list);

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lstTodoItems) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo contextMenuAdapter = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(lv.getItemAtPosition(contextMenuAdapter.position).toString());

            // Context menu items
            menu.add("Delete Item");
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ListView lv;
        lv = (ListView) findViewById(R.id.lstTodoItems);

        ArrayList<String> newList = new ArrayList<String>();
        int numOfItems = lv.getCount();

        for(int i=0; i<numOfItems; i++) {
            String selectedFromList = (lv.getItemAtPosition(i).toString());
            newList.add(selectedFromList);
        }


        ArrayAdapter<String> todoArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, newList){

            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                // Controls the colors of list items

                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if ((position % 2) == 0) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.BLUE);
                }
                return view;
            }
        };

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        newList.remove(info.position);
        ListView list = (ListView)findViewById(R.id.lstTodoItems);
        list.setAdapter(todoArrayAdapter);

        // Enable the long click context menu option
        registerForContextMenu(list);


        return super.onOptionsItemSelected(item);

    }
}