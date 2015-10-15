package com.phplogin.sidag.myapplication;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import com.phplogin.sidag.data.ListDatabaseHelper;
import com.phplogin.sidag.data.ListProvider;

import java.util.ArrayList;

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
    private static final String ARG_LIST_ITEMS = "listItems";
    private static final String ARG_HEADER_TEXT = "headerText";


    // TODO: Rename and change types of parameters
    private ArrayList<ListItems> mlistItems;
    private String mheaderText;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;
    private TextView headerText;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private CursorAdapter cAdapter;

    // TODO: Rename and change types of parameters
    public static FragmentList newInstance(ArrayList<ListItems> listItems, String headerText) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST_ITEMS, listItems);
        args.putString(ARG_HEADER_TEXT, headerText);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentList() {
    }

    public void setAdapterData(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mlistItems = (ArrayList<ListItems>)getArguments().getSerializable(ARG_LIST_ITEMS);
            mheaderText = getArguments().getString(ARG_HEADER_TEXT);
        }


        // TODO: Change Adapter to display your content
        //mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, mlist);
        mAdapter = new MyAdapter(getActivity(), mlistItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        headerText = (TextView) view.findViewById(R.id.header);
        headerText.setText(mheaderText);
        // Set the adapter
        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setItemsCanFocus(true);
        //mListView.setAdapter(mAdapter);
        String[] uiBindFrom = {"'" + ListDatabaseHelper.LIST_ITEM + "'"};
        int[] uiBindTo = { R.id.title };

        cAdapter = new SimpleCursorAdapter(getActivity()
                .getApplicationContext(), android.R.layout.simple_list_item_1, null,
                uiBindFrom, uiBindTo);

        getLoaderManager().initLoader(0, null, this);

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
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { "'" + ListDatabaseHelper.LIST_ITEM_ID + "' as _id", "'" + ListDatabaseHelper.LIST_ITEM + "'"};
        //String[] projection = { ListDatabaseHelper.LIST_ITEM_ID , ListDatabaseHelper.LIST_ITEM };
        CursorLoader cursor = new CursorLoader(getActivity(), ListProvider.CONTENT_URI, projection , null, null, null);
        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("cursor", (String) String.valueOf(data.getCount()));
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
