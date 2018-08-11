package example.coolp.mainactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    public static int REQ_CODE_NAME=100;
    public static int REQ_CODE_EMAIL=101;
    public static int REQ_CODE_DPT=102;
    public static int REQ_CODE_MOOD=103;
    public static int REQ_CODE=1000;
    public static String KEY_STUDENT="STUDENT_KEY";
    public static String KEY_REQ_TYPE="REQ_TYPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("Display Activity");
        Student stu1= (Student) getIntent().getExtras().getSerializable(MainActivity.STUDENT_KEY);
        TextView t1= (TextView)findViewById(R.id.stuNameText);
        TextView t2= (TextView)findViewById(R.id.stuEmailText);
        TextView t3= (TextView)findViewById(R.id.stuDeptText);
        TextView t4= (TextView)findViewById(R.id.stumoodText);
        t1.setText(stu1.getName());
        t2.setText(stu1.getEmail());
        t3.setText(stu1.getDepartment());
        t4.setText(stu1.getMood()+"% Positive");
        final Intent intent=new Intent(DisplayActivity.this,EdittActivity.class);
        intent.putExtra(KEY_STUDENT,stu1);

        findViewById(R.id.ibName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(KEY_REQ_TYPE,REQ_CODE_NAME);
                startActivityForResult(intent,REQ_CODE);
            }
        });
        findViewById(R.id.ibEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(KEY_REQ_TYPE,REQ_CODE_EMAIL);
                startActivityForResult(intent,REQ_CODE);
            }
        });
        findViewById(R.id.ibDpt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(KEY_REQ_TYPE,REQ_CODE_DPT);
                startActivityForResult(intent,REQ_CODE);
            }
        });
        findViewById(R.id.ibMood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(KEY_REQ_TYPE,REQ_CODE_MOOD);
                startActivityForResult(intent,REQ_CODE);
            }
        });

        //finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            if(resultCode==RESULT_OK && data.getExtras().containsKey(DisplayActivity.KEY_STUDENT)){
                Student st= (Student) data.getExtras().getSerializable(DisplayActivity.KEY_STUDENT);
                TextView t1= (TextView)findViewById(R.id.stuNameText);
                t1.setText(st.getName());
                TextView t2= (TextView)findViewById(R.id.stuEmailText);
                t2.setText(st.getEmail());
                TextView t3= (TextView)findViewById(R.id.stuDeptText);
                t3.setText(st.getDepartment());
                TextView t4= (TextView)findViewById(R.id.stumoodText);
                t4.setText(st.getMood()+"% Positive");
            }
        }

        }


}
