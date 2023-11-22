package interpreteur.src.dydev.code.affichage;

/**
 * Enumeration de couleur pour la coloration de la console
 * @author Dydev : Maës Theo
 * @version 1.0
 */
public enum Couleurs {

	/**
	 * Couleur noir
	 */
	NOIR  ("\u001b[30m", "\u001b[40m"),

	/**
	 * Couleur rouge
	 */
	ROUGE ("\u001b[31m", "\u001b[41m"),

	/**
	 * Couleur Vert
	 */
	VERT  ("\u001b[32m", "\u001b[42m"),

	/**
	 * Couleur jaune
	 */
	JAUNE ("\u001b[33m", "\u001b[43m"),

	/**
	 * Couleur bleu
	 */
	BLEU  ("\u001b[34m", "\u001b[44m"),

	/**
	 * Couleur mauve
	 */
	MAUVE ("\u001b[35m", "\u001b[45m"),

	/**
	 * Couleur cyan
	 */
	CYAN  ("\u001b[36m", "\u001b[46m"),

	/**
	 * Couleur blanc
	 */
	BLANC ("\u001b[37m", "\u001b[47m");

	/**
	 * Attribut d'une couleur.
	 * Stock le code échappement Ansi de coloration du texte
	 */
	final String couleurForeground;

	/**
	 * Attribut d'une couleur.
	 * Stock le code échappement Ansi de coloration du background
	 */
	final String couleurBackground;

	/**
	 * Constructeur des couleurs.
	 * @param couleurForeground	: Code échappement Ansi de la coloration du texte
	 * @param couleurBackground : Code échappement Ansi de la coloration du background
	 */
	Couleurs(String couleurForeground, String couleurBackground) {
		this.couleurForeground = couleurForeground;
		this.couleurBackground = couleurBackground;
	}

	/**
	 * @return Le code d'échappement Ansi du foreground de la couleur
	 */
	public String getCouleurForeground() {
		return this.couleurForeground;
	}

	/**
	 * @return Le code d'échappement Ansi du background de la couleur
	 */
	public String getCouleurBackground() {
		return this.couleurBackground;
	}

	/**
	 * Met le texte en gras
	 * @return Le code d'échappement Ansi du gras
	 */
	public static String setGras(){
		return "\u001b[1m";
	}

	/**
	 * Remettre la console à son état d'origine
	 * @return Le code échappement Ansi pour remettre la console d'origine
	 */
	public static String normal(){
		return "\u001b[0m";
	}
}
