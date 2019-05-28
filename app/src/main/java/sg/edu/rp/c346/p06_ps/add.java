package sg.edu.rp.c346.p06_ps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class add extends AppCompatActivity {
    int reqCode = 12345;
    AlarmManager am;
    Button btnAdd , btnCancel;
    EditText etName , etDesciption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        btnAdd = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        etName = findViewById(R.id.etName);
        etDesciption = findViewById(R.id.etDescription);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String description = etDesciption.getText().toString();
                DBHelper dbh = new DBHelper(add.this);
                long row_affected = dbh.insertTask(name , description);

                if (row_affected != -1){
                    Toast.makeText(add.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }


                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND,5);

                Intent intent = new Intent(add.this, NotificationReceiver.class);
                intent.putExtra("name", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(add.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                finish();


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
