package mx.com.navcas.ejerciciosqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import mx.com.navcas.ejerciciosqlite.dao.SQLController;

public class MainActivity extends AppCompatActivity {

    private ListView listaCursos;
    private SQLController dbConnection;

    private TextView tvCursoID;
    private TextView tvCursoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbConnection = new SQLController(this);
        dbConnection.openDataBase();
        listaCursos = (ListView) findViewById(R.id.lvDataList);
        Cursor cursor = dbConnection.getAllData();
        fillListaCursos(cursor);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();*/
                openAddActivity();
            }
        });

        listaCursos.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvCursoID = (TextView) view.findViewById(R.id.curso_id);
                tvCursoName = (TextView) view.findViewById(R.id.curso_name);

                String pCursoID = tvCursoID.getText().toString();
                String pCursoName = tvCursoName.getText().toString();

                Intent updateCurso = new Intent(getApplicationContext(), AddCurso.class);
                updateCurso.putExtra(AddCurso.KEY_NAME, pCursoName);
                updateCurso.putExtra(AddCurso.KEY_ID, pCursoID);

                startActivity(updateCurso);
            }
        });

        listaCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteCursor item = (SQLiteCursor) listaCursos.getItemAtPosition(position);

                Log.d("long click asd: ",item.getString(1));
                showResult("¿Desea eliminar a \""+item.getString(1)+"\"?", item.getString(0));
                return Boolean.TRUE;
            }
        });

    }

    private void fillListaCursos(Cursor cursor) {
        //name of the table columns
        String[] cols = dbConnection.getAllColumns();

        int[] colIDs = getIds();

        //creating adapter for handling data
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.format_cell, cursor, cols, colIDs
        );

        adapter.notifyDataSetChanged();

        listaCursos.setAdapter(adapter);
    }

    private int[] getIds(){
        //Ids from list view layour declared
        return  new int[]{
                R.id.curso_id,
                R.id.curso_name
        };
    }
    private void showResult(String message,final String id) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Confirmación:");
        alert.setMessage(message);

        alert.setButton("Ver lista", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        alert.setButton2("Eliminar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dbConnection.deleteData(Long.parseLong(id));
                Cursor cursor = dbConnection.getAllData();
                fillListaCursos(cursor);
            }
        });
        alert.show();
    }

    public void openAddActivity(){
        Intent add = new Intent(MainActivity.this,AddCurso.class);
        startActivity(add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
