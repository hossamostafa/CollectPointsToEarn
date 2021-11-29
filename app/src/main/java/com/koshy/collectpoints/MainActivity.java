package com.koshy.collectpoints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.koshy.collectpoints.adapters.ContainersAdapter;
import com.koshy.collectpoints.models.ContainerModel;
import com.koshy.collectpoints.models.UserData;
import com.koshy.collectpoints.viewmodels.ContainersViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.dailyOffers)
    TextView dailyOffers;
    @BindView(R.id.addContainer)
    TextView addContainer;
    @BindView(R.id.usernameText)
    TextView usernameText;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.eyeParent)
    RelativeLayout eyeParent;
    @BindView(R.id.coinsParent)
    RelativeLayout coinsParent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    boolean isRegistered = false;

    FirebaseFirestore db;

    ContainersViewModel containersViewModel;
    ContainersAdapter containersAdapter;

    String userId;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        db = FirebaseFirestore.getInstance();

        Intent gIntent = getIntent();
        boolean isRegistered = gIntent.getBooleanExtra("isRegistered", false);

        addContainer.setOnClickListener(v -> {
            showAddContainerDialog();
        });
        eyeParent.setOnClickListener(v->{
            coinsParent.setVisibility(View.VISIBLE);
            eyeParent.setVisibility(View.INVISIBLE);
        });

        if (isRegistered) {
            userId =gIntent.getStringExtra("userId");
            DocumentReference documentReference = db.collection("userData").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    usernameText.setText(value.getString("username"));
                    userData = new UserData(value.getString("firstName"),value.getString("username"),value.getString("points"));
                }
            });
            init();
        } else {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

    private void init() {
        containersViewModel = new ViewModelProvider(this).get(ContainersViewModel.class);
        containersViewModel.getContainerData();
        containersViewModel.conListMutableLiveData.observe(this, DModels -> {
            if (DModels.size()>0) {

            }
            containersAdapter = new ContainersAdapter(getBaseContext(), (ArrayList<ContainerModel>) DModels);
            containersAdapter.setOnPopupClickListener(new ContainersAdapter.TOnPopupMenuClickListener() {
                @Override
                public void onDeleteClicked(int pos, String id) {
//                    Toast.makeText(getActivity(), "clicked: " + id, Toast.LENGTH_SHORT).show();
//                    deleteItem(pos, id);
                }

                @Override
                public void onEditClicked(int pos, ContainerModel model) {
//                    showAddDollarBankDia(pos, model);
                }

                @Override
                public void onConClicked(int pos, ContainerModel model) {
                    Toast.makeText(getBaseContext(), "clicked: " + model.getName(), Toast.LENGTH_SHORT).show();

                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(containersAdapter);
            containersAdapter.notifyDataSetChanged();
        });

        containersViewModel = new ViewModelProvider(this).get(ContainersViewModel.class);
        containersViewModel.getContainerData();
        containersViewModel.conListMutableLiveData.observe(this, DModels -> {
            if (DModels.size()>0) {

            }
            containersAdapter = new ContainersAdapter(getBaseContext(), (ArrayList<ContainerModel>) DModels);
            containersAdapter.setOnPopupClickListener(new ContainersAdapter.TOnPopupMenuClickListener() {
                @Override
                public void onDeleteClicked(int pos, String id) {
//                    Toast.makeText(getActivity(), "clicked: " + id, Toast.LENGTH_SHORT).show();
//                    deleteItem(pos, id);
                }

                @Override
                public void onEditClicked(int pos, ContainerModel model) {
//                    showAddDollarBankDia(pos, model);
                }

                @Override
                public void onConClicked(int pos, ContainerModel model) {
                    Toast.makeText(getBaseContext(), "clicked: " + model.getName(), Toast.LENGTH_SHORT).show();

                }
            });
            GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(containersAdapter);
            containersAdapter.notifyDataSetChanged();
        });
    }

    public void showAddContainerDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View Alertview = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_container_dialog, null);
        alertDialogBuilder.setView(Alertview);
        final AlertDialog alertDialog = alertDialogBuilder.show();
        EditText conName = (EditText) Alertview.findViewById(R.id.conName);
        EditText conImgUrl = (EditText) Alertview.findViewById(R.id.conImgUrl);
        EditText conComLink = (EditText) Alertview.findViewById(R.id.conComLink);
//        CheckBox sendToGreenCB = (CheckBox) Alertview.findViewById(R.id.sendNotifyToGreenCB);
        TextView send = (TextView) Alertview.findViewById(R.id.send);
        TextView cancel = (TextView) Alertview.findViewById(R.id.cancel);


        send.setOnClickListener(v -> {
            String name = conName.getText().toString();
            String img = conImgUrl.getText().toString();
            String link = conComLink.getText().toString();

            Map<String,Object> addCurrMap = new HashMap<>();
//        addCurrMap.put("dollarId", newId);
            addCurrMap.put("name", name);
            addCurrMap.put("img", img);
            addCurrMap.put("link", link);
            db.collection("containersData").add(addCurrMap)
                    .addOnSuccessListener(documentReference -> {
                        String fireId =documentReference.getId();
                        documentReference.update("fireId", fireId);
                    /*editor.putString("phonename",ti);
                    editor.putString("phoneprice",des);
                    editor.commit();
                    finish();*/
                    }).addOnFailureListener(e -> {
                Toast.makeText(getBaseContext(), "حدث خطأ تأكد من الإتصال بالإنترنت...", Toast.LENGTH_LONG).show();
            });

            alertDialog.dismiss();
        });
        cancel.setOnClickListener(v -> alertDialog.dismiss());

    }

    @Override
    protected void onResume() {
        super.onResume();
        coinsParent.setVisibility(View.INVISIBLE);
        eyeParent.setVisibility(View.VISIBLE);
    }
}