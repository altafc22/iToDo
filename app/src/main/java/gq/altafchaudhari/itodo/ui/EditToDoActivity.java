package gq.altafchaudhari.itodo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;
import gq.altafchaudhari.itodo.CircleCheckBox;
import gq.altafchaudhari.itodo.R;
import gq.altafchaudhari.itodo.databinding.ActivityEditTodoBinding;
import gq.altafchaudhari.itodo.model.ToDo;
import gq.altafchaudhari.itodo.view_model.ToDoViewModel;

public class EditToDoActivity extends AppCompatActivity {

    ActivityEditTodoBinding binding;
    ToDo todo;

    Toolbar mTopToolbar;
    ToDoViewModel viewModel;
    String note_color = "";
    int note_id ;

    String selectedDrawable = "bg_back";
    boolean is_finished;
    boolean colorChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        changeBarColor();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_todo);



       /* getSupportActionBar().setTitle("Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       /* todo = getIntent().getParcelableExtra("todo");
        System.out.println(todo.getDate());
        System.out.println(todo.getDescription());
        System.out.println(todo.getNote_color());
        System.out.println(todo.getTitle());
        System.out.println(todo.isIs_finished());*/

        populateFields();


        saveTodo();

        binding.noteMauve.setOnClickListener(v -> {

            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_mauve";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.editNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.editNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.noteLush.setOnClickListener(v -> {
            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_lush";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.editNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.editNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.noteFrost.setOnClickListener(v -> {
            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_frost";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.editNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.editNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.noteMorning.setOnClickListener(v -> {
            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_sweet_morning";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.editNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.editNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.checkboxFinished.setOnCheckedChangeListener(new CircleCheckBox.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CircleCheckBox view, boolean isChecked) {
                is_finished = isChecked;
                if(isChecked)
                    binding.textViewFinishedStatus.setTextColor(getColor(R.color.colorDarkGreen));
                else
                    binding.textViewFinishedStatus.setTextColor(getColor(R.color.colorText));
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void populateFields(){
        note_id = getIntent().getExtras().getInt("id");
        binding.editTextTitle.setText(getIntent().getExtras().getString("title"));
        binding.editTextDescription.setText(getIntent().getExtras().getString("desc"));
        binding.textViewDate.setText(getIntent().getExtras().getString("date"));
        binding.checkboxFinished.setChecked(getIntent().getExtras().getBoolean("finish"));
        note_color = getIntent().getExtras().getString("color");
        is_finished = getIntent().getExtras().getBoolean("finish");
        if(is_finished)
            binding.textViewFinishedStatus.setTextColor(getColor(R.color.colorDarkGreen));
        else
            binding.textViewFinishedStatus.setTextColor(getColor(R.color.colorText));
        if(!note_color.equals("bg_back"))
        {
            selectedDrawable = note_color;
            int id = getResources().getIdentifier(note_color, "drawable", getPackageName());
            binding.editNoteLayout.setBackground(getDrawable(id));
        }
        else
        {
            binding.editNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            selectedDrawable = "bg_back";
        }
    }

    private void saveTodo(){
        binding.saveTodo.setOnClickListener(v -> {
                String title = binding.editTextTitle.getText().toString();
                String description = binding.editTextDescription.getText().toString();
                String note_color = selectedDrawable;
                //int id = todo.getId();

            if (!title.trim().isEmpty() || !description.trim().isEmpty()) {
                viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);

                ToDo todo = new ToDo(note_id,title,description,getCurrentDate(),note_color,is_finished);
                viewModel.update(todo);
                Toasty.success(this, "ToDo Updated", Toasty.LENGTH_SHORT,false).show();
                Intent gotoMainIntent = new Intent(EditToDoActivity.this, MainActivity.class);
                startActivity(gotoMainIntent);
                finish();
            }else{
                Toasty.warning(this, "Please insert note information", Toasty.LENGTH_SHORT,false).show();
            }
        });
    }

    private String getCurrentDate()
    {
        Calendar calendar =  Calendar.getInstance();;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        return date;
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
