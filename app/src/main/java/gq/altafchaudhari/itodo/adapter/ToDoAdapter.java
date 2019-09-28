package gq.altafchaudhari.itodo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;
import gq.altafchaudhari.itodo.CircleCheckBox;
import gq.altafchaudhari.itodo.clickcallback.ToDoClickCallBack;
import gq.altafchaudhari.itodo.databinding.TodoCardItemBinding;
import gq.altafchaudhari.itodo.model.ToDo;
import gq.altafchaudhari.itodo.ui.EditToDoActivity;
import gq.altafchaudhari.itodo.ui.MainActivity;
import gq.altafchaudhari.itodo.view_model.ToDoViewModel;

public class ToDoAdapter extends ListAdapter<ToDo, ToDoAdapter.TodoHolder> {


    TodoCardItemBinding binding;
    Context context;
    ToDoViewModel viewModel;
    private ToDoClickCallBack todoClickCallBack;
    String note_color,title,description,date;
    boolean is_finished;
    int id;
    ToDo todo;

    public ToDoAdapter(ToDoClickCallBack todoClickCallBack, Context context) {
        super(DIFF_CALLBACK);
        this.todoClickCallBack = todoClickCallBack;
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<ToDo> DIFF_CALLBACK = new DiffUtil.ItemCallback<ToDo>() {
        @Override
        public boolean areItemsTheSame(ToDo oldItem, ToDo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(ToDo oldItem, ToDo newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = TodoCardItemBinding.inflate(layoutInflater, parent, false);
        return new TodoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        todo = getItem(position);
        holder.bind(todo, todoClickCallBack);
        is_finished = todo.isIs_finished();

        /*binding.checkboxFinished.setOnCheckedChangeListener(new CircleCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CircleCheckBox view, boolean isChecked) {

                id = todo.getId();
                title = todo.getTitle();
                description = todo.getDescription();
                date = todo.getDate();
                note_color = todo.getNote_color();
                is_finished = isChecked;
                System.out.println(id+"|"+title+"|"+description+"|"+date+"|"+note_color+"|"+true);
                ToDo new_todo = new ToDo(id,title,description,date,note_color,isChecked);
                viewModel = ViewModelProviders.of((FragmentActivity) view.getContext()).get(ToDoViewModel.class);
                viewModel.update(new_todo);
                Toasty.success(context, "ToDo Updated", Toasty.LENGTH_SHORT,false).show();
            }
        });*/

        binding.editTodo.setOnClickListener(v -> {
            Intent editIntent = new Intent(context, EditToDoActivity.class);
            id = todo.getId();
            title = todo.getTitle();
            description = todo.getDescription();
            date = todo.getDate();
            note_color = todo.getNote_color();
            is_finished = todo.isIs_finished();

            System.out.println(id+"|"+title+"|"+description+"|"+date+"|"+note_color+"|"+true);

            Bundle mBundle = new Bundle();
            mBundle.putInt("id", id);
            mBundle.putString("title", title);
            mBundle.putString("desc", description);
            mBundle.putString("date", date);
            mBundle.putString("color", note_color);
            mBundle.putBoolean("finish", is_finished);
            editIntent.putExtras(mBundle);

            context.startActivity(editIntent);
        });

    }




    public ToDo getTodoAt(int position) {
        return getItem(position);
    }

    class TodoHolder extends RecyclerView.ViewHolder{

        public TodoHolder(TodoCardItemBinding todoItemBinding) {
            super(binding.getRoot());
            binding = todoItemBinding;
        }
        public void bind(ToDo todo, ToDoClickCallBack todoClickCallBack){
            itemView.setOnClickListener(v -> todoClickCallBack.showTodo(todo));
            binding.textViewTitle.setText(todo.getTitle());
            binding.textViewDescription.setText(todo.getDescription());
            binding.textViewDate.setText(todo.getDate());
            binding.checkboxFinished.setChecked(todo.isIs_finished());
            int id = context.getResources().getIdentifier(todo.getNote_color(), "drawable", context.getPackageName());
            binding.cardColor.setBackground(ContextCompat.getDrawable(context, id));
        }
    }
}
