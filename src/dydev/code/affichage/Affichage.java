package interpreteur.src.dydev.code.affichage;

/*----Les-imports-pour-l-affichage-du-programme----*/
import interpreteur.src.dydev.utilitaire.FileReader;
import interpreteur.src.dydev.utilitaire.MotClef;
import interpreteur.src.dydev.code.variable.Variable;


import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * <b>Affichage est la classe qui permet d'afficher toutes les informations du pseudo-code</b>
 * @author DyDev : Bosquain Maxence, Maës Théo
 * @version 2.0
 */
public class Affichage
{
	/**
	 * <b> List des valeur des donnees du pseudo-code </b>
	 */
	private ArrayList<Object> tabVal;

	/**
	 * <b> List des donnees du pseudo-code </b>
	 */
	private ArrayList<String> tabDonn;

	/**
	 * <b> List des variable du pseudo-code </b>
	 *
	 * @see Affichage#initTabCode()
	 *
	 */
	private ArrayList<Integer> tabNum;

	/**
	 * <b> List des ligne du pseudo-code </b>
	 *
	 * @see Affichage#initTabCode()
	 *
	 */
	private ArrayList<String> tabCode;

	/**
	 * <b> List représentant la console </b>
	 *
	 */
	private ArrayList<String> tabCons;

	/**
	 * <b> Numéro de ligne qui sera surlignée </b>
	 * <p>
	 * Est initialisée à 0 donc aucune ligne n'est surlignée
	 * </p>
	 */
	private int numLigneSurligner;

	/**
	 * <b> Numéro de la ligne où débutera l'affichage </b>
	 * <p>
	 * Est initialisée à 0 donc la première ligne est affichée
	 * </p>
	 */
	private int numLigneAfficher;

	/**
	 * <b> Le lecteur de fichier qui sera utilisé </b>
	 *
	 * @see Affichage#Affichage(File)
	 *
	 */
	private FileReader fileReader;

	/**
	 *
	 */
	private File file;

	/**
	 * <b>Constructeur Affichage</b>
	 *
	 * <p>
	 * Ce constructeur initialise les deux Array liste tabCode tabNum et crée une classe FileReader
	 * </p>
	 *
	 * @see Affichage#fileReader
	 * @see Affichage#tabCode
	 * @see Affichage#tabNum
	 */
	public Affichage( File fichier )
	{
		this.fileReader = new FileReader( fichier );

		this.file = fichier;

		this.tabCode = new ArrayList<>();
		this.tabNum  = new ArrayList<>();
		this.tabCons = new ArrayList<>();
		this.tabVal = new ArrayList<>();
		this.tabDonn = new ArrayList<>();

		initTabCode();
		initTabCons();
	}

	/**
	 *
	 */
	public void majTabDonn(  )
	{
		this.tabDonn.clear();
		File fichierVar = new File(file.getPath().replace(".algo", ".var"));
		if(fichierVar.exists()) {
			for( Variable v : Variable.getVariables() ) {
				FileReader reader = new FileReader(fichierVar);
				while(reader.suivante()) {
					reader.ligneSuivante();
					if (reader.getLigneActuelle() != null) {
						if (reader.getLigneActuelle().equals(v.getNomVar())) {
							this.tabDonn.add(v.getNomVar() + " : ");
						}
					}
				}
			}
		}

		for( int cpt = 0; cpt < 100; cpt++ )
			this.tabDonn.add( "" );
	}

	/**
	 *
	 */
	public void majTabVal(  )
	{
		this.tabVal.clear();

		File fichierVar = new File(file.getPath().replace(".algo", ".var"));
		if(fichierVar.exists()) {
			for (Variable v : Variable.getVariables()) {
				FileReader reader = new FileReader(fichierVar);
				while(reader.suivante()){
					if(reader.getLigneActuelle() != null) {
						if (reader.getLigneActuelle().equals(v.getNomVar())) {
							this.tabVal.add(v.getValeur());
						}
					}
					reader.ligneSuivante();
				}
			}
		}

		for( int cpt = 0; cpt < 100; cpt++ )
		{
			this.tabVal.add( "" );
		}
	}

	/**
	 *
	 */
	private void initTabCons()
	{
		for( int cpt = 0; cpt < 4; cpt++ )
			this.tabCons.add( "                                                                                                                      " );
	}

