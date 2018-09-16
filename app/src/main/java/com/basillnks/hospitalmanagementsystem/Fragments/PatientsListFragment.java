package com.basillnks.hospitalmanagementsystem.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basillnks.hospitalmanagementsystem.Database.PatientDBHelper;
import com.basillnks.hospitalmanagementsystem.PatientAdapter;
import com.basillnks.hospitalmanagementsystem.PatientModel;
import com.basillnks.hospitalmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientsListFragment extends Fragment implements OnClickListener, OnQueryTextListener {

    public static boolean is_in_action_mode = false;
    public static TextView counterTextView;
    public static Toolbar toolbar;
    private ActionBar actionBar;
    private PatientDBHelper dbHelper;
    private PatientAdapter adapter;
    private ArrayList<PatientModel> patientModels;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public PatientsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patients_list, container, false);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.recycler_action);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");
        findWidgets(view);

        return view;
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     */
    @Override
    public void onResume() {
        super.onResume();
        displayPatientsList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_new_patient:
                PatientEditorFragment patientEditorFragment = new PatientEditorFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, patientEditorFragment)
                        .addToBackStack(null).commit();
                break;
        }
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link Fragment#onCreateOptionsMenu(Menu, MenuInflater)}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_sort_menu, menu);
        MenuItem.OnActionExpandListener listener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchItem.setOnActionExpandListener(listener);
        searchView.setOnQueryTextListener(this);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<PatientModel> modelList = new ArrayList<>();

        for (PatientModel patient :
                patientModels) {
            if (patient.getPatientName().toLowerCase().contains(userInput)) {
                modelList.add(patient);
            }
        }
        adapter.updateSearch(modelList);
        return true;
    }

    private void findWidgets(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_new_patient);
        fab.setOnClickListener(this);

        counterTextView = view.findViewById(R.id.counter_textview);
        counterTextView.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    public void displayPatientsList() {
        dbHelper = new PatientDBHelper(getActivity());
        patientModels = dbHelper.displayPatientList();

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PatientAdapter(getActivity(), patientModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
