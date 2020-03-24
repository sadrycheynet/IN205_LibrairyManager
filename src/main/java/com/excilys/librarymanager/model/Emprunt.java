package com.excilys.librarymanager.model;

import java.time.LocalDate;

public class Emprunt{
    /*!
	 *	CHAMPS DE LA TABLE MEMBRE
	 */
    private int id;
    private Livre livre;
    private Membre membre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    /*!
	 *	CONSTRUCTEURS
	 */
    public Emprunt() {
        this.id = 0;
        this.livre = new Livre();
        this.membre = new Membre();
        this.dateEmprunt = LocalDate.now();
        this.dateRetour = dateEmprunt.plusDays(14);
    }
    
    public Emprunt(int id, Livre livre, Membre membre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.livre = livre;
        this.membre = membre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    /*!
	 *	ACCESSEURS
	 */
    public int getId() {return id;}
    public Livre getLivre() {return livre;}
    public Membre getMembre() {return membre;}
    public LocalDate getDateEmprunt() {return dateEmprunt;}
    public LocalDate getDateRetour() {return dateRetour;}

    /*!
	 *	MODIFIEURS
	 */
    public void setId(int id) {this.id = id;}
    public void setLivre(Livre livre) {this.livre = livre;}
    public void setMembre(Membre membre) {this.membre = membre;}
    public void setDateEmprunt(LocalDate dateEmprunt) {this.dateEmprunt = dateEmprunt;}
    public void setDateRetour(LocalDate dateRetour) {this.dateRetour = dateRetour;}

    public String toString() {
        String res = "Id : " + String.valueOf(id) + ", Livre : " + livre.getTitre() + ", Membre : " + membre.getNom()
                        + ", dateEmprunt : " + this.dateEmprunt.toString() + ", dateRetour : " + this.dateRetour.toString();
        return res;
    }

}