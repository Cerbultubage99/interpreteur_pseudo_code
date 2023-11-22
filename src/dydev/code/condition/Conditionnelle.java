package interpreteur.src.dydev.code.condition;

import interpreteur.src.dydev.utilitaire.FileReader;

import java.util.ArrayList;

/**
 * <b>Cette classe permet de se déplacer correctement dans les si.</b>
 * @author DyDev : Bosquain Maxence
 * @version 2.1
 */
public class Conditionnelle {
	private final String LIGNE;
	private final int    NUM_LIGNE;

	/**
	 * @param LIGNE     : La ligne où se situe le si.
	 * @param NUM_LIGNE : Le numéro de ligne où se situe le si.
	 */
	public Conditionnelle( String LIGNE, int NUM_LIGNE ) {
		this.LIGNE    = LIGNE;
		this.NUM_LIGNE = NUM_LIGNE;
	}

	/**
	 * En cas de condition fausse, vérifie la présence d'un "sinon" ou d'un "fsi". 
	 * Sinon, rentre dans la condition jusqu'au "sinon"/"fsi"
	 * @param fileReader Le fileReader sur lequel interagit la méthode .
	 */
	public void lireCondi(FileReader fileReader ) {
		String LIGNE = this.LIGNE;
		LIGNE = LIGNE.replace( "\t", "" ).replace( " ", "" ).substring( 2 );
		String[] separation = LIGNE.split( "alors$" );

		GestionBoolean b = new GestionBoolean( separation[0] );

		if( b.executerCondition() == false ) {

			ArrayList<String> tabStringSiSinonFsi = new ArrayList<>();
			ArrayList<Integer> tabIntSiSinonFsi = new ArrayList<>();

			String ligneActu = "";
			while( ligneActu != null ) {
				if( ligneActu.contains( "si " ) ) {
					tabStringSiSinonFsi.add( "si" );
					tabIntSiSinonFsi   .add( fileReader.getNumeroLigne() );
				}

				if( ligneActu.contains( "sinon" ) ) {
					tabStringSiSinonFsi.add( "sinon" );
					tabIntSiSinonFsi   .add( fileReader.getNumeroLigne() );
				}

				if( ligneActu.contains( "fsi" ) ) {
					tabStringSiSinonFsi.add( "fsi" );
					tabIntSiSinonFsi   .add( fileReader.getNumeroLigne() );
				}

				ligneActu = fileReader.ligneSuivante();
			}
			int ligneVoulu = -1;

			boolean estDansSi = false;
			for ( int cpt = 0; ligneVoulu == -1; cpt++) {
				if( tabStringSiSinonFsi.get( cpt ).matches( "si" ) ) {
					estDansSi = true;
				}

				if( tabStringSiSinonFsi.get( cpt ).matches( "sinon" ) && estDansSi == false ) {
					ligneVoulu = tabIntSiSinonFsi.get( cpt );
				} else {
					if( tabStringSiSinonFsi.get( cpt ).matches( "fsi" ) && estDansSi == false ) {
						ligneVoulu = tabIntSiSinonFsi.get( cpt );
					} else {
						if( tabStringSiSinonFsi.get( cpt ).matches( "fsi" ) && estDansSi ) {
							estDansSi = false;
						}
					}
				}
			}

			fileReader.getLigneNumero( this.NUM_LIGNE );
			for( int cpt = 0; cpt < ligneVoulu - NUM_LIGNE; cpt++){
				fileReader.ligneSuivante();
			}
		}
	}
}