	/**
	 * <b>Initialiseur de TabCode et TabNum</b>
	 *
	 * <p>
	 * Cette classe initialise les deux ArrayList TabCode et TabNum à l'aide du FileReader
	 * </p>
	 *
	 * @see Affichage#fileReader
	 * @see Affichage#tabCode
	 * @see Affichage#tabNum
	 */
	private void initTabCode()
	{
		int cpt = 0;
		do
		{
			tabNum.add( cpt + 1 );
			tabCode.add( this.fileReader.ligneSuivante().replace( "\t", "    " ) );
			cpt++;
		}
		while( this.fileReader.suivante() );
	}

	/**
	 * <b>Seteur de numLigneSurligner</b>
	 *
	 * <p>
	 * Cette classe permet de changer la ligne surlignée en changeant numLigneSurligner
	 * </p>
	 *
	 *@param num : numéro de la ligne qui sera surlignée
	 *
	 * @see Affichage#numLigneSurligner
	 */
	public void setNumLigneSurligner( int num )
	{
		if( num >= numLigneAfficher + 35 )
			this.descendre();

		this.numLigneSurligner = num;
	}

	/**
	 *
	 */
	public void monter()
	{
		if( this.numLigneAfficher != 0 )
			this.numLigneAfficher--;
	}

	/**
	 *
	 */
	public void descendre()
	{
		if( this.numLigneAfficher - tabCode.size() < -40 )
			this.numLigneAfficher++;
	}

	/**
	 *
	 * @param num
	 */
	public void setNumLigneAfficher( int num )
	{
		if( num < tabCode.size() )
			this.numLigneAfficher = num;
	}

	/**
	 *
	 * @param align
	 * @param longueur
	 * @param type
	 * @param ligne
	 * @param surligner
	 * @return
	 */
	private String Colorisation( String align, int longueur, String type, String ligne, boolean surligner )
	{
		MotClef.generer();

		ArrayList<String> alMots = new ArrayList<String>(Arrays.asList(ligne.split( " " )));

		int nbDefoisColo = 0;
		for( int cpt = 0; cpt < alMots.size(); cpt++ )
		{
			if( MotClef.getMotCleByName( alMots.get( cpt ).replaceAll( "\t", "" ) ) != null )
			{
				alMots.add( cpt + 1, (surligner? MotClef.getDefautSurligner():MotClef.getDefault()) );
				alMots.add( cpt, MotClef.getMotCleByName( alMots.get( cpt ).replaceAll( "\t", "" ) ).getCodeCouleur() );
				cpt++;
				nbDefoisColo++;
			}
		}

		String format = align +( longueur + ( 19 * nbDefoisColo ) ) + type;



		String res = "";
		for( String s : alMots )
			res += s + " ";

		return String.format( format, res.replace( "\t", "    " ) );
	}

	/**
	 *
	 * @param s
	 * @return
	 */
	private static String sansAccent(String s) {
		String strTemp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(strTemp).replaceAll("");
	}


	/**
	 *
	 * @param message
	 */
	public void ecrire( String message )
	{
		for( int cpt = 0; cpt < 30 - message.length(); cpt++ )
			message += " ";

		this.tabCons.set( 3, this.tabCons.set( 2, message ) );
		this.tabCons.set( 2, this.tabCons.set( 1, message ) );
		this.tabCons.set( 1, this.tabCons.set( 0, message ) );
		this.tabCons.set( 0, message                        );
	}

