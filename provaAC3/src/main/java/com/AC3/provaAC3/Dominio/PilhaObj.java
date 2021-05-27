package com.AC3.provaAC3.Dominio;

import java.util.Arrays;

public class PilhaObj <T>{
    private int topo;		/* índice do topo da pilha */
    private T[] pilha;	/* vetor que representa a pilha */

    /* Construtor - recebe a capacidade da pilha */
    public PilhaObj(int capacidade) {
        topo = -1;				/* inicializa topo com -1 */
        pilha = (T[]) new Object[capacidade];	/* cria o vetor da pilha */
    }

    @Override
    public String toString() {
        return "PilhaObj{" +
                "topo=" + topo +
                ", pilha=" + Arrays.toString(pilha) +
                '}';
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == pilha.length - 1;

    }

    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        }
        else {		/* pilha cheia */
            System.out.println("Pilha cheia");
        }
    }

    /* Método pop - se a pilha não estiver vazia, desempilha
     * e retorna o elemento do topo da pilha.
     * Se a pilha estiver vazia, retorna -1
     */
    public T pop() {
        if (!isEmpty()) {
            return pilha[topo--];

            /* a instrução acima equivale às 3 abaixo: */
            // int retorno = pilha[topo];
            // topo--;
            // return retorno;

        }
        System.out.println("Lista vazia!");
        return null;
    }

    /* Método peek - Retorna o elemento do topo da pilha */
    public T peek() {
        if(!isEmpty()) {
            return pilha[topo];
        }
        return null;
    }

    public Boolean isPalindromo(){
        Boolean retorno = false;
        if (!isEmpty()){
            retorno = true;
            if (topo+1 % 2 == 0) {
                //caso tenha itens em qtd par
                for(int i=0; i<= topo; i++){
                    if(pilha[i] != pilha[topo-i]){
                        retorno = false;
                    }
                }
            }
            else {
                int meio = Math.round(topo/2);
                for (int i=0; i<meio; i++){
                    if(pilha[i] != pilha[topo-i]){
                        retorno = false;
                    }
                }
            }
        }
        return retorno;
    }

    /* Método exibe - Exibe os elementos da pilha */
    public void exibe() {
        if(isEmpty()) {
            System.out.println("Pilha vazia");
        }
        else {
            System.out.println("\nImprimindo lista:");
            for(int i = 0; i <= topo; i++) {
                System.out.println(pilha[i]);
            }
        }
    }

    //METODOS RESPECTIVOS AO EXERCICIO 6 - LIVRO

    // n --> qtd de itens que serão desempilhados
    // n = capacidade --> OK, retorno é a pilha toda
    // n > capacidade --> Null, tamanho invalido
    public PilhaObj <T> multiPop (int n){
        PilhaObj<T> pRetorno = new PilhaObj<>(n);
        for (int i=0; i<n; i++){
            pRetorno.push(this.pop());
        }
        return pRetorno;
    }

    public void multiPush(PilhaObj<T> pAux){
        for (int i=0; i<=pAux.topo+1; i++){
            this.push(pAux.pop());
        }
    }
}
