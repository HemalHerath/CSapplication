package com.example.beiber.csapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    FloatingActionButton fab;//floating button for send
    FirebaseAuth firebaseAuth;//to check user logging
    private DatabaseReference databaseReference;//to save messages
    private DatabaseReference databaseReferenceC;//to remove data when deleting user
    ListView listViewMessages;//show messages in a lst view
    List<ChatMessage>messageList;//array list to save messages
    private DrawerLayout drawerLayout;//slide bar
    private ActionBarDrawerToggle toggle;//sliding

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //saving messages in message child
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
        //list view and array list
        listViewMessages = (ListView)findViewById(R.id.list_of_message);
        messageList = new ArrayList<>();
        //get current user and check
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //floating send button action
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                EditText input = (EditText) findViewById(R.id.input);
                String text = input.getText().toString().trim();
                //message field empty stop executing futher
                if(TextUtils.isEmpty(text))
                {
                    return;
                }

                FirebaseDatabase.getInstance().getReference("Message").push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                input.setText("");
            }
        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    //when back press give a alert box
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
        builder.setMessage("Are you sure want to exit");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    //when starts the page retrieve the messages
    protected void onStart()
    {
        super.onStart();
        //Value listener for retreve data
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                messageList.clear();
                //for loop for retreve all the children
                for(DataSnapshot messageSnapshot : dataSnapshot.getChildren())
                {
                    ChatMessage chatMessage = messageSnapshot.getValue(ChatMessage.class);
                    messageList.add(chatMessage);
                }
                ChatAdapter adapter = new ChatAdapter(ChatActivity.this,messageList);
                listViewMessages.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        int id =  item.getItemId();

        if(id==R.id.logout)
        {
            Toast.makeText(ChatActivity.this, "Successfully Logout",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential("user@example.com","password1234");

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ChatActivity.this, "deleted",Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Toast.makeText(ChatActivity.this, "Not deleted. Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void removeUserData()
    {
        databaseReferenceC = FirebaseDatabase.getInstance().getReference("users/profiles/");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReferenceC.child(user.getUid()).removeValue();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        //Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile)
        {
            startActivity(new Intent(this,Profile.class));

        } else if (id == R.id.nav_all)
        {
            startActivity(new Intent(this,ImageListActivity.class));
        } else if (id == R.id.nav_delete)
        {
            delete();
            removeUserData();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        } else if (id == R.id.nav_edit)
        {
            startActivity(new Intent(this, ProActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
