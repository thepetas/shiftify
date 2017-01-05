package cz.cvut.fit.shiftify;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.data.managers.UserManager;

/**
 * Created by Vojta on 13.11.2016.
 */


public class PersonsListFragment extends ListFragment implements AdapterView.OnItemClickListener {


    /* Vojta:
    * Dummy pole pro seznam osob - pozdeji se budou tahat z DB.
    * Dale pomocne struktury pro ListLayout.
    * */
    private String[] personsArray;
    private List<User> userList;

    Integer[] imageId = {
            R.drawable.face,
            R.drawable.icon_bar_example,
            R.drawable.face_obama,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.face_obama,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.face,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example

    };

    private ListView personsList;
    private ArrayAdapter arrayAdapter;

    private CustomPersonsListAdapter adapter;
    /*-----------*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persons, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        UserManager userManager = new UserManager();
        userList = new ArrayList<>();

        try {
            userList = userManager.allUsers();

        } catch (Exception e) {
            System.err.println("Nepovedlo se nacist uzivatele z DB.");
        }

        personsArray = new String[userList.size()];
        makeArray(userList);

        adapter = new CustomPersonsListAdapter(getActivity(), personsArray, imageId);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    private void makeArray(List<User> list) {

        int index = 0;
        String firstname, surname, nickname;

        for (User u :
                list) {

            firstname = u.getFirstName();
            surname = u.getSurname();
            nickname = (u.getNickname() == null ? " " : " \"" + u.getNickname() + "\" ");


            personsArray[index] = firstname + nickname + surname;
            index++;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


        Intent i = new Intent(PersonsListFragment.this.getActivity(), PersonDetailActivity.class);
        i.putExtra("userId", userList.get(position).getId());

        startActivity(i);

    }
}