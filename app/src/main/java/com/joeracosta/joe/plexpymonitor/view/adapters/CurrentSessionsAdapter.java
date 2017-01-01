package com.joeracosta.joe.plexpymonitor.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.joeracosta.joe.plexpymonitor.R;
import com.joeracosta.joe.plexpymonitor.databinding.SessionCardBinding;
import com.joeracosta.joe.plexpymonitor.model.CurrentPlexActivity;
import com.joeracosta.joe.plexpymonitor.viewmodels.SessionCardViewModel;

import java.util.ArrayList;

/**
 * Created by jacosta on 12/31/16.
 */

public class CurrentSessionsAdapter extends RecyclerView.Adapter<CurrentSessionsAdapter.SessionViewHolder> {

    private ArrayList<CurrentPlexActivity.Response.Data.Session> mSessions;

    public CurrentSessionsAdapter(ArrayList<CurrentPlexActivity.Response.Data.Session> sessions) {
        mSessions = sessions;
    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SessionCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.session_card, parent, false);
        return new SessionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        holder.bindSession(mSessions.get(position));
    }

    @Override
    public int getItemCount() {
        return mSessions.size();
    }

    static class SessionViewHolder extends RecyclerView.ViewHolder{

        SessionCardBinding binding;
        SessionCardViewModel viewModel;

        public SessionViewHolder(SessionCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewModel = new SessionCardViewModel();
            binding.setViewModel(viewModel);
        }

        void bindSession(CurrentPlexActivity.Response.Data.Session session){
            viewModel.setSession(session);
            binding.executePendingBindings();
        }
    }
}
