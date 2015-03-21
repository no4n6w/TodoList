package il.ac.huji.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.String;
import java.util.Date;



public class TodoListManagerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        ListView lv;
        lv = (ListView) findViewById(R.id.lstTodoItems);

        ArrayList<String> newList = new ArrayList<String>();
        int numOfItems = lv.getCount();

        // Updating the list with all items
        for(int i=0; i<numOfItems; i++) {
            String selectedFromList = (lv.getItemAtPosition(i).toString());
            newList.add(selectedFromList);
        }

        ArrayAdapter<String> todoArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, newList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                ListView lv = (ListView) findViewById(R.id.lstTodoItems);
                String item = lv.getItemAtPosition(position).toString();

                ParsePosition ind = new ParsePosition(item.lastIndexOf(" ")+1);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date = sdf.parse(item,ind);

                Date currentDate = new Date();


                if (date.compareTo(currentDate) < 0) {
                    textView.setTextColor(Color.RED);
                }

                return view;
            };


        };

//---------------------------------------------------------------------------


        ListView list = (ListView)findViewById(R.id.lstTodoItems);
        list.setAdapter(todoArrayAdapter);
        registerForContextMenu(list);

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


        Intent intent = new Intent(this, AddItemDialogActivity.class);
        startActivityForResult(intent, 1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && data.getExtras().containsKey("newItem")) {
            String name = data.getExtras().getString("newItem");

            ListView lv;
            lv = (ListView) findViewById(R.id.lstTodoItems);

            String newTask = name;

            ArrayList<String> newList = new ArrayList<String>();
            int numOfItems = lv.getCount();

            // Updating the list with all items
            for(int i=0; i<numOfItems; i++) {
                String selectedFromList = (lv.getItemAtPosition(i).toString());
                newList.add(selectedFromList);
            }

            // Adding the new item to the head of the list
            newList.add(0,newTask);

            ArrayAdapter<String> todoArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, newList);



            ListView list = (ListView)findViewById(R.id.lstTodoItems);
            list.setAdapter(todoArrayAdapter);

            // Enable the long click context menu option
            registerForContextMenu(list);

        }
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lstTodoItems) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo contextMenuAdapter = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String itemName = lv.getItemAtPosition(contextMenuAdapter.position).toString();
            menu.setHeaderTitle(itemName.substring(0,itemName.lastIndexOf(" ")));


            // Create one or two context menu items
            menu.add(menu.NONE, 1, 1, "Delete Item"); // Delete Item
            if (itemName.startsWith("call") || itemName.startsWith("Call")){
                menu.add(menu.NONE, 2, 2, itemName.substring(0, itemName.lastIndexOf(" ")));
            }
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ListView lv;
        lv = (ListView) findViewById(R.id.lstTodoItems);
        AdapterView.AdapterContextMenuInfo infoList = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case 1: // Item is Deleted from the list

                ArrayList<String> newList = new ArrayList<String>();
                int numOfItems = lv.getCount();

                for (int i = 0; i < numOfItems; i++) {
                    String selectedFromList = (lv.getItemAtPosition(i).toString());
                    newList.add(selectedFromList);
                }

                ArrayAdapter<String> todoArrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, newList) {};

                newList.remove(infoList.position);
                ListView list = (ListView) findViewById(R.id.lstTodoItems);
                list.setAdapter(todoArrayAdapter);

                // Enable the long click context menu option
                registerForContextMenu(list);

                break;

            case 2: // Call action was chosen by the user

                String callStr =  lv.getItemAtPosition(infoList.position).toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + callStr.substring(5,callStr.lastIndexOf(" "))));
                startActivity(intent);
                break;
        }



        return super.onOptionsItemSelected(item);

    }
}