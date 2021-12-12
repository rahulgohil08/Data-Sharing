package com.example.datasharing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datasharing.database.entities.Document;
import com.example.datasharing.databinding.LayoutDocumentBinding;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private Context context;
    private List<Document> documentList;
    private DocumentInterface documentInterface;


    public DocumentAdapter(Context context, List<Document> documentList, DocumentInterface documentInterface) {
        this.context = context;
        this.documentList = documentList;
        this.documentInterface = documentInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutDocumentBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Document data = documentList.get(position);
        holder.binding.title.setText(data.getTitle());
        holder.binding.description.setText(data.getDescription());

        holder.itemView.setOnClickListener(view -> {
            documentInterface.onClick(data);
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

        LayoutDocumentBinding binding;

        public ViewHolder(@NonNull LayoutDocumentBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public interface DocumentInterface {
        void onClick(Document document);

        void onDelete(Document document);
    }

}
