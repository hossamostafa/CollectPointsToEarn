package com.koshy.collectpoints.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.koshy.collectpoints.models.ContainerModel;
import com.koshy.collectpoints.repositories.ContainersRepository;

import java.util.ArrayList;
import java.util.List;

public class ContainersViewModel extends ViewModel {
    public MutableLiveData<List<ContainerModel>> phListMutableLiveData;
    ContainersRepository containersRepository;
 
    public ContainersViewModel() {
        containersRepository = new ContainersRepository();
    }

    public LiveData<ContainerModel> getPhoneData(){
        if (phListMutableLiveData == null){
            phListMutableLiveData = new MutableLiveData<>();
            containersRepository.setOnReturnValueListener(new ContainersRepository.ReturnValueListener() {
                @Override
                public void returnList(ArrayList<ContainerModel> list, boolean auto) {
                    getMatchdata(list,auto);
                }
            });
        }
        return null;
    }

    private void getMatchdata(ArrayList<ContainerModel> list, boolean auto){
        if (auto) {
            if (list != null || list.size() > 0) phListMutableLiveData.setValue(list);
        } else {
            ArrayList<ContainerModel> phoneDataModels = containersRepository.getMatchItems();
            phListMutableLiveData.setValue(phoneDataModels);
        }
    }
}
