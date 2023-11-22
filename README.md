# interpreteur

<div align="center">
  <img src="https://github.com/Theo-Maes/interpreteur/blob/main/ressources/images/InterpreteurStart.png" alt="illustration" />
</div>


------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        Contexte
------------------------------------------------------------------------------------------------------------------------------------------------------------------

Dans le cadre de notre projet tutoré de S3, il nous était demandé de réalisé un interpreteur de pseudo-code.

Grâce à cet interpréteur, il est désormais possible d'avoir un suivi sur une console de l'exécution d'un pseudo-code n'étant pas compilable normalement. Il est également possible de suivre les modifications de chaque variable au cours de votre programme grâce un suivi ligne par ligne.

------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                        Fonctions
------------------------------------------------------------------------------------------------------------------------------------------------------------------

- Suivi du pseudo-code pas à pas, avec un affichage des lignes ainsi que celle où l'on se trouve;

- La coloration syntaxique pour une meilleure lisibilité à partir d'un fichier .xml;

- La gestion des types de bases ( entier, caractère, chaine de caractères, réél, booléen );

- La déclaration de variable, de une à plusieurs sur une ligne, ainsi que la trace au fil du code. L'affectation de ces dernières ( utilisez "<--" ) et la concaténation ( utilisez "©" );

- Il est également possible de déclarer des constantes;

- Les tableaux à une dimension sont possibles;

- La fonction "lire" permettant d'affecter à une variable ce que l'on souhaite, et la fonction "ecrire" qui renvoie la variable ou le texte en paramètre, sont       possibles;

- Les conditionnelles ( "if" + "else"), ainsi que les imbrications ( "if" dans un autre );

- Les quatres opérateurs de calculs fonctionnent individuellement ( la multiplication s'effectue avec ce caractère : × ). Les calculs DIV et MOD ( en majuscule ). La mise à la puissance ( s'effectue avec le caractère "^" ). Les nombres négatifs sont gérés ainsi que les expressions booléens simples;

- La gestion des conversions de chaine, entier, réél ( "enChaine" --> convertit la variable en chaine de caractère );

- La gestion des commentaires "//".

------------------------------------------------------------------------------------------------------------------------------------------------------------------
                                                 Lancement de l'application 
------------------------------------------------------------------------------------------------------------------------------------------------------------------

Pour lancer l'application, il vous suffit de suivre les étapes suivantes : 

- Ouvrez un terminal de commande
- Placez vous dans le répertoire     **interpreteur**
- Compilez à l'aide de la commande   **javac @compile.list -d [ emplacement souhaité pour les .class ]**
- Exécutez à l'aide de la commande   **java src/dydev/Metier.java [ chemin d'accès du fichier ]**

**/!\ Vous devez exécuter dans le repertoire interpreteur !**
