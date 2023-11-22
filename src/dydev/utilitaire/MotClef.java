package interpreteur.src.dydev.utilitaire;

import interpreteur.src.dydev.code.affichage.Couleurs;
import org.jdom2.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe MotCle permet de stocker un mot clef ainsi que sa couleur et si il est en gras ou non
 * @author Dydev : Maës Theo
 * @version 1.0
 */
public class MotClef {

	private final static String PATH = "ressources/coloration.xml";

	/**
	 * L'attribut motCle stock un mot
	 */
	private final String  motCle;

	/**
	 * L'attribut couleur stock le nom de la couleur du mot clé
	 */
	private final String  couleur;

	/**
	 * L'attribut gras est a true si le mot clé doit etre en gras
	 */
	private final boolean gras;

	/**
	 * L'attribut stock toutes les instances des mots clés créées
	 */
	private static List<MotClef> listMotCle;

	/**
	 * Constructeur privé.
	 * Ajoute l'instance du mot clé dans la list de l'attribut listMotCle
	 * @param motCle : mot à colorer
	 * @param couleur : couleur du mot
	 * @param gras : si il est en gras ou non
	 */
	private MotClef(String motCle, String couleur, boolean gras) {
		this.motCle = motCle;
		this.couleur = couleur;
		this.gras    = gras;

		listMotCle.add(this);
	}

	/**
	 * @return return le mot clé
	 */
	public String getMotCle() {
		return motCle;
	}

	/**
	 *
	 * @return le code d'échappement Ansi de la couleur
	 */
	public String getCodeCouleur() {
		return Couleurs.valueOf(couleur.toUpperCase()).getCouleurForeground() + (isGras()? Couleurs.setGras():"");
	}

	/**
	 *
	 * @return vrai si le mot est en gras
	 */
	public boolean isGras() {
		return gras;
	}

	/**
	 * @param mot le mot à chercher
	 * @return l'instance de motCle correspondante
	 */
	public static MotClef getMotCleByName(String mot){
		for (MotClef motCle: listMotCle) {
			if (motCle.getMotCle().equalsIgnoreCase(mot)){
				return motCle;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "MotClef{" +
				"motCle='" + motCle + '\'' +
				", couleur='" + couleur + '\'' +
				", gras=" + gras +
				'}';
	}

	/**
	 *
	 * @return le code d'échappement Ansi pour mettre la couleur de l'interpreteur par défaut
	 */
	public static String getDefault() {
		XmlReader reader = new XmlReader(new File( PATH ));
		return Couleurs.normal() + (reader.isDefaultGras()? Couleurs.setGras(): "") + reader.getDefaultBackground() + reader.getDefaultForeground();
	}

	/**
	 *
	 * @return le code d'échappement Ansi pour mettre la couleur de la ligne surlignée de l'interpreteur par défaut
	 */
	public static String getDefautSurligner() {
		XmlReader reader = new XmlReader(new File( PATH ));
		return Couleurs.normal() + (reader.isSurlignerGras()? Couleurs.setGras(): "") + reader.getSurlignerBackground() + reader.getSurlignerForeground();
	}

	/**
	 * Permet de générer les instances de MotCle correspondant au xml
	 */
	public static void generer() {
		listMotCle = new ArrayList<>();

		XmlReader reader = new XmlReader(new File( PATH ));

		for (Element element : reader.getListChildren("variante") ) {
			for (Element subElement : element.getChildren("mot-clef")) {
				boolean gras = (element.getAttributeValue("gras").equalsIgnoreCase("true") ||
						element.getAttributeValue("gras").equalsIgnoreCase("vrai")    );

				new MotClef(subElement.getText(), element.getAttributeValue("foreground"), gras);
			}
		}
	}
}
