package interpreteur.src.dydev.utilitaire;

/*----Les-imports-pour-la-lecture-de-fichier----*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * <b>FileReader est la classe qui permet de lire les fichiers</b>
 * @author Théo Maës
 * @version 1.0
 */
public class FileReader {

	/**
	 * <b>Scanner principal.</b>
	 * <p>
	 * Il est défini automatiquement par le constructeur
	 * </p>
	 *
	 * @see FileReader#FileReader(File)
	 */
	private Scanner scanner;

	/**
	 * Charset pour l'encodage de la lecture des fichiers
	 */
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * Fichier d'écriture
	 * @see FileReader#FileReader(File)
	 */
	private final File fichier;

	/**
	 * <b>Ligne actuelle du scanner</b>
	 *
	 * @see FileReader#ligneSuivante()
	 * @see FileReader#getLigneActuelle()
	 */
	private String ligneActuelle;

	/**
	 * <b>Numéro de la Ligne Actuelle.</b>
	 * <p>
	 * La première ligne est la numero zero
	 * </p>
	 *
	 * @see FileReader#getNumeroLigne()
	 */
	private int numLigne;


	/**
	 * <b>Constructeur FileReader</b>
	 *
	 * <p>
	 * A la construction de l'objet FileReader créer un scanner qu'il stock dans l'attribut 'scanner'.
	 * Le constructeur stock le fichier dans l'attribut fichier.
	 * Le numéro de la première ligne est défini a zero.
	 * </p>
	 *
	 * @param fichier : fichier a lire
	 *
	 * @see FileReader#scanner
	 * @see FileReader#ligneActuelle
	 * @see FileReader#numLigne
	 * @see FileReader#fichier
	 */
	public FileReader( File fichier ) {
		this.fichier = fichier;

		try {
			scanner = new Scanner ( new FileInputStream ( fichier.getAbsolutePath() ), ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.numLigne = 0;
	}

	/**
	 * <b>Permet d'aller à la ligne suivante.</b>
	 * <p>
	 * La ligne actuelle devient donc la ligne suivante
	 * </p>
	 * @return Ligne suivante du scanner
	 *
	 * @see FileReader#ligneActuelle
	 * @see FileReader#scanner
	 * @see FileReader#FileReader(File)
	 */
	public String ligneSuivante(){

		if(suivante()){
			ligneActuelle = scanner.nextLine();
		} else {
			ligneActuelle = null;
		}
		numLigne++;
		return ligneActuelle;
	}

	public boolean suivante(){
		return scanner.hasNextLine();
	}

	/**
	 * @return Ligne actuelle du scanner
	 *
	 * @see FileReader#ligneActuelle
	 * @see FileReader#scanner
	 * @see FileReader#FileReader(File)
	 */
	public String getLigneActuelle(){
		return ligneActuelle;
	}

	/**
	 * @return Numero de la ligne actuelle
	 */
	public int getNumeroLigne() {
		return numLigne;
	}

	/**
	 * <b>Recherche la ligne qui correspond au numéro de la ligne en paramètre.</b>
	 * <p>
	 * Positionne l'attribut scanner ainsi l'attribut ligneActuelle et l'attribut numLigne sur la ligne trouvée.
	 * Si la ligne n'est pas trouvée, retourne -1 et le scanner reste positionné sur la ligne actuelle.
	 * </p>
	 * @param numLigne : numéro de la ligne à rechercher dans le fichier
	 *
	 * @return La ligne correspondant au numéro de la ligne ou null si il n'est pas trouvé
	 * @see FileReader#ligneActuelle
	 * @see FileReader#scanner
	 * @see FileReader#numLigne
	 */
	public String getLigneNumero(int numLigne) {
		String ligne = null;
		int nbligne  = 0;

		Scanner scanner = null;
		try {
			scanner = new Scanner( new FileInputStream(fichier.getAbsolutePath() ), ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(true) {
			assert scanner != null;
			if (!scanner.hasNext()) break;
			ligne = scanner.nextLine();
			nbligne++;

			if(nbligne == numLigne){
				this.scanner        = scanner;
				this.ligneActuelle = ligne;
				this.numLigne       = numLigne;
				break;
			}else{
				ligne = null;
			}
		}
		return ligne;
	}

	/**
	 * <b>Recherche une ligne qui contient la chaine passée en paramètre.</b>
	 * <p>
	 * Positionne l'attribut scanner ainsi que l'attribut ligneActuelle et l'attribut numLigne sur la première ligne trouvée.
	 * Si la ligne n'est pas trouvée retourne null et le scanner reste positionné sur la ligne actuelle.
	 * </p>
	 * @param contenu : Chaine de caractère à rechercher dans le fichier
	 *
	 * @return La première ligne qui contient la chaine de caractère
	 * @see FileReader#ligneActuelle
	 * @see FileReader#scanner
	 * @see FileReader#numLigne
	 */
	public String getLigneContenant(String contenu) {
		String ligne    = null;
		int    numLigne = 0;

		Scanner scanner = null;
		try {
			scanner = new Scanner( new FileInputStream(fichier.getAbsolutePath() ), ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(true) {
			assert scanner != null;
			if (!scanner.hasNext()) break;
			ligne = scanner.nextLine();

			numLigne++;

			if(ligne.contains(contenu)){
				this.scanner        = scanner;
				this.ligneActuelle = ligne;
				this.numLigne       = numLigne;
				break;
			} else {
				ligne = null;
			}
		}
		return ligne;
	}


	/**
	 * <b>Recherche une ligne qui contient la chaine passée en paramètre.</b>
	 * <p>
	 * Positionne l'attribut scanner ainsi que l'attribut ligneActuelle et l'attribut numLigne sur la première ligne trouvée.
	 * Si la ligne n'est pas trouvée retourne null et le scanner reste positionné sur la ligne actuelle.
	 * </p>
	 * @param contenu : Chaine de caractère à rechercher dans le fichier
	 * @param numLigne: numéro de la ligne à partir de laquelle chercher
	 * @return La première ligne trouvée après le numero de ligne qui contient la chaine de caractères
	 * @see FileReader#ligneActuelle
	 * @see FileReader#scanner
	 * @see FileReader#numLigne
	 */
	public String getLigneAfter(int numLigne, String contenu) {
		String ligne    = null;
		Scanner sc = null;
		try {
			sc = new Scanner( new FileInputStream(fichier.getAbsolutePath() ), ENCODING);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int cptLigne = 0;
		while(true) {
			if (!sc.hasNext()) break;
			ligne = sc.nextLine();
			cptLigne++;

			if( (numLigne < cptLigne) && ligne.contains(contenu)){
				this.scanner        = sc;
				this.ligneActuelle = ligne;
				this.numLigne       = cptLigne;
				break;
			} else {
				ligne = null;
			}
		}
		return ligne;
	}
}