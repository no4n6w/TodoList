package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class AddItemDialogActivity extends Activity implements View.OnClickListener {

    public EditText itemEditText;
    public Button bOk;
    public Button bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_new_item);
        itemEditText = (EditText) findViewById(R.id.edtNewItem);
        bOk = (Button) findViewById(R.id.ok);
        bCancel = (Button) findViewById(R.id.cancel);
        bOk.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = getIntent();
        switch (v.getId()) {
            case R.id.ok: // Pass the new item to the main activity
                String itemText = itemEditText.getText().toString();
                DatePicker datePicker= (DatePicker) findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                String itemDate = day + "-" + month + "-" + year;
                String fullItem = itemText + " " + itemDate;
                intent.putExtra(getString(R.string.newItemKey), fullItem);
                setResult(RESULT_OK, intent);
                break;

            case R.id.cancel: // Cancel
                setResult(RESULT_CANCELED, intent);
                break;
        }
        finish();
    }
}