	/**
	 * <b>ToString de Affichage</b>
	 *
	 * <p>
	 * Cette classe initialise les deux ArrayList TabCode et TabNum à l'aide du FileReader
	 * </p>
	 */
	public String toString()
	{
		StringBuilder res = new StringBuilder();

		if (System.getProperty("os.name").substring(0, 5).equalsIgnoreCase("windo")) {
			res.append(MotClef.getDefault()).append("+").append(getLigneString(39, "=")).append("+").append(getLigneString(86, "=")).append("+").append(Couleurs.normal()).append("\n");
			res.append(MotClef.getDefault()).append("|").append(String.format("%-39s", " Donnees")).append("|").append(String.format("%-86s", " code")).append("|").append(Couleurs.normal()).append("\n");
			res.append(MotClef.getDefault()).append("+").append(getLigneString(39, "=")).append("+").append(getLigneString(86, "=")).append("+").append(Couleurs.normal()).append("\n");

			int cptTotal = 0;
			for (int i = this.numLigneAfficher;  i < tabCode.size() && i < 40 + this.numLigneAfficher ; i++, cptTotal++) {
				if (i + 1 != this.numLigneSurligner) {
					res.append(MotClef.getDefault()).append("| ").append(String.format("%20s", tabDonn.get(cptTotal))).append(String.format("%-17s", tabVal.get(cptTotal))).append(" | ").append(String.format("%-85s", String.format("%-2s", tabNum.get(i)) + " " + sansAccent(Colorisation("%-", 82, "s", tabCode.get(i), false))));
				} else {
					res.append(MotClef.getDefault()).append("| ").append(String.format("%20s", tabDonn.get(cptTotal))).append(String.format("%-17s", tabVal.get(cptTotal))).append(" | ").append(MotClef.getDefautSurligner()).append(String.format("%-85s", (String.format("%-2s", tabNum.get(i)) + " " + sansAccent(Colorisation("%-", 82, "s", tabCode.get(i), true))))).append(Couleurs.normal()).append(MotClef.getDefault());
				}
				res.append("|").append(Couleurs.normal()).append("\n");
			}
			res.append(MotClef.getDefault()).append("+").append(getLigneString(39, "=")).append("+").append(getLigneString(86, "=")).append("+").append(Couleurs.normal()).append("\n");

			for (int i = 3; i >= 0; i--) {
				res.append(MotClef.getDefault()).append("| ").append(String.format("%-125s", tabCons.get(i))).append("|").append(Couleurs.normal()).append("\n");
			}

		} else {

			res.append(MotClef.getDefault()).append("╔").append(getLigneString(39, "═")).append("╦").append(getLigneString(86, "═")).append("╗").append(Couleurs.normal()).append("\n");
			res.append(MotClef.getDefault()).append("║").append(String.format("%-39s", " Donnees")).append("│").append(String.format("%-86s", " code")).append("║").append(Couleurs.normal()).append("\n");
			res.append(MotClef.getDefault()).append("╠").append(getLigneString(39, "─")).append("┼").append(getLigneString(86, "─")).append("╣").append(Couleurs.normal()).append("\n");

			int cptTotal = 0;
			for (int i = this.numLigneAfficher;  i < tabCode.size() && i < 40 + this.numLigneAfficher ; i++, cptTotal++) {
				if (i + 1 != this.numLigneSurligner) {
					res.append(MotClef.getDefault()).append("║ ").append(String.format("%20s", tabDonn.get(cptTotal))).append(String.format("%-17s", tabVal.get(cptTotal))).append(" │ ").append(String.format("%-85s", String.format("%-2s", tabNum.get(i)) + " " + Colorisation("%-", 82, "s", tabCode.get(i), false)));
				} else {
					res.append(MotClef.getDefault()).append("║ ").append(String.format("%20s", tabDonn.get(cptTotal))).append(String.format("%-17s", tabVal.get(cptTotal))).append(" │ ").append(MotClef.getDefautSurligner()).append(String.format("%-85s", (String.format("%-2s", tabNum.get(i)) + " " + Colorisation("%-", 82, "s", tabCode.get(i), true)))).append(Couleurs.normal()).append(MotClef.getDefault());
				}
				res.append("║").append(Couleurs.normal()).append("\n");
			}
			res.append(MotClef.getDefault()).append("╠").append(getLigneString(39, "═")).append("╩").append(getLigneString(86, "═")).append("╣").append(Couleurs.normal()).append("\n");

			for (int i = 3; i >= 0; i--) {
				res.append(MotClef.getDefault()).append("║ ").append(String.format("%-125s", tabCons.get(i))).append("║").append(Couleurs.normal()).append("\n");
			}
		}
		return res.toString();
	}

	/**
	 *
	 * @param taille
	 * @param symbole
	 * @return
	 */
	private String getLigneString(int taille, String symbole) {
		return String.format("%" + taille +"s", " " ).replaceAll(" ", symbole);
	}
}
