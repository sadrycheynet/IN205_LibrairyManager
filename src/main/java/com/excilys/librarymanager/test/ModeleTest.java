package com.excilys.librarymanager.test;

import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
//import com.excilys.librarymanager.model.Emprunt;

public class ModeleTest {

    public static void main() {
        testLivre();
		testMembre();
		//testEmprunt();
    }
	
	public static void testLivre(){
		Livre livre = new Livre();
		System.out.println(livre);
	}
	
	public static void testMembre(){
		Membre membre = new Membre();
		System.out.println(membre);
	}
	
	/*public static void testEmprunt(){
		Emprunt emprunt = new Emprunt();
		System.out.println(emprunt);
	}*/
}