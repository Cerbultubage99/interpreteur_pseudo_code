package interpreteur.src.dydev.code;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

import interpreteur.src.dydev.code.affichage.Affichage;
import interpreteur.src.dydev.code.math.DecompMath;
import interpreteur.src.dydev.code.variable.Variable;
import interpreteur.src.dydev.utilitaire.FileReader;
import interpreteur.src.dydev.code.condition.Conditionnelle;

/**
 * @author DyDev : Maes Theo, Maxence
 */
public class Interpreteur
{
	/**
	 * <b> Le lecteur de fichier qui sera utilisé </b>
	 *
	 * @see Interpreteur#Interpreteur(File)
	 *
	 */
	private Affichage affichage;

	public Interpreteur( File file )
	{
		Scanner scanner = new Scanner(System.in);

		FileReader fileReader = new FileReader( file );

		this.affichage = new Affichage( file );

		Variable.creationVariable(file);

		fileReader.getLigneContenant( "DEBUT" );

		this.affichage.setNumLigneSurligner( fileReader.getNumeroLigne() + 1 );

		String ligneActu = "";
		while( !( ligneActu.matches( "FIN" ) )  )
		{
			avancer( fileReader );
			this.affichage.majTabDonn();
			this.affichage.majTabVal();
			System.out.print( this.affichage.toString() );

			ligneActu = fileReader.getLigneActuelle().replaceAll( "\t", "" );

			if(ligneActu.contains("//")){
				for( int i = 0; i<ligneActu.length()-1;i++){
					if(ligneActu.charAt(i) == '/' && ligneActu.charAt(i+1) == '/'){
						ligneActu = ligneActu.substring(0,i);
					}
				}
			}

			if( ligneActu.matches( ".+<--.+" ) ) {
				String[] info  = ligneActu.split("<--");

				if(info[info.length-1].startsWith(" ")){
					info[info.length-1] = info[info.length-1].replaceFirst(" ","");
					if(!Variable.isVariable(info[info.length-1])) {
						if(info[info.length-1].matches("(((enChaine)|(enEntier)|(enRéel)) *\\(.+\\))")){
							String[] ligneConversions = info[info.length-1].split("\\(");
							switch (ligneConversions[0]) {
								case "enChaine" -> {
									if (Pattern.matches("(.+[\\(\\)+\\-\\/%^|\u00D7].+)", ligneConversions[1])) {
										ligneConversions[1] = ligneConversions[1].replaceAll("[ \\)]", "");
										String[] tmp = ligneConversions[1].split("[)(+-/%^|\u00D7]");
										for (String string : tmp) {
											if (Variable.isVariable(string)) {
												ligneConversions[1] = ligneConversions[1].replaceFirst(string, Variable.getVariable(string).getValeur() + "");
											}
										}
										info[info.length - 1] = DecompMath.calculer(ligneConversions[1]) + "";
									} else {
										ligneConversions[1] = ligneConversions[1].replaceAll("[ )]", "");
										info[info.length - 1] = Variable.getVariable(ligneConversions[1]).getValeur() + "";
									}
								}
								case "enRéel", "enEntier" -> {
									if (Pattern.matches("\".*\"", ligneConversions[1].replaceAll("[ )]", ""))) {
										ligneConversions[1] = ligneConversions[1].replaceAll("[\" )]", "");
										info[info.length - 1] = ligneConversions[1];
									} else {
										ligneConversions[1] = ligneConversions[1].replaceAll("[ )]", "");
										if (Variable.isVariable(ligneConversions[1])) {
											info[info.length - 1] = Variable.getVariable(ligneConversions[1]).getValeur() + "";
										}
									}
								}
							}
						}

						if(Pattern.matches("(((\\d+\\.\\d+)|(\\d))? ?[\\(\\)+\\-\\/%^|×] ?((\\d+\\.\\d+)|(\\d))+)*",
								info[info.length-1].replaceAll("DIV","/").replaceAll("MOD","%"))){
							double result = DecompMath.calculer(info[info.length-1]);
							if(Pattern.matches("(\\d+.0+)",result +"")) {
								info[info.length-1] = (int) result + "";
							} else {
								info[info.length-1] = result + "";
							}
						}

						if(info[info.length-1].contains("\u00A9")){
							String[] textes = info[info.length-1].split("\u00A9");
							StringBuilder phrase = new StringBuilder();
							for (String text: textes) {
								if(text.startsWith(" ")){
									text = text.replaceFirst(" ","");
								}
								if(text.startsWith(" ", text.length() - 1)){
									text = text.substring(0, text.length()-1);
								}
								if(Variable.isVariable(text)){
									text = Variable.getVariable(text).getValeur() + "";
								}
								phrase.append(text);
							}
							info[info.length-1] = phrase.toString();
						}
					} else {
						info[info.length-1] = Variable.getVariable(info[info.length-1]).getValeur() + "";
					}
				}

				for (int i = 0; i < info.length-1; i++) {
					if(!Variable.modifierValeur(info[i].replaceAll("[\t ]", ""),info[info.length-1])){
						Variable.modifierValeurTabIndex(info[i].replaceAll("[\t ]", ""),info[info.length-1]);
					}
				}
			}

			if( ligneActu.matches( "ecrire (.*)" ) ) {
				affichage.ecrire(this.recupContenuEcrire(ligneActu));
			}

			if( ligneActu.matches("lire (.*)") ) {
				String contenu = ligneActu.split("\\(")[1];
				contenu = contenu.substring(0,contenu.length()-1).replaceAll("[\t ]","");
				for (String nomVariable: contenu.split(",")) {
					System.out.print(">");
					String valeur = scanner.nextLine();
					if(!Variable.modifierValeur(nomVariable, valeur)){
						Variable.modifierValeurTabIndex(nomVariable,valeur);
					}
				}
			}

			if( ligneActu.matches( "si .*" ) )
			{
				Conditionnelle b = new Conditionnelle( ligneActu, fileReader.getNumeroLigne() );
				b.lireCondi( fileReader );
				this.affichage.setNumLigneSurligner( fileReader.getNumeroLigne() + 1 );
			}

			String user = scanner.nextLine();
		}
	}

	private String recupContenuEcrire( String ligne )
	{
		String res = "";
		boolean coteOuvert = false;
		for( int cpt = 0; cpt < ligne.length(); cpt++ )
		{
			if( ligne.charAt( cpt ) == '\"' )
				coteOuvert = !coteOuvert;

			if( ligne.charAt( cpt ) == ' ' && coteOuvert == false )
				res += "";
			else
				res += ligne.charAt( cpt );
		}

		String[] decoup1 = res.split( "ecrire\\(\"" );
		String[] decoup2 = decoup1[1].split( "\"\\)" );

		return res = decoup2[0];
	}

	private void avancer( FileReader fileReader )
	{
		this.affichage.setNumLigneSurligner( fileReader.getNumeroLigne() + 1 );
		fileReader.ligneSuivante();
	}
}


/*

while( ligneActu.matches( ".+:.+" ) )
{
	ligneActu = ligneActu.replace( " ", "" );
	String[] tabType = ligneActu.split( ":" );

	String[] tabVar = tabType[0].split( "," );
}

while( ligneActu.matches( ".+<--.+" ) )
{
	ligneActu = ligneActu.replace( " ", "" );
	String[] tabType = ligneActu.split( "<--" );
}

*/
