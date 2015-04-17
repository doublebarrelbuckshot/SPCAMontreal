package com.example.jedgar.spcav10;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrowsePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BrowsePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */




public class BrowsePageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private LayoutInflater inflater;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static SearchCriteria sc;

    private OnFragmentInteractionListener mListener;
    DBHelper dbh;
    SQLiteDatabase db;

    private Cursor c;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BrowsePageFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static BrowsePageFragment newInstance() {
        BrowsePageFragment fragment = new BrowsePageFragment();
        return fragment;
    }





    public BrowsePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbh = new DBHelper(getActivity());
        db = dbh.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browse_page, container, false);
        this.inflater = inflater;

        Intent intent = ((MainActivity)BrowsePageFragment.this.getActivity()).getIntent();
        Bundle data = intent.getExtras();
        SearchCriteria sc = (SearchCriteria) data.getParcelable("SearchCriteria");

        String sql = sc.getSelectCommand();
        c = dbh.getCursorForSelect(db, sql);
        ListView list=(ListView)rootView.findViewById(R.id.browseView);

        // Getting adapter by passing xml data ArrayList
        BaseAdapter adapter=new LazyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {

                Log.d("SHARE", "88888888888 SHARE");
                Toast.makeText(getActivity(), "CLICKED ITEM at position  " + position, Toast.LENGTH_SHORT).show();

                ((MainActivity)BrowsePageFragment.this.getActivity()).doDetails(position);




            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity)activity).onSectionAttached(6);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDetailsListener {
        public void doDetails(int pos);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }




    private class LazyAdapter extends BaseAdapter {

        public LazyAdapter() {
        }

        public int getCount() {
            return c.getCount();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LinearLayout vi = (LinearLayout) inflater.inflate(R.layout.browse_row, null);
            c.moveToPosition(position);
            String t = Integer.toString(c.getInt(DBHelper.C_ANIMAL_ID));
            String d = c.getString(DBHelper.C_ANIMAL_NAME);
            String p1 = c.getString(DBHelper.C_ANIMAL_PHOTO1);
            String a = c.getString(DBHelper.C_ANIMAL_AGE);
            if(!p1.equals("")) {
                ImageView animalImage = (ImageView) vi.findViewById(R.id.animalImage);
                Picasso.with(getActivity().getApplicationContext()).load(p1).into(animalImage);
            }


            TextView animalID = (TextView) vi.findViewById(R.id.animalID);
            TextView animalName = (TextView) vi.findViewById(R.id.animalName);
            TextView animalAge = (TextView) vi.findViewById(R.id.animalAge);

            animalID.setText("ID: "+t);
            animalName.setText("Name: "+d);
            animalAge.setText("Age: :"+a);
            return vi;
        }
    }
}