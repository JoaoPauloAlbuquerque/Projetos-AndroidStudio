package com.example.calculadora;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadora.model.MyAdapter;
import com.example.calculadora.objetos.ObjetoPrimeiroGrau;
import com.example.calculadora.objetos.ObjetoSegundoGrau;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PrimeiroGrauActivity extends AppCompatActivity {

    private TextView a, b, c;
    private AppCompatButton botaoCalcular;
    private EditText editA, editB, editC;

    private ArrayList<String> lista;

    private RecyclerView rv;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiro_grau);

        iniComponentes();

        editA.addTextChangedListener(new EventoAlteracaoEditText(editA, a, "a"));
        editB.addTextChangedListener(new EventoAlteracaoEditText(editB, b, "b"));
        editC.addTextChangedListener(new EventoAlteracaoEditText(editC, c, "c"));

        editA.setOnEditorActionListener(new EventoPularEditText(editB));
        editB.setOnEditorActionListener(new EventoPularEditText(editC));
        editC.setOnEditorActionListener(new EventoPularEditText(editA));
    }

    private void iniComponentes(){
        botaoCalcular = findViewById(R.id.botao_calcular_primeiro_grau);
        a = findViewById(R.id.texto_a_primeiro_grau);
        b = findViewById(R.id.texto_b_primeiro_grau);
        c = findViewById(R.id.texto_c_primeiro_grau);
        editA = findViewById(R.id.edit_a_primeiro_grau);
        editB = findViewById(R.id.edit_b_primeiro_grau);
        editC = findViewById(R.id.edit_c_primeiro_grau);
        lista = new ArrayList<>();
        rv = findViewById(R.id.recyclerview_primeiro_grau);
    }

    public void voltar (View v){
        finish();
    }

    public void calcular(View view) {
        double a, b, c;
        lista.clear();
        if (editA.getText().toString().isEmpty() || editB.getText().toString().isEmpty() || editC.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Informe os valores de a, b e c!", Toast.LENGTH_SHORT).show();
        } else {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editC.getWindowToken(), 0);
            a = Double.valueOf(editA.getText().toString());
            b = Double.valueOf(editB.getText().toString());
            c = Double.valueOf(editC.getText().toString());
            ObjetoPrimeiroGrau obj = new ObjetoPrimeiroGrau(a, b, c);
            obj.calcular();
            adapter = new MyAdapter(listar(obj));
            rv.setAdapter(adapter);
        }
    }

    private ArrayList<String> listar(ObjetoPrimeiroGrau obj) {
        lista.add(convert(obj.getA()) + "x + (" + convert(obj.getB()) + ") = " + convert(obj.getC()));
        lista.add(convert(obj.getA()) + "x = " + convert(obj.getC()) + (obj.getB() < 0 ? " + " : " - ") + convert(obj.getB()));
        lista.add(convert(obj.getA()) + "x = " + convert(obj.getResultadoPrimeiraParte()));
        lista.add("x = " + convert(obj.getResultadoPrimeiraParte()) + " / " + convert(obj.getA()));
        lista.add("x = " + convert(obj.getX()));

        return lista;
    }

    private String convert(double d){
        return new DecimalFormat("0.00").format(d);
    }

    public void zerar(View view) {
        editA.setText("");
        editB.setText("");
        editC.setText("");

        a.setText("a");
        b.setText("b");
        c.setText("c");

        lista.clear();
        adapter.notifyDataSetChanged();

        editA.requestFocus();

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editA, InputMethodManager.SHOW_IMPLICIT);
    }

    private class EventoAlteracaoEditText implements TextWatcher {

        private EditText edit;
        private TextView txt;
        private String c;

        public EventoAlteracaoEditText(EditText edit, TextView txt, String c){
            this.edit = findViewById(edit.getId());
            this.txt = findViewById(txt.getId());
            this.c = c;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String v = this.edit.getText().toString();
            if(!v.isEmpty()){
                this.txt.setText(v);
            } else {
                this.txt.setText(c);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private class EventoPularEditText implements TextView.OnEditorActionListener{

        private EditText edit;

        public EventoPularEditText(EditText edit){
            this.edit = findViewById(edit.getId());
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_NEXT){
                edit.requestFocus();
                return true;
            } else if(actionId == EditorInfo.IME_ACTION_GO){
                calcular(botaoCalcular);
                return true;
            }
            return false;
        }
    }
}