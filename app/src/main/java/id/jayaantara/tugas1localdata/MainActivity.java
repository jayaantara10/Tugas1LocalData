package id.jayaantara.tugas1localdata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSave;
    EditText editContent;
    EditText editTitle;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.button_save);
        editContent = (EditText) findViewById(R.id.edit_note);
        editTitle = (EditText) findViewById(R.id.edit_title);

        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_save:
                saveNote();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_op,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_note:
                newNote();
                return true;
            case R.id.open_note:
                showList();
                return true;
        }
        return false;
    }

    public void newNote() {
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Clearing note", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Open " + title + " note", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Your Note");
        builder.setItems(items, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            loadData(items[item].toString()); }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveNote() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "You must add title", Toast.LENGTH_SHORT).show();
        }else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " note", Toast.LENGTH_SHORT).show();
        }
    }


}