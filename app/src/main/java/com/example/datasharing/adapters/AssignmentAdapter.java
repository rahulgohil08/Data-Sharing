package com.example.datasharing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasharing.database.entities.Assignment;
import com.example.datasharing.databinding.LayoutAssignmentBinding;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private Context context;
    private List<Assignment> documentList;
    private AssignmentInterface documentInterface;
    private boolean isTeacher;


    public AssignmentAdapter(Context context, List<Assignment> documentList, AssignmentInterface documentInterface, boolean isTeacher) {
        this.context = context;
        this.documentList = documentList;
        this.documentInterface = documentInterface;
        this.isTeacher = isTeacher;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutAssignmentBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Assignment data = documentList.get(position);
        holder.binding.title.setText(data.getTitle());
        holder.binding.description.setText(data.getDescription());

        if (!isTeacher) {
            holder.binding.imageDelete.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            documentInterface.onClick(data);
        });

        holder.itemView.setOnLongClickListener(view -> {
            documentInterface.onClick(data);
            return true;
        });

        holder.binding.imageView.setOnClickListener(view -> {
            documentInterface.onView(data);
        });
        holder.binding.imageDelete.setOnClickListener(view -> {
            documentInterface.onDelete(data);
        });

    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutAssignmentBinding binding;

        public ViewHolder(@NonNull LayoutAssignmentBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public interface AssignmentInterface {
        void onClick(Assignment assignment);

        void onDelete(Assignment assignment);

        void onLongClick(Assignment assignment);

        void onView(Assignment assignment);
    }

}
