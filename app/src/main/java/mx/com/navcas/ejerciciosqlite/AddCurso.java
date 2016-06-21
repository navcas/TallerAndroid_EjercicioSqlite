package mx.com.navcas.ejerciciosqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;

import mx.com.navcas.ejerciciosqlite.dao.SQLController;

public class AddCurso extends AppCompatActivity {

    public static final String KEY_NAME = "_NAME";
    public static final String KEY_ID = "_ID";

    private boolean isUpdate = Boolean.FALSE;
    private long idItemUpdate = -1L;

    private EditText etName;
    private SQLController dbConection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curso);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName =  (EditText) findViewById(R.id.etName);
        dbConection = new SQLController(this);
        dbConection.openDataBase();

        //Check if the action is update a existing element
        Intent i = getIntent();
        String pName = i.getStringExtra(KEY_NAME);
        String pID = i.getStringExtra(KEY_ID);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if(isUpdate)
                    updateName();
                else
                    addName();
            }
        });
        isUpdate = (pID != null && !pID.isEmpty());
        if(isUpdate){
            idItemUpdate = Long.parseLong(pID);
            etName.setText(pName);
            fab.setImageResource(R.drawable.ic_save_update);
        }else{
            fab.setImageResource(R.drawable.ic_save);
        }
    }

    public void addName(){
        String name = etName.getText().toString();
        dbConection.insertData(name);
        showResult("Se ha guardado de forma satisfactoria");
    }

    public  void updateName(){
        String name = etName.getText().toString();
        dbConection.updatetData(idItemUpdate,name);
        showResult("Se ha actualizado de forma satisfactoria");
    }

    private void showResult(String message) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Resultado de la operación");
        alert.setMessage(message);

        alert.setButton("Ver lista", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                Intent mainActivity= new Intent(AddCurso.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainActivity);
            }
        });
        alert.setButton2("Agregar nuevo",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                etName.setText("");
            }
        });
        alert.show();
    }
}
