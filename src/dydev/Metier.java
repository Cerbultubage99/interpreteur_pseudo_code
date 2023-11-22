package interpreteur.src.dydev;

import java.io.File;
import java.io.IOException;

import interpreteur.src.dydev.code.Interpreteur;
import org.fusesource.jansi.AnsiConsole;

public class Metier {

	public Metier(File fileCode) {
		new Interpreteur( fileCode);
	}

	public static void main(String[] args) throws IOException {

		if(System.getProperty("os.name").substring(0, 5).equalsIgnoreCase("windo")){
			AnsiConsole.systemInstall();
		}

		if(args.length != 1){
			System.out.println("Il faut renseigner un fichier .algo");
			return;
		}

		if(!args[0].substring(args[0].length()-(".algo".length())).equalsIgnoreCase(".algo")){
			System.out.println("Il faut renseigner un fichier .algo");
			return;
		}

		File file = new File(args[0]);
		if(!file.exists()) {
			System.out.println("Aucun fichier n'a été trouvé");
			return;
		}

		new Metier(file);
	}
}