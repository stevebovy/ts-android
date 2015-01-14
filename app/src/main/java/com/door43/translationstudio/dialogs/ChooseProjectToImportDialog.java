package com.door43.translationstudio.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.door43.translationstudio.R;
import com.door43.translationstudio.events.ChoseProjectEvent;
import com.door43.translationstudio.events.ChoseProjectToImportEvent;
import com.door43.translationstudio.network.Peer;
import com.door43.translationstudio.projects.SudoProject;
import com.door43.translationstudio.projects.Model;
import com.door43.translationstudio.projects.Project;
import com.door43.translationstudio.util.MainContext;

/**
 * This is basically the same as the ChooseProjectDialog but with some alterations for importing projects.
 * These will probably diverge later on so we are spliting them now.
 */
public class ChooseProjectToImportDialog extends DialogFragment {
    private ModelItemAdapter mModelItemAdapter;
    private Model[] mModelList = null;
    private Peer mPeer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(R.string.projects);
        View v = inflater.inflate(R.layout.dialog_choose_project, container, false);

        ListView listView = (ListView)v.findViewById(R.id.listView);

        if(mModelList != null) {
            if(mModelItemAdapter == null) mModelItemAdapter = new ModelItemAdapter(MainContext.getContext(), mModelList, false);
            // connect adapter
            listView.setAdapter(mModelItemAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Model m = mModelItemAdapter.getItem(i);
                    if(m.getClass().equals(SudoProject.class)) {
                        // re-load list
                        mModelItemAdapter.changeDataSet(((SudoProject)m).getChildren());
                    } else {
                        // return the selected project.
                        Project p = (Project)m;
                        MainContext.getEventBus().post(new ChoseProjectToImportEvent(mPeer, p, ChooseProjectToImportDialog.this));
                        // NOTE: the caller should close this dialog
                    }
                }
            });
        } else {
            dismiss();
        }

        return v;
    }

    /**
     * Specifies the model list to use in the dialog.
     * This must be called before showing the dialog.
     */
    public void setImportDetails(Peer server, Model[] models) {
        mPeer = server;
        mModelList = models;
    }
}