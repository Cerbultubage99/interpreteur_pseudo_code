package interpreteur.src.dydev.code.math;
/**
 * <b>Calcul effectue chaque calcul possible selon la méthode appelée.</b>
 * @author DyDev : Théo MAËS - Jason LEBLOND
 * @version 1.5
 */
public class Calcul {

	/**
	 * Additionne les deux valeurs entrées en paramètre
	 * @param valeur1 premiere valeur a additioner
	 * @param valeur2 deuxieme valeur a additioner
	 * @return double
	 */
	public static double addition(double valeur1, double valeur2) {
		return (valeur1 + valeur2);
	}

	/**
	 * Soustrait la valeur 2 à la valeur 1 entrées en paramètre
	 * @param valeur1 premiere valeur qui se fera soustraire
	 * @param valeur2 deuxieme valeur a soustraire
	 * @return double
	 */
	public static double soustraction(double valeur1, double valeur2) {
		return (valeur1 - valeur2);
	}

	/**
	 * Multiplie les deux valeurs entrées en paramètre
	 * @param valeur1 premiere valeur a multiplier
	 * @param valeur2 deuxieme valeur a multiplier
	 * @return double
	 */
	public static double multiplication(double valeur1, double valeur2) {
		return (valeur1 * valeur2);
	}

	/**
	 * Divise la valeur 1 par la valeur 2 entrées en paramètre
	 * @param valeur1 premiere valeur : dividende
	 * @param valeur2 deuxième valeur : diviseur
	 * @return double
	 */
	public static double division(double valeur1, double valeur2) {
		return (valeur1 / valeur2);
	}

	/**
	 * Divise la valeur 1 par la valeur 2 entrées en paramètre mais ne retourne que la partie entière du résultat
	 * @param valeur1 premiere valeur : dividende
	 * @param valeur2 deuxième valeur : diviseur
	 * @return double
	 */
	public static double div(double valeur1, double valeur2) {
		return (int)(valeur1 / valeur2);
	}
	/**
	 * Divise la valeur 1 par la valeur 2 entrées en paramètre mais ne retourne que la partie décimale du résultat
	 * @param valeur1 premiere valeur : dividende
	 * @param valeur2 deuxième valeur : diviseur
	 * @return double
	 */
	public static double mod(double valeur1, double valeur2) {
		return (int)(valeur1 % valeur2);
	}

	/**
	 * Met au carré la valeur 1 par la puissance entrées en paramètre
	 * @param valeur1 valeur qui sera mise a la puissance entree
	 * @param puissance valeur correspondant a la puissance pour la valeur 1
	 * @return double
	 */
	public static double carre(double valeur1, double puissance) {
		return Math.pow( valeur1, puissance);
	}

	/**
	 * Fais la racine carré du nombre entrée en paramètre
	 * @param valeur1 valeur dont on cherche la racine carre
	 * @return double
	 */
	public static double racineCarre(double valeur1) {
		return Math.sqrt( valeur1 );
	}
}