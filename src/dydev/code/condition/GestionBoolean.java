package interpreteur.src.dydev.code.condition;

import interpreteur.src.dydev.code.variable.Variable;

/**
 * <b>Cette classe permet d'obtenir le résultat d'une expression booléenne</b>
 *
 * @author DyDev : Bosquain Maxence
 * @version 1.2
 */
public class GestionBoolean {
	private final String CONDITION;

	/**
	 * @param ligneCondition expression booléenne à evaluer
	 */
	public GestionBoolean(String ligneCondition) {
		this.CONDITION = ligneCondition;
	}

	/**
	 * @return retourne le résultat d'une expression booléenne aussi grande soit elle
	 */
	public boolean executerCondition() {
		String[] tmp = this.tabChartoTabString(this.CONDITION.toCharArray());

		String[] tabDonnees = new String[tmp.length];
		String[] tabOperateur = new String[tmp.length];

		tabDonnees[0] = tmp[0];
		String precedent = tabDonnees[0];
		tmp = supprElt(0, tmp);

		for (String value : tmp) {
			if (value.matches("[(<=)(>=)(/=)><=]"))
				if (precedent.matches("[(<=)(>=)(/=)><=]"))
					tabOperateur[possitionDernierElement(tabOperateur) - 1] = tabOperateur[possitionDernierElement(tabOperateur) - 1] + value;
				else
					tabOperateur[possitionDernierElement(tabOperateur)] = value;
			else if (!precedent.matches("[(<=)(>=)(/=)><=]"))
				tabDonnees[possitionDernierElement(tabDonnees) - 1] = tabDonnees[possitionDernierElement(tabDonnees) - 1] + value;
			else
				tabDonnees[possitionDernierElement(tabOperateur)] = value;

			precedent = value;
		}

		boolean[] tabRes = new boolean[tmp.length];

		for (int cpt = 0; cpt < nbElt(tabOperateur); cpt++) {
			tabRes[cpt] = ext(tabDonnees[cpt], tabOperateur[cpt], tabDonnees[cpt + 1]);
		}

		for (int cpt = 1; cpt < nbElt(tabOperateur); cpt++) {
			tabRes[0] = Boolean.logicalAnd(tabRes[0], tabRes[cpt]);
		}

		return tabRes[0];
	}

