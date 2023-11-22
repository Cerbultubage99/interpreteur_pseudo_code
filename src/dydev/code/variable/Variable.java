package interpreteur.src.dydev.code.variable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import interpreteur.src.dydev.utilitaire.FileReader;

/**
 * Variable est la classe qui permet de stocker les différentes variables
 * @author Dydev : Maës Theo
 * @version 1.0
 */
public class Variable {

	/**
	 * Nom de la variable
	 * @see Variable#Variable(String, Object)
	 */
	private final String nomVar;

	/**
	 * Le tableau de valeurs de la variable
	 * @see Variable#Variable(String, int, Object)
	 */
	private Object[] tabValeur;

	/**
	 * La valeur de la variable
	 * @see Variable#Variable(String, Object)
	 */
	private Object valeur;

	/**
	 * Liste de toutes les variables
	 * @see Variable#variables
	 * @see Variable#Variable(String, Object)
	 */
	private static List<Variable> variables;

	/**
	 * Constructeur de la classe Variable
	 * <p>
	 * Défini à l'attribut nomVar le nom de la variable passée en paramètre.
	 * Etabli pour la variable "valeur" le contenu de l'objet passé en paramètre.
	 * Rajoute l'instance de la variable à la liste de variables
	 * </p>
	 * @param nomVar nom de la variable
	 * @param valeur valeur de la variable
	 */
	public Variable(String nomVar, Object valeur) {
		this.nomVar = nomVar;
		this.valeur = valeur;
		variables.add(this);
	}

	/**
	 * Constructeur de la classe Variable
	 * <p>
	 * Défini à l'attribut nomVar le nom de la variable passée en paramètre.
	 * Creer un tableau de la taille se trouvant en paramètre et le rempli avec la valeur par défaut
	 * Rajoute l'instance de la variable à la liste de variables
	 * </p>
	 * @param nomVar nom de la variable
	 * @param taille taille du tableau
	 * @param valeur valeur par défaut du tableau
	 */
	public Variable(String nomVar, int taille, Object valeur) {
		this.nomVar = nomVar;
		this.tabValeur =
				switch(valeur.getClass().getSimpleName()){
					case "Double"    -> new Double    [taille];
					case "Integer"   -> new Integer   [taille];
					case "Boolean"   -> new Boolean   [taille];
					case "Character" -> new Character [taille];
					default          -> new String    [taille];
				};
		Arrays.fill(this.tabValeur, valeur);
		variables.add(this);
	}

