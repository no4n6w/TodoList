package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Michael on 3/20/2015.
 */
public class NameDialogActivity extends Activity implements View.OnClickListener {

    public EditText mNameEditText;
    public Button mOk;
    public Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signin);
        mNameEditText = (EditText) findViewById(R.id.edtNewItem);
        mOk = (Button) findViewById(R.id.ok);
        mCancel = (Button) findViewById(R.id.cancel);
        mOk.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = getIntent();
        switch (v.getId()) {
            case R.id.ok:
                String itemText = mNameEditText.getText().toString();
                DatePicker datePicker= (DatePicker) findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                String itemDate = day + "-" + month + "-" + year;
                String fullItem = itemText + " " + itemDate;

                intent.putExtra("newItem", fullItem);
                setResult(RESULT_OK, intent);
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED, intent);
                break;
        }
        finish();
    }
}

