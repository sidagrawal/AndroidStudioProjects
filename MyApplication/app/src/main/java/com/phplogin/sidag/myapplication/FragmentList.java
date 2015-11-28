package com.phplogin.sidag.myapplication;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import com.phplogin.sidag.data.ItemProvider;
import com.phplogin.sidag.data.ListDatabaseHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class FragmentList extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST_ID = "listid";
    private static final String ARG_LIST_NAME = "listname";

    // TODO: Rename and change types of parameters
    private String mheaderText;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;
    private TextView headerText;
    private String list_uid;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private CursorAdapter cAdapter;

    // TODO: Rename and change types of parameters
    public static FragmentList newInstance(String list_uid, String list_name) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST_ID, list_uid);
        args.putSerializable(ARG_LIST_NAME, list_name);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            list_uid = getArguments().getString(ARG_LIST_ID);
            mheaderText = getArguments().getString(ARG_LIST_NAME);
        }


        // TODO: Change Adapter to display your content
        //mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, mlist);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);



        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setItemsCanFocus(true);

        //Set where to get the data from and where to put it
        String[] uiBindFrom = {ListDatabaseHelper.LIST_ITEM };
        int[] uiBindTo = { R.id.ItemCaption };

        //Create an empty Cursor Adapter to hold the cursor from the Database
        cAdapter = new SimpleCursorAdapter(getActivity()
                .getApplicationContext(), R.layout.item, null,
                uiBindFrom, uiBindTo);

        //Start the Cursor Loader
        getLoaderManager().initLoader(0, null, this);

        //Set the Header to the List's name
        headerText = (TextView) view.findViewById(R.id.header);
        headerText.setText(mheaderText);

        //Set the adapter for the ListView
        mListView.setAdapter(cAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    //Query the database for items for the given list UID
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { "" + ListDatabaseHelper.LIST_ITEM_ID + " as _id", ListDatabaseHelper.LIST_ITEM};
        String selection =  ListDatabaseHelper.LIST_UID + "= ?";
        String[] selectionArgs = {list_uid};
        CursorLoader cursor = new CursorLoader(getActivity(), ItemProvider.CONTENT_URI_ITEMS, projection , selection, selectionArgs, null);
        return cursor;
    }

    //Swap the cursor in the cursor adapter
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Log.d("cursor", DatabaseUtils.dumpCursorToString(data));
        cAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
        public void onFragmentInteraction(String id);
    }

}