	/**
	 * Permet de modifier la valeur d'une variable
	 * @param nomVariable le nom de la variable à modifier
	 * @param contenu la valeur à mettre
	 * @return true si la valeur à été modifiée
	 */
	public static boolean modifierValeur(String nomVariable, String contenu) {
		if(!Pattern.matches(".+\\[\\d\\]", nomVariable.replaceAll("[\t ]", ""))) {
			Variable var = Variable.getVariable(nomVariable.replaceAll("[\t ]", ""));
			if(contenu.equals("vrai") || contenu.equals("faux")){
				var.setValeur(Boolean.parseBoolean(contenu));
			} else {
				if(Pattern.matches("\\d+", contenu.replaceAll("[\t ]", ""))){
					var.setValeur(Integer.parseInt(contenu.replaceAll("[\t ]", "")));
				} else {
					if(Pattern.matches("\\d+[\\.]\\d+", contenu.replaceAll("[\t ]", ""))){
						var.setValeur(Double.parseDouble(contenu.replaceAll("[\t ]", "")));
					} else {
						if(Pattern.matches("('.')", contenu.replaceAll("[\t ]", ""))){
							var.setValeur(contenu.replaceAll("[\t ]","").charAt(1));
						} else {
							contenu = contenu.replaceAll("\"", "");
							var.setValeur(contenu);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Permet de modifier la valeur d'une variable à l'indice du tableau
	 * @param nomVariable : le nom de la variable a modifier
	 * @param contenu : la valeur à mettre
	 */
	public static void modifierValeurTabIndex(String nomVariable, String contenu) {
		String[] tab = nomVariable.replaceAll("[\t ]", "").split("\\[");
		Variable var = Variable.getVariable(tab[0].replaceAll("[\t ]", ""));
		if(contenu.equals("vrai") || contenu.equals("faux")){
			Boolean[] valeurs = (Boolean[]) var.getTabValeur();
			valeurs[Integer.parseInt(tab[1].replaceAll("[\t ]", "").substring(0, tab.length-1))] =
					Boolean.parseBoolean(contenu);
		} else {
			if(Pattern.matches("\\d+", contenu.replaceAll("[\t ]", ""))){
				Integer[] valeurs = (Integer[]) var.getTabValeur();
				valeurs[Integer.parseInt(tab[1].replaceAll("[\t ]", "").substring(0, tab.length-1))] =
						Integer.parseInt(contenu.replaceAll("[\t ]", ""));
			} else {
				if(Pattern.matches("\\d+[\\.]\\d+", contenu.replaceAll("[\t ]", ""))){
					Double[] valeurs = (Double[]) var.getTabValeur();
					valeurs[Integer.parseInt(tab[1].replaceAll("[\t ]", "").substring(0, tab.length-1))] =
							Double.parseDouble(contenu.replaceAll("[\t ]", ""));

				} else {
					if(Pattern.matches("('.')", contenu.replaceAll("[\t ]", ""))){
						Character[] valeurs = (Character[]) var.getTabValeur();
						valeurs[Integer.parseInt(tab[1].replaceAll("[\t ]", "").substring(0, tab.length-1))] =
								contenu.replaceAll("[\t ]","").charAt(1);

						var.setValeur(valeurs);
					} else {
						contenu = contenu.replaceAll("\"","");
						String[] valeurs = (String[]) var.getTabValeur();
						valeurs[Integer.parseInt(tab[1].replaceAll("[\t ]", "").substring(0, tab.length-1))] = contenu;

						var.setValeur(valeurs);
					}
				}
			}
		}
	}

	/**
	 *
	 * @return nom de la variable
	 */
	public String getNomVar() {
		return nomVar;
	}

	/**
	 * Redéfini la valeur de la variable
	 * @param valeur : de la variable
	 */
	public void setValeur(Object valeur) {
		if(valeur.getClass().getSimpleName().contains("[]")){
			this.tabValeur =
					switch(valeur.getClass().getSimpleName().split("\\[")[0]){
						case "Double"    -> (Double   [] ) valeur;
						case "Integer"   -> (Integer  [] ) valeur;
						case "Boolean"   -> (Boolean  [] ) valeur;
						case "Character" -> (Character[] ) valeur;
						default          -> (String   [] ) valeur;
					};
		}else{
			this.valeur = valeur;
		}
	}

	/**
	 * @return valeur de la variable
	 */
	public Object getValeur() {
		return valeur;
	}

	/**
	 * @return valeur de la variable
	 */
	public Object[] getTabValeur() {
		return tabValeur;
	}

	/**
	 * @return la liste de variables
	 */
	public static List<Variable> getVariables() {
		return variables;
	}

	/**
	 * Récupère la variable qui correspond au nom passé en paramètre
	 * @param nomVar : nom de la variable
	 * @return la variable trouvée ou null si non trouvée
	 */
	public static Variable getVariable(String nomVar){
		for (Variable variable : variables) {
			if(variable.nomVar.equals(nomVar)){
				return variable;
			}
		}
		return null;
	}

	/**
	 * @param nomVariable : le nom de la variable
	 * @return si la variable existe
	 */
	public static boolean isVariable(String nomVariable){
		return getVariable(nomVariable) != null;
	}

	@Override
	public String toString() {
		StringBuilder message = new StringBuilder("[nom Variable = " + nomVar + " | valeur = ");

		if(tabValeur == null){
			message.append(valeur);
		} else {
			message.append(tabValeur[0]);
			for (int i = 1; i < tabValeur.length; i++) {
				message.append(" | ").append(tabValeur[i]);
			}
		}
		message.append(" ] ");
		return message.toString();
	}

	/**
	 * Lit le fichier passée en paramètre et appelle la création de constante puis créer les variables
	 * @param file : fichier a lire
	 * @see Variable#creationConstante(FileReader)
	 */
	public static void creationVariable(File file) {
		variables = new ArrayList<>();
		FileReader reader = new FileReader(file);

		creationConstante(reader);

		reader.getLigneContenant("variable:");

		while(!reader.getLigneActuelle().contains("DEBUT")){
			reader.ligneSuivante();

			if(reader.getLigneActuelle().contains(":")){

				String[] ligne = reader.getLigneActuelle().split(":");
				String[] nomVars = ligne[0].replaceAll("[\t ]", "").split(",");

				if(!ligne[1].replaceAll("[\t ]", "").contains("tableau")) {
					for (String nomVar : nomVars) {
						switch(ligne[1].replaceFirst("[\t ]", "")){
							case "entier"               -> new Variable(nomVar, 0       );
							case "réel"                 -> new Variable(nomVar, 0.0d    );
							case "chaine de caractères" -> new Variable(nomVar, ""      );
							case "caractères"           -> new Variable(nomVar, ' '     );
							case "booléen"              -> new Variable(nomVar, false   );
						}
					}
				} else {
					String[] information = ligne[1].split("[\\]\\[]");
					String[] type        = information[2].replaceFirst("[\t ]", "").replaceFirst("[\\' ]", "-").split("-");
					for (String nomVar : nomVars) {
						switch(type[1]){
							case "entier"               -> new Variable(nomVar, Integer.parseInt(information[1]), 0       );
							case "réel"                 -> new Variable(nomVar, Integer.parseInt(information[1]), 0.0d    );
							case "chaine de caractères" -> new Variable(nomVar, Integer.parseInt(information[1]), ""      );
							case "caractères"           -> new Variable(nomVar, Integer.parseInt(information[1]), ' '     );
							case "booléen"              -> new Variable(nomVar, Integer.parseInt(information[1]), false   );
						}
					}
				}
			}
		}
	}

	/**
	 * Lit le fichier à l'aide du FileReader passé en paramètre et créer les constantes
	 * @param reader le FileReader du fichier à parcourir
	 */
	private static void creationConstante(FileReader reader) {

		reader.getLigneContenant("constante:");
		while(!reader.getLigneActuelle().contains("DEBUT") && !reader.getLigneActuelle().contains("variable:") ){
			if(reader.getLigneActuelle() != null && reader.getLigneActuelle().contains("<--")){
				String[] ligne = reader.getLigneActuelle().split("<--");
				String[] nomVars = ligne[0].replaceAll("[\t ]", "").split(",");

				for (String nomVar : nomVars) {
					new Variable(nomVar, ligne[1].replaceAll("[\t ]", ""));
				}
			}
			reader.ligneSuivante();
		}
	}
}
