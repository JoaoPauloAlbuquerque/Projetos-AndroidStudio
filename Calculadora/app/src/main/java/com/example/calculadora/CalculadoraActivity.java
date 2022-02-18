package com.example.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculadoraActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, soma, subtracao, multiplicacao, divisao, limpar, ponto, igual, abrirParenteses, fecharParenteses;
    private ImageView backspace;

    private TextView conta, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        iniComponentes();

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        abrirParenteses.setOnClickListener(this);
        fecharParenteses.setOnClickListener(this);
        ponto.setOnClickListener(this);
        soma.setOnClickListener(this);
        subtracao.setOnClickListener(this);
        divisao.setOnClickListener(this);
        multiplicacao.setOnClickListener(this);

        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conta.setText("");
                resultado.setText("");
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = findViewById(R.id.conta);
                String s = t.getText().toString();

                if (!s.isEmpty()) {
                    byte var0 = 0;
                    int var1 = s.length() - 1;
                    String novaS = s.substring(var0, var1);
                    t.setText(novaS);
                }

                resultado.setText("");
            }
        });

        igual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Expression expressao = new ExpressionBuilder(conta.getText().toString()).build();
                    double result = expressao.evaluate();
                    long longResultado = (long) result;

                    if (result == (double) longResultado) {
                        resultado.setText((CharSequence) String.valueOf(longResultado));
                    } else {
                        resultado.setText((CharSequence) String.valueOf(result));
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Expressão inválida", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void voltar(View v) {
        finish();
    }

    private void iniComponentes() {
        b0 = findViewById(R.id.botao_calculadora_zero);
        b1 = findViewById(R.id.botao_calculadora_um);
        b2 = findViewById(R.id.botao_calculadora_dois);
        b3 = findViewById(R.id.botao_calculadora_tres);
        b4 = findViewById(R.id.botao_calculadora_quatro);
        b5 = findViewById(R.id.botao_calculadora_cinco);
        b6 = findViewById(R.id.botao_calculadora_seis);
        b7 = findViewById(R.id.botao_calculadora_sete);
        b8 = findViewById(R.id.botao_calculadora_oito);
        b9 = findViewById(R.id.botao_calculadora_nove);
        soma = findViewById(R.id.botao_calculadora_soma);
        multiplicacao = findViewById(R.id.botao_calculadora_multiplicar);
        subtracao = findViewById(R.id.botao_calculadora_subtracao);
        divisao = findViewById(R.id.botao_calculadora_dividir);
        igual = findViewById(R.id.botao_calculadora_igualdade);
        limpar = findViewById(R.id.botao_calculadora_limpar);
        ponto = findViewById(R.id.botao_calculadora_ponto);
        backspace = findViewById(R.id.botao_calculadora_backspace);
        conta = findViewById(R.id.conta);
        resultado = findViewById(R.id.resultado);
        abrirParenteses = findViewById(R.id.botao_calculadora_abrir_parenteses);
        fecharParenteses = findViewById(R.id.botao_calculadora_fechar_parenteses);
    }

    public void addConta(String s, boolean operador) {
        if (!resultado.getText().toString().isEmpty()) {
            if(operador){
                conta.setText("");
                conta.setText(resultado.getText());
                conta.append(s);
                resultado.setText("");
            } else {
                conta.setText(s);
                resultado.setText("");
            }
        } else {
            conta.append(resultado.getText());
            conta.append(s);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botao_calculadora_zero:
                addConta("0", false);
                break;
            case R.id.botao_calculadora_um:
                addConta("1", false);
                break;
            case R.id.botao_calculadora_dois:
                addConta("2", false);
                break;
            case R.id.botao_calculadora_tres:
                addConta("3", false);
                break;
            case R.id.botao_calculadora_quatro:
                addConta("4", false);
                break;
            case R.id.botao_calculadora_cinco:
                addConta("5", false);
                break;
            case R.id.botao_calculadora_seis:
                addConta("6", false);
                break;
            case R.id.botao_calculadora_sete:
                addConta("7", false);
                break;
            case R.id.botao_calculadora_oito:
                addConta("8", false);
                break;
            case R.id.botao_calculadora_nove:
                addConta("9", false);
                break;
            case R.id.botao_calculadora_abrir_parenteses:
                addConta("(", false);
                break;
            case R.id.botao_calculadora_fechar_parenteses:
                addConta(")", false);
                break;
            case R.id.botao_calculadora_ponto:
                addConta(".", false);
                break;
            case R.id.botao_calculadora_soma:
                addConta("+", true);
                break;
            case R.id.botao_calculadora_subtracao:
                addConta("-", true);
                break;
            case R.id.botao_calculadora_multiplicar:
                addConta("*", true);
                break;
            case R.id.botao_calculadora_dividir:
                addConta("/", true);
                break;
            default:
                break;

        }
    }
}