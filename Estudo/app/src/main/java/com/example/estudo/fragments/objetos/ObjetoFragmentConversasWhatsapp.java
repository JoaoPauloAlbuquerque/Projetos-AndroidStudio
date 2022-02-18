package com.example.estudo.fragments.objetos;

import com.example.estudo.R;

import java.util.Arrays;
import java.util.List;

public class ObjetoFragmentConversasWhatsapp {

    private int icon;
    private String nome;
    private String mensagem;
    private String data;
    private boolean lido;

    public ObjetoFragmentConversasWhatsapp(){}

    public int getIcon() {
        return icon;
    }

    public ObjetoFragmentConversasWhatsapp setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ObjetoFragmentConversasWhatsapp setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getMensagem() {
        return mensagem;
    }

    public ObjetoFragmentConversasWhatsapp setMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public String getData() {
        return data;
    }

    public ObjetoFragmentConversasWhatsapp setData(String data) {
        this.data = data;
        return this;
    }

    public boolean isLido() {
        return lido;
    }

    public ObjetoFragmentConversasWhatsapp setLido(boolean lido) {
        this.lido = lido;
        return this;
    }

    public static class Construir{
        public static List<ObjetoFragmentConversasWhatsapp> contruir(){
            return Arrays.asList(
                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Melissa")
                            .setMensagem("Boa noite")
                            .setData("8:55 da noite")
                            .setLido(false),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Mateus")
                            .setMensagem("Yae mano")
                            .setData("8:31 da noite")
                            .setLido(false),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Gabriel")
                            .setMensagem("Ei pow, tu vai descer pra cá hoje?")
                            .setData("8:12 da noite")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Denylson")
                            .setMensagem("Bixo, ficou sabendo?")
                            .setData("7:59 da noite")
                            .setLido(false),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Guilherme")
                            .setMensagem("Boa noite mano")
                            .setData("7:40 da noite")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Paulo")
                            .setMensagem("Yae")
                            .setData("7:14 da noite")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Geremias")
                            .setMensagem("Tu fez aquele negócio?")
                            .setData("7:11 da noite")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Gustavo")
                            .setMensagem("Sim!")
                            .setData("4:01 da tarde")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Osvaldo")
                            .setMensagem("Vamos?")
                            .setData("3:54 da tarde")
                            .setLido(false),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Pedro")
                            .setMensagem("Não pow")
                            .setData("11:22 da da manhã")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Jorge")
                            .setMensagem("Tais melhor parceiro?")
                            .setData("11:21 da manhã")
                            .setLido(true),

                    new ObjetoFragmentConversasWhatsapp()
                            .setIcon(R.drawable.logo)
                            .setNome("Henrique")
                            .setMensagem("sabia não vey")
                            .setData("10:30 da manhã")
                            .setLido(true)
            );
        }
    }
}
