package com.example.rashi.class08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view= inflater.inflate(R.layout.fragment_edit, container, false);
        int reqCode= (int) getArguments().getInt(MainActivity.KEY_REQ_TYPE);
        Student student= (Student) getArguments().getSerializable(MainActivity.KEY_STUDENT);

        final EditText editName= view.findViewById(R.id.name1);
        final EditText editEmail= view.findViewById(R.id.email1);
        final TextView dptText=view.findViewById(R.id.dept1);
        final TextView textMood=view.findViewById(R.id.mood1);
        final SeekBar sb= view.findViewById(R.id.sb1);

        final RadioGroup rg=view.findViewById(R.id.rg1);
        final String[] dptValue = new String[1];
        final Student stu1= (Student) getArguments().getSerializable(MainActivity.KEY_STUDENT);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) view.findViewById(checkedId);
                dptValue[0] = rb.getText().toString();
            }
        });

        if(reqCode==MainActivity.REQ_CODE_NAME){
            editName.setVisibility(View.VISIBLE);
            editName.setText(stu1.name);
        }
        else if(reqCode==MainActivity.REQ_CODE_EMAIL){
            editEmail.setVisibility(View.VISIBLE);
            editEmail.setText(stu1.email);
        }
        else if(reqCode==MainActivity.REQ_CODE_DPT){
            dptText.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
            RadioButton rb1= view.findViewById(R.id.rb1);
            RadioButton rb2= view.findViewById(R.id.rb2);
            RadioButton rb3= view.findViewById(R.id.rb3);
            RadioButton rb4= view.findViewById(R.id.rb4);
            if(rb1.getText().equals(stu1.department)){
                rb1.setChecked(true);
            }else if(rb2.getText().equals(stu1.department)){
                rb2.setChecked(true);
            } else if(rb3.getText().equals(stu1.department)){
                rb3.setChecked(true);
            }else if(rb4.getText().equals(stu1.department)){
                rb4.setChecked(true);
            }
            //RadioButton rb= view.find


        }
        else if(reqCode==MainActivity.REQ_CODE_MOOD){
            textMood.setVisibility(View.VISIBLE);
            sb.setVisibility(View.VISIBLE);
            sb.setProgress(stu1.mood);
        }

        Button saveBtn=(Button) view.findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    mListener.updatedData( stu1);
                    /*
                    Intent i=new Intent();
                    i.putExtra(DisplayActivity.KEY_STUDENT,stu1);
                    setResult(RESULT_OK,i);
                    finish();*/
                    /*rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton rb=(RadioButton) findViewById(checkedId);
                            dptValue[0] = rb.getText().toString();
                        }*/
                    // });


                }
                catch(Exception e) {
                    Toast.makeText(getContext(),"Please enter the correct values",Toast.LENGTH_SHORT).show();
                }


            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void updatedData(Student stu1);
    }
}
