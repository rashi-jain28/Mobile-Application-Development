package com.example.rashi.class08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DisplayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        TextView t1= view.findViewById(R.id.stuNameText);
        TextView t2= view.findViewById(R.id.stuEmailText);
        TextView t3= view.findViewById(R.id.stuDeptText);
        TextView t4= view.findViewById(R.id.stumoodText);
        //Bundle bundle = getArguments();
        final Student stu1= (Student) getArguments().getSerializable(MainActivity.ARG_PARAM3);
        t1.setText(stu1.getName());
        t2.setText(stu1.getEmail());
        t3.setText(stu1.getDepartment());
        t4.setText(stu1.getMood()+"% Positive");

        view.findViewById(R.id.ibName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editType(MainActivity.REQ_CODE_NAME,stu1);

            }
        });
        view.findViewById(R.id.ibEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editType(MainActivity.REQ_CODE_EMAIL,stu1);

            }
        });
        view.findViewById(R.id.ibDpt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editType(MainActivity.REQ_CODE_DPT,stu1);

            }
        });
        view.findViewById(R.id.ibMood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.editType(MainActivity.REQ_CODE_MOOD,stu1);

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

    public void display(Student stu1){
        TextView t1= getView().findViewById(R.id.stuNameText);
        TextView t2= getView().findViewById(R.id.stuEmailText);
        TextView t3= getView().findViewById(R.id.stuDeptText);
        TextView t4= getView().findViewById(R.id.stumoodText);
        t1.setText(stu1.getName());
        t2.setText(stu1.getEmail());
        t3.setText(stu1.getDepartment());
        t4.setText(stu1.getMood()+"% Positive");

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void editType(int reqCode,Student s );

    }
}
