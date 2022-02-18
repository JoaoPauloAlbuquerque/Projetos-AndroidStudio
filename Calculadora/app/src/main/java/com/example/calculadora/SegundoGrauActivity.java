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
import com.example.calculadora.objetos.ObjetoSegundoGrau;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SegundoGrauActivity extends AppCompatActivity {

    private TextView a, b, c;
    private AppCompatButton botaoCalcular, botaoZerar;
    private EditText editA, editB, editC;

    private RecyclerView rv;
    MyAdapter adapter;

    ArrayList<String> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo_grau);

        iniComponentes();

        editA.addTextChangedListener(new EventoAlteracaoEditText(editA, a, "a"));
        editB.addTextChangedListener(new EventoAlteracaoEditText(editB, b, "b"));
        editC.addTextChangedListener(new EventoAlteracaoEditText(editC, c, "c"));

        editA.setOnEditorActionListener(new EventoPularEditText(editB));
        editB.setOnEditorActionListener(new EventoPularEditText(editC));
        editC.setOnEditorActionListener(new EventoPularEditText(editA));
    }

    private void iniComponentes() {
        a = findViewById(R.id.texto_a);
        b = findViewById(R.id.texto_b);
        c = findViewById(R.id.texto_c);
        botaoCalcular = findViewById(R.id.botao_calcular);
        botaoZerar = findViewById(R.id.botao_zerar);
        editA = findViewById(R.id.edit_a);
        editB = findViewById(R.id.edit_b);
        editC = findViewById(R.id.edit_c);
        rv = findViewById(R.id.recyclerview);
        lista = new ArrayList<>();
    }

    public void voltar(View v) {
        finish();
    }

    public void calcular(View v) {
        double a, b, c;
        lista.clear();
        if (editA.getText().toString().isEmpty() || editB.getText().toString().isEmpty() || editC.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Informe os valores de a, b e c!", Toast.LENGTH_SHORT).show();
        } else {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editC.getWindowToken(), 0);
            a = Double.valueOf(editA.getText().toString());
            b = Double.valueOf(editB.getText().toString());
            c = Double.valueOf(editC.getText().toString());
            ObjetoSegundoGrau obj = new ObjetoSegundoGrau(a, b, c);
            int tipo = obj.calcularRaizes(obj.calcularDelta());
            adapter = new MyAdapter(listar(obj, tipo));
            rv.setAdapter(adapter);
        }
    }

    private ArrayList<String> listar(ObjetoSegundoGrau obj, int tipo) {
        lista.add("Fórmula: Δ = (b)² - 4 * a * c");
        lista.add("Δ = (" + convert(obj.getB()) + ")² - 4 * " + convert(obj.getA()) + " * " + convert(obj.getC()));
        lista.add("Δ = (" + convert(obj.getRetultadoContaPrimeiraParteDelta()) + ") - (" + convert(obj.getRetultadoContaSegundaParteDelta()) + ")");
        lista.add("Δ = " + convert(obj.getDelta()));
        lista.add(" ");
        if (tipo < 0) {
            lista.add(obj.getMsg());
        } else if (tipo == 0) {
            lista.add(obj.getMsg());
            lista.add("");
            lista.add("Fórmula: X = (-b ± √Δ) / (2 * a)");
            lista.add("X = (-(" + convert(obj.getB()) + ") + √" + convert(obj.getDelta()) + ") / (2 * " + convert(obj.getA()) + ")");
            lista.add("X = " + convert(obj.getResultadoX1Cima()) + " / " + convert(obj.getResultadoX1Baixo()));
            lista.add("X = " + convert(obj.getX1()));
            lista.add("Solução = {" + convert(obj.getX1()) + "}");
        } else {
            lista.add("Fórmula: X = (-b ± √Δ) / (2 * a)");
            lista.add("X' = (-(" + convert(obj.getB()) + ") + √" + convert(obj.getDelta()) + ") / (2 * " + convert(obj.getA()) + ")");
            lista.add("X' = " + convert(obj.getResultadoX1Cima()) + " / " + convert(obj.getResultadoX1Baixo()));
            lista.add("X' = " + convert(obj.getX1()));
            lista.add("");
            lista.add("X'' = (-(" + convert(obj.getB()) + ") - √" + convert(obj.getDelta()) + ") / (2 * " + convert(obj.getA()) + ")");
            lista.add("X'' = " + convert(obj.getResultadoX2Cima()) + " / " + convert(obj.getResultadoX2Baixo()));
            lista.add("X'' = " + convert(obj.getX2()));
            lista.add("");
            lista.add("Solução = {" + convert(obj.getX1()) + ", " + convert(obj.getX2()) + "}");
        }
        return lista;
    }

    private String convert(double d) {
        return new DecimalFormat("0.00").format(d);
    }

    public void zerar(View v) {
        editA.setText("");
        editB.setText("");
        editC.setText("");

        a.setText("a");
        b.setText("b");
        c.setText("c");

        lista.clear();
        adapter.notifyDataSetChanged();

        editA.requestFocus();

        //Esconde o teclado
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editA, InputMethodManager.SHOW_IMPLICIT);
    }


    private class EventoPularEditText implements EditText.OnEditorActionListener {

        private EditText edit;

        /**
         * @param edit - EditText que vai receber o próximo foco
         */
        public EventoPularEditText(EditText edit) {
            this.edit = edit;
        }

        /**
         * Método executa quando o usuário clicar no botao ENTER no teclado
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                edit.requestFocus();
                return true;
            } else if (actionId == EditorInfo.IME_ACTION_GO) {
                calcular(botaoCalcular);
                return true;
            }
            return false;
        }
    }

    private class EventoAlteracaoEditText implements TextWatcher {

        private EditText edit;
        private TextView txt;
        private String c;

        /**
         * @param edite - EditText onde está o valor que vai alterar o TextView
         * @param txt   - TextView que vai receber o valor do EditText
         * @param c     - Caractere padrão para quando o conteúdo do EditText for vazio
         */
        public EventoAlteracaoEditText(EditText edite, TextView txt, String c) {
            this.edit = findViewById(edite.getId());
            this.txt = txt;
            this.c = c;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * Método executa quando o conteúdo do EditText for alterado
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String v = this.edit.getText().toString();
            if (!v.isEmpty()) {
                this.txt.setText(v);
            } else {
                this.txt.setText(this.c);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}