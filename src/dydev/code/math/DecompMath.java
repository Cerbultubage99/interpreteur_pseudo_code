package interpreteur.src.dydev.code.math;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <b>DecompMath décompose les opérations mathématiques par nombre et expression arithmétique,
 * puis appelle les méthodes pour calculer grâce à la classe "Calcul".</b>
 * @author DyDev : Jason LEBLOND - Théo MAËS
 * @version 1.2
 */
public class DecompMath {
	/**
	 * Tableaux récupérant les nombres et opérateurs de la ligne donnée après décomposition
	 */
	private static String[] tabOperation;

	/**
	 * Variable de stockage de la ligne à décomposer
	 */
	private static String ligneCalcul;

	/**
	 * Liste des nombres obtenus lors des calculs effectués
	 */
	private static List<Double> alNombres;

	/**
	 * <p>Décomposition sépare les nombres des symboles/opérateurs.
	 * Repere la presence de nombre negatif
	 * Stock chacun des éléments dans le tableau tabOperation.
	 */
	private static void decomposition() {
		ligneCalcul = ligneCalcul.replaceAll(" ", "");

		for (int i = 0; i<ligneCalcul.length(); i++) {
			if ( ligneCalcul.charAt(i) == '.') {
				i++;
			} else {
				if(Pattern.matches("[)(+-/%^|\u00D7]", String.valueOf (ligneCalcul.charAt(i)))) {
					if ( Pattern.matches("-", String.valueOf (ligneCalcul.charAt(i))) && ligneCalcul.charAt(i == 0?i:(i == 1?i-1:i-2)) == '(' ||
							ligneCalcul.startsWith("-") && i == 0 ) {
						i++;
					} else {
						ligneCalcul = ligneCalcul.substring(0, i) + "#" + ligneCalcul.charAt(i) + "#" + ligneCalcul.substring(i+1, ligneCalcul.length());
						i+=2;
					}
				} else {
					if ( ligneCalcul.contains("DIV") && ligneCalcul.charAt(i) == 'D' || ligneCalcul.contains("MOD") && ligneCalcul.charAt(i) == 'M' || ligneCalcul.contains("\\/\u0304") && ligneCalcul.charAt(i) == '\\') {
						ligneCalcul = ligneCalcul.substring(0, i) + "#" + ligneCalcul.substring(i, i+3) + "#" + ligneCalcul.substring(i+3);
						i+=3;
					}
				}
			}
		}

		tabOperation = ligneCalcul.split("#");

		for (int i = 0; i < tabOperation.length; i++) {
			if ( tabOperation[i].isEmpty() ) {
				for (int j = i; j < tabOperation.length -1 ; j++) {
					tabOperation[j] = tabOperation[j+1];
				}
			}
			else {
				if ( tabOperation[i].equals(".") ) {
					tabOperation[i-1] += tabOperation[i] + "" + tabOperation[i+1];
					i += 2;
				}
			}
		}
	}

	/**
	 * <p>calcul verifie la presence de paranthèses ou operateur pour appeler la methode "operation" avec les nombres et l'operateur correspondant.
	 * @param indice nombre indiquant la case du tableau tabOperation ou commencer le calcul
	 *
	 * @see DecompMath#operation(String)
	 */
	private static void calcul(int indice) {

		for (int j = indice; j < tabOperation.length-1; j++) {
			if (tabOperation[j].equals("(") || tabOperation[j].equals(")")) {
				for (int k = ++j; !tabOperation[k].equals(")"); k++,j++) {
					String sCpt = tabOperation[j];

					if (tabOperation[k].matches("[+-/%^|×x]") || tabOperation[j].equals("DIV") ||
							tabOperation[j].equals("MOD")  || tabOperation[j].equals("\\/\u0304")) {

						if ( !tabOperation[k+1].equals("(") ) {
							alNombres.add(Double.parseDouble(tabOperation[k+1]));
							operation(sCpt);
							k++;
						} else {
							calcul(k+1);
							break;
						}
					} else {
						if(!tabOperation[k].equals("(") && !tabOperation[k].equals(")")){
							alNombres.add(Double.parseDouble(tabOperation[k]));
						}
					}
				}
			} else {
				String sCpt = tabOperation[j];
				if (tabOperation[j].matches("[+-/%*^|×]") || tabOperation[j].equals("DIV") || tabOperation[j].equals("MOD")) {
					if (tabOperation[(tabOperation.length-1 == j?j:j+1)].equals("(")) {
						calcul(j+1);
						j++;
						operation(sCpt);
					} else {
						alNombres.add(Double.parseDouble(tabOperation[j+1]));
						operation(sCpt);
						j++;
					}
				}
				else {
					if (tabOperation[j].equals("\\/\u0304")) {
						alNombres.add(Double.parseDouble(tabOperation[j+1]));
						operation(sCpt);
						j++;
					}
					else {
						alNombres.add(Double.parseDouble(tabOperation[j]));
					}
				}
			}
		}
	}

	/**
	 * <p>Operation appelle les méthodes de la classe Calcul selon le paramètre c.
	 * Supprime de la liste alNombres les nombres utilisés lors des calculs.<p>
	 *
	 * @param c défini l'opérateur du calcul
	 *
	 * @see Calcul
	 */
	private static void operation (String c) {
		switch (c) {
			case "+"         -> alNombres.add(Calcul.addition      (alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "-"         -> alNombres.add(Calcul.soustraction  (alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "/"         -> alNombres.add(Calcul.division      (alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "^"         -> alNombres.add(Calcul.carre         (alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "\u00D7"    -> alNombres.add(Calcul.multiplication(alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "DIV"       -> alNombres.add(Calcul.div           (alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "MOD"       -> alNombres.add(Calcul.mod           (alNombres.remove(alNombres.size()-2), alNombres.remove(alNombres.size()-1)));
			case "\\/\u0304" -> alNombres.add(Calcul.racineCarre   (alNombres.remove(alNombres.size()-1)                                      ));
		}

	}

	/**
	 * <p>calculer initialise une ArrayList de double. Stock ligneCalcul dans une variable de la classe.
	 * Appelle les méthodes de la classe pour calculer.
	 * Renvoi le resultat se trouvant a la premiere position de alNombres</p>
	 *
	 * @param ligneCalcul ligne de calcul envoye d'un fichier pour le calculer
	 * @return double
	 *
	 * @see DecompMath#decomposition()
	 * @see DecompMath#calcul(int)
	 */
	public static double calculer(String ligneCalcul) {
		alNombres = new ArrayList<>();
		DecompMath.ligneCalcul = ligneCalcul;
		decomposition();

		calcul(0);

		return alNombres.get(0);
	}
}