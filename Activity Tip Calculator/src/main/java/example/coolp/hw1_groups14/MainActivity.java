    package example.coolp.hw1_groups14;

    import android.support.v7.app.ActionBar;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.KeyEvent;
    import android.view.View;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import android.widget.Toolbar;

    import org.w3c.dom.Text;

    import static example.coolp.hw1_groups14.R.mipmap.ic_launcher;

    /*
    Assignment: Homework 1 (HW1_Group#14)
    Group Members: Priyanka Taneja, Rashi Jain
     */
    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ActionBar actionbar= getSupportActionBar();
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setIcon(R.mipmap.ic_launcher_round);
            setTitle("Tip Calculator");
            final EditText billText= findViewById(R.id.billText);
            final TextView tipVal=(TextView)findViewById(R.id.tipValue);
            final TextView billVal=(TextView) findViewById(R.id.total);
            final double tipValue;
            final TextView progressVal=(TextView)findViewById(R.id.progress);
            final SeekBar seekBar= (SeekBar)findViewById(R.id.seekBar);
            seekBar.setProgress(25);
            progressVal.setText("25%");
            final Integer[] tipTotal=new Integer[1];
            tipTotal[0]=10;

            RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
            final String[] radioValue=new String[1];
            radioValue[0]="10%";
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb = (RadioButton) findViewById(checkedId);
                    radioValue[0] = rb.getText().toString();
                    if (!radioValue[0].equalsIgnoreCase("Custom")) {
                        tipTotal[0]=Integer.parseInt(radioValue[0].split("%")[0]);
                        seekBar.setProgress(25);
                        if(billText.getText().toString().isEmpty()){
                            billText.setError("Enter Bill Total");
                            tipVal.setText("0.00");
                            billVal.setText("0.00");
                        }else {
                            calculateTipBill(tipTotal[0]);
                        }
                    }else{
                        tipTotal[0]=25;
                        seekBar.setProgress(tipTotal[0]);
                        progressVal.setText(tipTotal[0]+"%");
                        if(billText.getText().toString().isEmpty()){
                            billText.setError("Enter Bill Total");
                            tipVal.setText("0.00");
                            billVal.setText("0.00");
                        }else {
                            calculateTipBill(tipTotal[0]);
                        }
                    }

                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressVal.setText(progress+"%");
                    if (radioValue!=null && radioValue[0].equalsIgnoreCase( "Custom")){
                        tipTotal[0]=progress;
                        if(billText.getText().toString().isEmpty()){
                            billText.setError("Enter Bill Total");
                            tipVal.setText("0.00");
                            billVal.setText("0.00");
                        }else {
                            calculateTipBill(tipTotal[0]);
                        }
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });


            billText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(billText.getText().toString().isEmpty()){
                        billText.setError("Enter Bill Total");
                        tipVal.setText("0.00");
                        billVal.setText("0.00");
                    }
                    else{
                        calculateTipBill(tipTotal[0]);
                    }
                    return false;
                }
            });

            Button exitBtn=(Button)findViewById(R.id.buttonExit);
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

        public void calculateTipBill(int tipValue){

            EditText billText= findViewById(R.id.billText);
            double bill= Double.parseDouble(billText.getText().toString());
            TextView tipVal=(TextView)findViewById(R.id.tipValue);
            TextView billVal=(TextView) findViewById(R.id.total);
            double billTotal= Double.parseDouble(billText.getText().toString());
            if(billTotal>0.0) {
                double totalTip=(bill*tipValue)/100.0;
                 totalTip = Math.round(totalTip*100.0)/100.0;
                tipVal.setText(totalTip+"");
                double totalBill= bill+totalTip;
                totalBill=Math.round(totalBill*100);
                totalBill=totalBill/100;
                billVal.setText(totalBill+"");

            }
        }
    }

