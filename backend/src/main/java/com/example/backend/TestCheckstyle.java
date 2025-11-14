package com.example.backend;

import java.util.*; // 👈 1. Import con asterisco — ERRORE

public class TestCheckstyle { // 👈 2. Nome ok
    private int MyVar=10;   // 👈 3. Variabile con maiuscola — ERRORE (naming)
    private static final int MAXvalue = 5; // 👈 4. Costante non tutta maiuscola — ERRORE

    public void test() {
        int x=1;int y=2; // 👈 5. Mancano spazi — ERRORE
        if(x>y) System.out.println("Ok"); // 👈 6. Manca graffa — ERRORE (NeedBraces)
    }

    public void longMethod() { // 👈 7. Metodo troppo lungo (se superi il limite configurato)
        for (int i = 0; i < 120; i++) {
            System.out.println(i);
        }
    }
}
