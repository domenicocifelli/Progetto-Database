package progettoDatabase;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Operations operations = new Operations();

        while (true) {
            System.out.println("\nSeleziona un'operazione:");
            System.out.println("1. Inserisci Cliente");
            System.out.println("2. Iscrivi un cliente a un corso");
            System.out.println("3. Assegna un allenamento a un cliente");
            System.out.println("4. Stampa dei corsi disponibili con i relativi istruttori");
            System.out.println("5. Stampa degli allenamenti disponibili in base al gruppo muscolare");
            System.out.println("6. Inserimento di un nuovo allenatore");
            System.out.println("7. Inserimento di un nuovo istruttore");
            System.out.println("8. Visualizza gli allenamenti proposti da uno speicifico allenatore");
            System.out.println("9. Visualizza i clienti iscritti a un determinato corso");
            System.out.println("10. Esci");

            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    operations.insertCliente(); 
                    break;

                case 2:
                    operations.newIscrizioneCorso(); 
                    break;

                case 3:
                    operations.assegnazioneAllenamento();
                    break;
                
                case 4:
                    operations.getCorsi();;
                    break;
                
                case 5:
                    operations.getAllenamento();
                    break;
                    
                case 6:
                    operations.newAllenatore();
                    break;
                    
                case 7:
                    operations.newIstruttore();
                    break;
                    
                case 8:
                    operations.getSpecificoAllenamento();
                    break;
                    
                case 9:
                	operations.getClientiCorso();
                	break;

                case 10:
                    System.out.println("Uscita dal programma...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Scelta non valida, riprova.");
                    break;
            }
        }
    }
}
