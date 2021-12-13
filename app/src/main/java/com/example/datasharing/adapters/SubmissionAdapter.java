package com.example.datasharing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasharing.database.entities.SubmissionWithStudent;
import com.example.datasharing.databinding.LayoutSubmissionBinding;

import java.util.List;

public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.ViewHolder> {

    private Context context;
    private List<SubmissionWithStudent> documentList;
    private SubmissionInterface documentInterface;
    private boolean isTeacher;


    public SubmissionAdapter(Context context, List<SubmissionWithStudent> documentList, SubmissionInterface documentInterface, boolean isTeacher) {
        this.context = context;
        this.documentList = documentList;
        this.documentInterface = documentInterface;
        this.isTeacher = isTeacher;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutSubmissionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubmissionWithStudent data = documentList.get(position);
        holder.binding.title.setText(data.getUser().getName());

        if (!isTeacher) {
//            holder.binding.imageDelete.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            documentInterface.onClick(data);
        });

        holder.itemView.setOnLongClickListener(view -> {
            documentInterface.onView(data);
            return true;
        });


    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutSubmissionBinding binding;

        public ViewHolder(@NonNull LayoutSubmissionBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public interface SubmissionInterface {
        void onClick(SubmissionWithStudent submissionWithStudent);

        void onView(SubmissionWithStudent submissionWithStudent);
    }

}
