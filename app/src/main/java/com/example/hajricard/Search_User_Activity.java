package com.example.hajricard;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hajricard.Adapter.SearchUserRecyclerViewAdapter;
import com.example.hajricard.utils.FirebaseUtil;
import com.example.hajricard.utils.Model.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Search_User_Activity extends AppCompatActivity {

    EditText search_edit_text;
    ImageButton back_button, search_user_button;
    RecyclerView search_recycler_view;
    SearchUserRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_user);

        search_edit_text = findViewById(R.id.search_edit_text);
        back_button = findViewById(R.id.back_button);
        search_user_button = findViewById(R.id.search_user_button);
        search_recycler_view = findViewById(R.id.search_recycler_view);

        search_edit_text.requestFocus();

        back_button.setOnClickListener(v -> onBackPressed());

        search_user_button.setOnClickListener(v -> {
            String searchterm = search_edit_text.getText().toString();
            if (searchterm.isEmpty() || searchterm.length() < 3) {
                search_edit_text.setError("Invalid Name");
                return;
            }

            setupsearchRecylerView(searchterm);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void setupsearchRecylerView(String searchterm) {
        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username",searchterm);


        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class).build();

        adapter = new SearchUserRecyclerViewAdapter(options, getApplicationContext());
        search_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        search_recycler_view.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }
}