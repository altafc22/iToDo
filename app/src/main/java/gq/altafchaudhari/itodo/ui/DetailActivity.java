package gq.altafchaudhari.itodo.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import dagger.android.AndroidInjection;
import gq.altafchaudhari.itodo.R;
import gq.altafchaudhari.itodo.databinding.ActivityDetailBinding;
import gq.altafchaudhari.itodo.model.ToDo;
import gq.altafchaudhari.itodo.view_model.ToDoViewModel;

public class DetailActivity extends AppCompatActivity {

    private static final String TODO = "todo";
    ActivityDetailBinding binding;
    ToDoViewModel viewModel;
    int note_id;
    String note_color,title,description,date;
    boolean is_finished;
    ToDo todo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        changeBarColor();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        updateView();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateView(){
        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        note_id = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");
        description = getIntent().getExtras().getString("desc");
        date = getIntent().getExtras().getString("date");
        is_finished = getIntent().getExtras().getBoolean("finish");
        note_color = getIntent().getExtras().getString("color");

        if(is_finished)
        {
            binding.textViewFinishedStatus.setText("Task Finished!");
            binding.textViewFinishedStatus.setTextColor(getColor(R.color.colorDarkGreen));
        }
        else
        {
            binding.textViewFinishedStatus.setText("Task Not Finished!");
            binding.textViewFinishedStatus.setTextColor(getColor(R.color.colorFunRed));
        }

        System.out.println(note_id+"|"+title+"|"+description+"|"+date+"|"+is_finished+"|"+note_color);

        binding.titleText.setText(title);
        binding.detailText.setText(description);
        binding.textViewDate.setText(date);

        if(!note_color.equals("bg_back"))
        {
            note_color = note_color;
            int id = getResources().getIdentifier(note_color, "drawable", getPackageName());
            binding.detailNoteLayout.setBackground(getDrawable(id));
        }
        else
        {
            binding.detailNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            note_color = "bg_back";
        }

        binding.deleteTodo.setOnClickListener(v -> {
            todo = new ToDo(note_id,title,description,date,note_color,is_finished);
            showDeleteDialog(todo);
        });
        binding.editTodo.setOnClickListener( v -> {
            Intent editIntent = new Intent(DetailActivity.this,EditToDoActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putInt("id", note_id);
            mBundle.putString("title", title);
            mBundle.putString("desc", description);
            mBundle.putString("date", date);
            mBundle.putString("color", note_color);
            mBundle.putBoolean("finish", is_finished);
            editIntent.putExtras(mBundle);
            startActivity(editIntent);
        });
    }

    private void showDeleteDialog(ToDo todo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure, you want to delete this?").
                setPositiveButton("Yes", (dialog, which) -> {
                    viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
                    viewModel.delete(todo);
                    dialog.dismiss();
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                    Toast.makeText(this, "ToDo deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Delete ToDo");
        alertDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeBarColor()
    {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.setNavigationBarColor(Color.parseColor("#FFFFFF"));
    }


}
