package gq.altafchaudhari.itodo.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;

import dagger.android.AndroidInjection;
import es.dmoral.toasty.Toasty;
import gq.altafchaudhari.itodo.R;
import gq.altafchaudhari.itodo.adapter.ToDoAdapter;
import gq.altafchaudhari.itodo.clickcallback.ToDoClickCallBack;
import gq.altafchaudhari.itodo.databinding.ActivityMainBinding;
import gq.altafchaudhari.itodo.model.ToDo;
import gq.altafchaudhari.itodo.view_model.ToDoViewModel;

public class MainActivity extends AppCompatActivity {
    private ToDoViewModel todoViewModel;
    ActivityMainBinding binding;
    ToDoAdapter adapter;
    private static final String TODO = "todo";
    private Toolbar mTopToolbar;

    public static boolean checked;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        changeBarColor();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.noteRecyclerView.setHasFixedSize(true);
        binding.noteRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(500);
        itemAnimator.setRemoveDuration(500);
        binding.noteRecyclerView.setItemAnimator(itemAnimator);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setUpViewModel();
        setUpAdapter();
        //swipeDelete();

        binding.addTodoFab.setOnClickListener(v -> {
            Intent addIntent = new Intent(MainActivity.this,AddToDoActivity.class);
            startActivity(addIntent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                todoViewModel.deleteAll();
                Toasty.error(this, "Deleted Successfully!", Toasty.LENGTH_SHORT,false).show();
                return true;
            case R.id.about:
                showAboutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setUpAdapter(){
        ToDoClickCallBack todoClickCallBack = todo -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Bundle mBundle = new Bundle();
            ToDo todo1 = todo;
            mBundle.putInt("id", todo1.getId());
            mBundle.putString("title", todo1.getTitle());
            mBundle.putString("desc", todo1.getDescription());
            mBundle.putString("date", todo1.getDate());
            mBundle.putString("color", todo1.getNote_color());
            mBundle.putBoolean("finish", todo1.isIs_finished());
            intent.putExtras(mBundle);
          //intent.putExtra(TODO, todo);
          startActivity(intent);
        };
        adapter = new ToDoAdapter(todoClickCallBack, this);
        binding.noteRecyclerView.setAdapter(adapter);
    }

    private void setUpViewModel(){
        todoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        todoViewModel.getTodoList().observe(this,todos -> {
            adapter.submitList(todos);
        });
    }
    /*private void swipeDelete(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                showDeleteDialog(viewHolder);
            }
        }).attachToRecyclerView(binding.noteRecyclerView);
    }*/


    /*private void showDeleteDialog(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure, you want to delete this?").
                setPositiveButton("Yes", (dialog, which) -> {
                    todoViewModel.delete(adapter.getTodoAt(viewHolder.getAdapterPosition()));
                    dialog.dismiss();
                    Toasty.error(MainActivity.this, "Todo Deleted!", Toasty.LENGTH_SHORT,false).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Delete ToDo");
        alertDialog.show();

    }*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeBarColor()
    {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.setNavigationBarColor(Color.parseColor("#FFFFFF"));
    }

    private void showAboutDialog()
    {
        new TTFancyGifDialog.Builder(MainActivity.this)
                .setTitle("iToDo App")
                .setMessage("Developer: Altaf Chaudhari\nCheck repository on github\nhttps://github.com/altafc22/iToDo")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Cancel")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.isomatric_checkmark)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                /*.OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                    }
                })*/
                .build();
    }

}