	/**
	 * Retourne le résultat d'une expression booléenne contenant deux données et un opérateur
	 *
	 * @param donnees1  : opérande gauche
	 * @param operateur
	 * @param donnees2  : opérande droite
	 * @return booléen, résultat de l'expression
	 */
	private boolean ext(String donnees1, String operateur, String donnees2) {
		if (!donnees1.matches("\".*\"") && !donnees1.matches("\\d+")) {
			donnees1 = Variable.getVariable(donnees1).getValeur() + "";
		}
		if (!donnees2.matches("\".*\"") && !donnees2.matches("\\d+") && !donnees2.matches("\\d+[\\.]\\d+")) {
			donnees2 = (String) (Variable.getVariable(donnees2).getValeur());
		}
		if (donnees2.matches("\\d+") && donnees1.matches("\\d+[\\.]\\d+")) {
			String tmp = donnees1;
			donnees1 = donnees2;
			donnees2 = tmp;
		}

		boolean res = false;
		switch (operateur) {
			case "=" -> {
				// DoubleDur - DoubleDur
				if (donnees1.matches("\\d+[\\.]\\d+") && donnees2.matches("\\d+[\\.]\\d+"))
					return Double.parseDouble(donnees1) == Double.parseDouble(donnees2);

				// intDur - intDur
				if (donnees1.matches("\\d+") && donnees2.matches("\\d+"))
					return Integer.parseInt(donnees1) == Integer.parseInt(donnees2);

				// intDur - DoubleDur
				if (donnees1.matches("\\d+") && donnees2.matches("\\d+"))
					return Integer.parseInt(donnees1) == Double.parseDouble(donnees2);


				// DoubleVar - DoubleDur
				if (donnees1.matches("\\d+[\\.]\\d+") && donnees2.matches("\\d+[\\.]\\d+")) {
					res = Double.parseDouble(donnees1) == Double.parseDouble(donnees2);
				}

				// IntegerVal - doubleDur
				if (donnees1.matches(".*") && donnees2.matches("\\d+[\\.]\\d+")) {
					return Integer.parseInt(donnees1) == Double.parseDouble(donnees2);
				}

				// StringDur - StringDur
				if (donnees1.matches(".*") && donnees2.matches(".*")) {
					donnees2 = "\"" + donnees2 + "\"";
					return donnees1.equals(donnees2);
				}
			}

			case "<" -> {
				return Integer.parseInt(donnees1) < Integer.parseInt(donnees2);
			}
			case ">" -> {
				return Integer.parseInt(donnees1) > Integer.parseInt(donnees2);
			}
			case "/=" -> {
				if (donnees1.matches("\\d+[\\.]\\d+") && donnees2.matches("\\d+[\\.]\\d+"))
					return Double.parseDouble(donnees1) != Double.parseDouble(donnees2);

				if (donnees1.matches("\\d+") && donnees2.matches("\\d+"))
					return Integer.parseInt(donnees1) != Integer.parseInt(donnees2);

				if (donnees1.matches("\\d+") && donnees2.matches("\\d+"))
					return Integer.parseInt(donnees1) != Double.parseDouble(donnees2);


				if (donnees1.matches("\\d+[\\.]\\d+") && donnees2.matches("\\d+[\\.]\\d+")) {
					res = Double.parseDouble(donnees1) != Double.parseDouble(donnees2);
				}

				if (donnees1.matches(".*") && donnees2.matches("\\d+[\\.]\\d+")) {
					return Integer.parseInt(donnees1) != Double.parseDouble(donnees2);
				}

				if (donnees1.matches(".*") && donnees2.matches(".*")) {
					donnees2 = "\"" + donnees2 + "\"";
					return !(donnees1.equals(donnees2));
				}
			}
			default -> res = true;
		}
		return res;
	}


	/**
	 * Retourne le résultat d'une expression boolean contenant deux données et un opérateur
	 *
	 * @param tabChar le tableau qui sera transformé
	 * @return String[] Nouveau tableau crée
	 */
	private String[] tabChartoTabString(char[] tabChar) {
		String[] tabString = new String[tabChar.length];

		for (int cpt = 0; cpt < tabChar.length; cpt++) {
			tabString[cpt] = String.valueOf(tabChar[cpt]);
		}

		return tabString;
	}

	private String[] supprElt(int id, String[] tabString) {
		String[] tabStringRes = new String[tabString.length - 1];

		int cpt = 0;
		for (int cptTotal = 0; cptTotal < tabString.length; cptTotal++) {
			if (cptTotal != id) {
				tabStringRes[cpt++] = tabString[cptTotal];
			}
		}
		return tabStringRes;
	}

	/**
	 * Retourne le nombre d'élément d'un tableau
	 *
	 * @param tabObject le tableau qui sera compté
	 * @return int le nombre d'élement du tableau
	 */
	private int nbElt(Object[] tabObject) {
		int cpt = 0;

		for (Object o : tabObject) {
			if (tabObject[cpt] != null) {
				cpt++;
			}
		}
		return cpt;
	}


	/**
	 * Retourne le nombre d'élément d'un tableau
	 *
	 * @param tabString le tableau qui sera compté
	 * @return int le nombre d'élément du tableau
	 */
	private int possitionDernierElement(String[] tabString) {
		if (tabString.length == 0 || tabString[0] == null) {
			return 0;
		}

		int cpt = 0;
		for (String s : tabString) {
			if (s != null) {
				cpt++;
			}
		}
		return cpt;
	}
}