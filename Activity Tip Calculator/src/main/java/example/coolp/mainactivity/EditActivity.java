package example.coolp.mainactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        setTitle("Edit Activity");

        int reqCode= (int) getIntent().getExtras().getSerializable(DisplayActivity.KEY_REQ_TYPE);

        final EditText editName=(EditText) findViewById(R.id.nameTextEdit);
        final EditText editEmail= (EditText)findViewById(R.id.emailTextEdit);
        final TextView dptText=(TextView)findViewById(R.id.deptEditText);
        final TextView textMood=(TextView)findViewById(R.id.textmoodEdit);
        final SeekBar sb= (SeekBar)findViewById(R.id.seekBarEdit);

        final RadioGroup rg=(RadioGroup)findViewById(R.id.dptGroupEdit);
        final String[] dptValue = new String[1];
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) findViewById(checkedId);
                dptValue[0] = rb.getText().toString();
            }
        });

        if(reqCode==DisplayActivity.REQ_CODE_NAME){
            editName.setVisibility(View.VISIBLE);
        }
        else if(reqCode==DisplayActivity.REQ_CODE_EMAIL){
            editEmail.setVisibility(View.VISIBLE);
        }
        else if(reqCode==DisplayActivity.REQ_CODE_DPT){
            dptText.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb=(RadioButton) findViewById(checkedId);
                    dptValue[0] = rb.getText().toString();
                }
            });
        }
        else if(reqCode==DisplayActivity.REQ_CODE_MOOD){
            textMood.setVisibility(View.VISIBLE);
            sb.setVisibility(View.VISIBLE);
        }

        Button saveBtn=(Button) findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Student stu1= (Student) getIntent().getExtras().getSerializable(DisplayActivity.KEY_STUDENT);
                try {
                    if (editName.getVisibility() == View.VISIBLE) {
                        stu1.setName(editName.getText().toString());
                    } else if (editEmail.getVisibility() == View.VISIBLE) {
                        stu1.setEmail(editEmail.getText().toString());
                    } else if (rg.getVisibility() == View.VISIBLE) {
                        stu1.setDepartment(dptValue[0]);
                    } else if (sb.getVisibility() == View.VISIBLE) {
                        stu1.setMood(sb.getProgress());
                    }

                }
                catch(Exception e) {
                    Toast.makeText(EditActivity.this,"Please enter the correct values",Toast.LENGTH_SHORT).show();
                }
                    Intent i=new Intent();
                    i.putExtra(DisplayActivity.KEY_STUDENT,stu1);
                    setResult(RESULT_OK,i);
                    finish();

                }
        });



    }
}
