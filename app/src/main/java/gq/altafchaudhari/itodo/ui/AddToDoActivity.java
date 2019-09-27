package gq.altafchaudhari.itodo.ui;

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
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;
import gq.altafchaudhari.itodo.R;
import gq.altafchaudhari.itodo.databinding.ActivityAddToDoBinding;
import gq.altafchaudhari.itodo.model.ToDo;
import gq.altafchaudhari.itodo.view_model.ToDoViewModel;

public class AddToDoActivity extends AppCompatActivity {

    Toolbar mTopToolbar;
    boolean colorChanged = false;
    String selectedDrawable = "bg_back";

    ToDoViewModel viewModel;
    ActivityAddToDoBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        changeBarColor();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_to_do);

        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add ToDo");*/

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.saveTodo.setOnClickListener(v -> {
            saveTodo();
        });

        binding.noteMauve.setOnClickListener(v -> {

            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_mauve";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.addNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.addNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.noteLush.setOnClickListener(v -> {
            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_lush";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.addNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.addNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.noteFrost.setOnClickListener(v -> {
            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_frost";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.addNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.addNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

        binding.noteMorning.setOnClickListener(v -> {
            if(!colorChanged)
            {
                colorChanged = true;
                selectedDrawable = "bg_sweet_morning";
                int id = getResources().getIdentifier(selectedDrawable, "drawable", getPackageName());
                binding.addNoteLayout.setBackground(getDrawable(id));
            }
            else
            {
                colorChanged = false;
                selectedDrawable = "bg_back";
                binding.addNoteLayout.setBackground(new ColorDrawable(0xffFFFFFF));
            }
        });

    }

    private void saveTodo() {
        String title = binding.editTextTitle.getText().toString();
        String description = binding.editTextDescription.getText().toString();
        String note_color = selectedDrawable;
        boolean is_finished = false;

        System.out.println(title+"|"+description+"|"+getCurrentDate()+"|"+note_color+"|"+is_finished);

        if (!title.trim().isEmpty() || !description.trim().isEmpty()) {
            viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);

            ToDo todo  = new ToDo(title,description,getCurrentDate(),note_color,is_finished);
            viewModel.insert(todo);
            Toasty.success(this, "ToDo Created!", Toasty.LENGTH_SHORT,false).show();
            Intent gotoMaintIntent = new Intent(AddToDoActivity.this, MainActivity.class);
            startActivity(gotoMaintIntent);
            finish();
        }else{
            Toasty.warning(this, "Please insert a note details", Toasty.LENGTH_SHORT,false).show();
        }

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

    private String getCurrentDate()
    {
        Calendar calendar =  Calendar.getInstance();;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        return date;
    }



}
