package interpreteur.src.dydev.utilitaire;

import interpreteur.src.dydev.code.affichage.Couleurs;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Classe permettant de lire les fichiers XML
 * @author Dydev : Maës Theo
 * @version 1.0
 */
public class XmlReader {

	/**
	 * Racine des éléments du fichier xml
	 */
	private final Element racine;

	/**
	 * Constructeur de la classe. Il permet d'instancier la racine
	 * @param file : fichier xml
	 */
	public XmlReader(File file) {
		Document document = null;
		SAXBuilder sxb = new SAXBuilder();


		try {
			document = sxb.build(file);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

		assert document != null;
		racine = document.getRootElement();
	}

	/**
	 * Retourne la liste d'élément enfant de la racine en fonction du nom de l'enfant passé en paramètre
	 * @param ChildrenName nom de l'enfant
	 * @return list d'element
	 */
	public List<Element> getListChildren(String ChildrenName) {
		return racine.getChildren(ChildrenName);
	}

	/**
	 * @return retourne le code de la couleur par défaut des textes
	 */
	public String getDefaultForeground(){
		return Couleurs.valueOf(racine.getChild("defaut").getAttributeValue("foreground").toUpperCase()).getCouleurForeground();
	}

	/**
	 * @return retourne le code de la couleur par défaut du background
	 */
	public String getDefaultBackground(){
		return Couleurs.valueOf(racine.getChild("defaut").getAttributeValue("background").toUpperCase()).getCouleurBackground();
	}

	/**
	 * @return vrai si le texte par défaut doit être en gras
	 */
	public boolean isDefaultGras(){
		return (racine.getChild("defaut").getAttributeValue("gras").equalsIgnoreCase("vrai") ||
				racine.getChild("defaut").getAttributeValue("gras").equalsIgnoreCase("true")    );
	}

	/**
	 *
	 * @return retourne le code de la couleur par défaut des textes surlignés
	 */
	public String getSurlignerForeground(){
		return Couleurs.valueOf(racine.getChild("ligne_surligner").getAttributeValue("foreground").toUpperCase()).getCouleurForeground();
	}

	/**
	 *
	 * @return retourne le code de la couleur de fond par défaut des textes surlignés
	 */
	public String getSurlignerBackground(){
		return Couleurs.valueOf(racine.getChild("ligne_surligner").getAttributeValue("background").toUpperCase()).getCouleurBackground();
	}

	/**
	 * @return vrai si le texte surligné par défaut doit être en gras
	 */
	public boolean isSurlignerGras(){
		return (racine.getChild("ligne_surligner").getAttributeValue("gras").equalsIgnoreCase("vrai") ||
				racine.getChild("ligne_surligner").getAttributeValue("gras").equalsIgnoreCase("true")    );
	}
}
