package com.picon.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.picon.utils.databinding.ItemOptionBinding;

public abstract class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private final String[] list;

    public OptionAdapter(@NonNull String[] list) {
        this.list = list;
    }

    public abstract void onSelect(String item);

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOptionBinding binding = ItemOptionBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new OptionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        holder.bind(list[position]);
        holder.listener(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {

        private final ItemOptionBinding binding;

        public OptionViewHolder(@NonNull ItemOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(String name) {
            binding.actionName.setText(name);
        }

        private void listener(String name) {
            itemView.setOnClickListener(v -> onSelect(name));
        }
    }
}
