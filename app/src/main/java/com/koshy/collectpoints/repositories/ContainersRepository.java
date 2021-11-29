package com.koshy.collectpoints.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.koshy.collectpoints.models.ContainerModel;

import java.util.ArrayList;

public class ContainersRepository {
    ArrayList<ContainerModel> containerModelArrayList;
    FirebaseFirestore firestore;
    ReturnValueListener returnValueListener;
    public ContainersRepository() {
        containerModelArrayList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
//        fetchList();
        addListener();
    }
    public ArrayList<ContainerModel> getMatchItems() {
        return containerModelArrayList;
    }

    public interface ReturnValueListener {
        void returnList(ArrayList<ContainerModel> list, boolean auto);
    }

    public void setOnReturnValueListener(ReturnValueListener returnValueListener) {
        this.returnValueListener = returnValueListener;
    }

    public void fetchList() {
        firestore.collection("phonesPrice")
                .orderBy("PhoneId", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                ContainerModel phoneDataModel = queryDocumentSnapshot.toObject(ContainerModel.class);
                                containerModelArrayList.add(phoneDataModel);
                            }
                            returnValueListener.returnList(containerModelArrayList,false);
                            addListener();
                        } else if (task.getException() != null){
                            Log.d("cocacolaerror", "fuel repo frtch error" + task.getException().getMessage());
                            return;
                        }
                    }
                });

    }

    private void addListener(){
        firestore.collection("containersData")
//                .orderBy("PhoneId", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.d("cocacola", "Listen failed: ", error);
                            return;
                        }
                        ArrayList<ContainerModel> matchItemList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value){
                            String fireId = doc.getString("fireId");
                            String img = doc.getString("img");
                            String name = doc.getString("name");
                            String link = doc.getString("link") ;
                            ContainerModel cm = new ContainerModel(img, name, link);
                            cm.setFireId(fireId);
                            matchItemList.add(cm);
                        }
                        returnValueListener.returnList(matchItemList,true);
                    }
                });
    }

    private void addUserListener(){
        firestore.collection("userData")
//                .orderBy("PhoneId", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.d("cocacola", "Listen failed: ", error);
                            return;
                        }
                        ArrayList<ContainerModel> matchItemList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value){
                            String fireId = doc.getString("fireId");
                            String img = doc.getString("img");
                            String name = doc.getString("name");
                            String link = doc.getString("link") ;
                            ContainerModel cm = new ContainerModel(img, name, link);
                            cm.setFireId(fireId);
                            matchItemList.add(cm);
                        }
                        returnValueListener.returnList(matchItemList,true);
                    }
                });
    }
}
