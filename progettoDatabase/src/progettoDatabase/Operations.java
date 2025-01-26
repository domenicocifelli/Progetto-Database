package progettoDatabase;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Operations {
	
	Scanner sc = new Scanner(System.in);
	
	//OPERAZIONE 1 : REGISTRAZIONE CLIENTE
	public void insertCliente() {
		
		System.out.println("Inserisci codice fiscale del cliente:");
		String cf = sc.next();
		
		System.out.println("Inserisci nome del cliente:");
		String nome = sc.next();
		
		System.out.println("Inserisci cognome del cliente:");
		String cognome = sc.next();
		
		System.out.println("Inserisci data di nascita del cliente (YYYY-MM-DD):");
		String dataNascitaInput = sc.next();
        Date nascita = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            java.util.Date parsedDate = dateFormat.parse(dataNascitaInput);
            nascita = new Date(parsedDate.getTime());  // Converte a java.sql.Date
        } catch (ParseException e) {
            System.out.println("Formato della data non valido! Usa il formato YYYY-MM-DD.");
            return;
        }
		
		System.out.println("Inserisci email del cliente:");
		String email = sc.next();
		
		System.out.println("Inserisci numero di telefono del cliente:");
		String telefono = sc.next();
		
		String query = "INSERT INTO cliente (codiceFiscale, nome, cognome, dataNascita, email, numTelefono) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, cf);
			statement.setString(2, nome);
			statement.setString(3, cognome);
			statement.setDate(4, nascita);
			statement.setString(5, email);
			statement.setString(6, telefono);
			
			statement.executeUpdate();
			System.out.println("Dati cliente inseriti con successo!");
		}catch(Exception e) {
			System.out.println("Dati cliente non inseriti!");
			e.printStackTrace();
			}
	}
	
	//OPERAZIONE 2: ISCRIZIONE CLIENTE A UN CORSO
	public void newIscrizioneCorso() {
		
		int numClienti = 0;
		
		System.out.println("Inserire il codice fiscale del cliente da iscrivere al corso:");	
		String cf = sc.next();
		
		System.out.println("Inserire il corso al quale iscriverlo (Id corso):");
		int idCorso = sc.nextInt();
		
		String query = "INSERT INTO partecipazione(idCliente, idCorso) VALUES (?, ?)";
		String query2 = "SELECT numClienti FROM corso WHERE idCorso = " + idCorso; //aggiornare il numero di clienti del corso
		String query3 = "UPDATE Corso SET NumClienti = ? WHERE idCorso = ?";
		
		//ISCRIVO IL CLIENTE AL CORSO
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, cf);
			statement.setInt(2, idCorso);
			
			statement.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//VADO A RECUPERARE IL NUMERO DI UTENTI ISCRITTI
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement pquery = conn.createStatement();
			ResultSet rs = pquery.executeQuery(query2);
			
			while(rs.next()) {
				numClienti = rs.getInt("numClienti");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		numClienti++;
		
		//VADO AD AGGIORNARE IL NUMERO DI UTENTI
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = conn.prepareStatement(query3);
			
			statement.setInt(1, numClienti);
			statement.setInt(2, idCorso);
			
			statement.executeUpdate();
			
			System.out.println("Cliente iscritto corretamente al corso!");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Cliente non iscritto corretamente al corso!");
		}
	}
	
	//OPERAZIONE 3: ASSEGNAZIONE DI UN ALLENAMENTO A UN CLIENTE
	public void assegnazioneAllenamento() {
		
		System.out.println("Inserire l'allenamento da sottoporre al cliente (id allenamento):");
		int idAllenamento = sc.nextInt();
		
		System.out.println("Codice fiscale del cliente citato:");
		String cf = sc.next();
		
		String query = "UPDATE cliente SET allenamento = ? WHERE codiceFiscale = ?";
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setInt(1, idAllenamento);
			statement.setString(2, cf);
			
			statement.executeUpdate();
			
			System.out.println("Allenamento assegnato correttamente al cliente!");
		}catch(Exception e) {
			e.printStackTrace();		}
	}
	
	//OPERAZIONE 4: STAMPA DEI CORSI DISPONIBILI
	public void getCorsi() {
		String query = "SELECT c.nome AS nomeCorso, c.descrizione, " +
	               "i.nome AS nomeIstruttore, i.cognome " +
	               "FROM corso c " +
	               "JOIN istruttore i ON c.istruttore = i.codiceFiscale";
		
		StringBuilder sb = new StringBuilder();
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				String nomeCorso = rs.getString("nomeCorso");
				String descrizione = rs.getString("descrizione");
				String nomeIstruttore = rs.getString("nomeIstruttore");
				String cognomeIstruttore = rs.getString("cognome");
				
				System.out.println("Corso: " + nomeCorso + " " + descrizione + " Istruttore: " + nomeIstruttore + " " +  cognomeIstruttore);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//OPERAZIONE 6: INSERIMENTO NUOVO ISTRUTTORE
	public void newIstruttore() {
		System.out.println("Inserisci codice fiscale dell'istruttore:");
		String cf = sc.next();
		
		System.out.println("Inserisci nome dell'istruttore:");
		String nome = sc.next();
		
		System.out.println("Inserisci cognome dell'istruttore:");
		String cognome = sc.next();
		
		System.out.println("Inserisci data di nascita dell'istruttore (YYYY-MM-DD):");
		String dataNascitaInput = sc.next();
        Date nascita = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            java.util.Date parsedDate = dateFormat.parse(dataNascitaInput);
            nascita = new Date(parsedDate.getTime());  // Converte a java.sql.Date
        } catch (ParseException e) {
            System.out.println("Formato della data non valido! Usa il formato YYYY-MM-DD.");
            return;
        }
		
		System.out.println("Inserisci email dell'istruttore:");
		String email = sc.next();
		
		System.out.println("Inserisci numero di telefono dell'istruttore:");
		String telefono = sc.next();
		
		System.out.println("Inserisci la data di inizio contratto:");
		String dataInizioInput = sc.next();
        Date inizioContratto = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            java.util.Date parsedDate = dateFormat.parse(dataInizioInput);
            inizioContratto = new Date(parsedDate.getTime());  // Converte a java.sql.Date
        } catch (ParseException e) {
            System.out.println("Formato della data non valido! Usa il formato YYYY-MM-DD.");
            return;
        }
		
        System.out.println("Inserisci la sua specializzazione:");
        String specializzazione = sc.next();
        
		String query = "INSERT INTO istruttore (codiceFiscale, nome, cognome, dataNascita, email, numTelefono, dataInizioContratto, specializzazione) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, cf);
			statement.setString(2, nome);
			statement.setString(3, cognome);
			statement.setDate(4, nascita);
			statement.setString(5, email);
			statement.setString(6, telefono);
			statement.setDate(7, inizioContratto);
			statement.setString(8, specializzazione);
			
			statement.executeUpdate();
			System.out.println("Dati cliente inseriti con successo!");
		}catch(Exception e) {
			System.out.println("Dati cliente non inseriti!");
			e.printStackTrace();
			}
	}
	
	//OPERAZIONE 7: INSERIMENTO NUOVO ALLENATORE
	public void newAllenatore() {
		System.out.println("Inserisci codice fiscale dell'allenatore:");
		String cf = sc.next();
		
		System.out.println("Inserisci nome dell'allenatore:");
		String nome = sc.next();
		
		System.out.println("Inserisci cognome dell?allenatore:");
		String cognome = sc.next();
		
		System.out.println("Inserisci data di nascita dell'allenatore (YYYY-MM-DD):");
		String dataNascitaInput = sc.next();
        Date nascita = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            java.util.Date parsedDate = dateFormat.parse(dataNascitaInput);
            nascita = new Date(parsedDate.getTime());  // Converte a java.sql.Date
        } catch (ParseException e) {
            System.out.println("Formato della data non valido! Usa il formato YYYY-MM-DD.");
            return;
        }
		
		System.out.println("Inserisci email dell'allenatore:");
		String email = sc.next();
		
		System.out.println("Inserisci numero di telefono dell'allenatore:");
		String telefono = sc.next();
		
		System.out.println("Inserisci la data di inizio contratto:");
		String dataInizioInput = sc.next();
        Date inizioContratto = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            java.util.Date parsedDate = dateFormat.parse(dataInizioInput);
            inizioContratto = new Date(parsedDate.getTime());  // Converte a java.sql.Date
        } catch (ParseException e) {
            System.out.println("Formato della data non valido! Usa il formato YYYY-MM-DD.");
            return;
        }
        
		String query = "INSERT INTO allenatore (codiceFiscale, nome, cognome, dataNascita, email, numTelefono, dataInizioContratto) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, cf);
			statement.setString(2, nome);
			statement.setString(3, cognome);
			statement.setDate(4, nascita);
			statement.setString(5, email);
			statement.setString(6, telefono);
			statement.setDate(7, inizioContratto);
			
			statement.executeUpdate();
			System.out.println("Dati allenatore inseriti con successo!");
		}catch(Exception e) {
			System.out.println("Dati allenatore non inseriti!");
			e.printStackTrace();
			}
	}

	//OPERAZIONE 5: STAMPA ALLENAMENTO IN BASE AL GRUPPO MUSCOLARE
	public void getAllenamento() {
		
		System.out.println("Di quale gruppo muscolare vuoi vedere gli allenamenti disponibili?");
		String gruppoMuscolare = sc.next();
		
		String query = "SELECT * FROM allenamento WHERE gruppoMuscolare = '" + gruppoMuscolare + "'";
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				int idCorso = rs.getInt("idAllenamento");
				String giornoSettimanale = rs.getString("giornoSettimanale");
				String orario = rs.getString("orario");
				String esercizi = rs.getString("esercizi");
				
				System.out.println("Allenamento per il gruppo muscolare scelto:");
				System.out.println("Id: " + idCorso + " giorno della settimana: " + giornoSettimanale + " orario di esecuzione: " + orario + " esercizi da svolgere: " + esercizi );
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//OPERAZIONE 8: VISUALIZZAZIONE ALLENAMENTO IN BASE ALL'ALLENATORE
	public void getSpecificoAllenamento() {
		
		System.out.println("Inserisci il codice fiscale dell'allenatore del quale vuoi conoscere l'allenamento che propone:");
		String cf = sc.next();
		
		String query = "SELECT a.idAllenamento, a.giornoSettimanale, a.orario, a.esercizi, a.gruppoMuscolare " +
                "FROM allenamento a " +
                "JOIN allenatore al ON a.allenatore = al.codiceFiscale " +
                "WHERE al.codiceFiscale = '" + cf + "'";
		
		try {
			
			Connection conn = DatabaseConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				int idAllenamento = rs.getInt("idAllenamento");
				String giornoSettimanale = rs.getString("giornoSettimanale");
				String orario = rs.getString("orario");
				String esercizi = rs.getString("esercizi");
				String gruppoMUscolare = rs.getString("gruppoMuscolare");
				
				System.out.println("L'allenatore scelto propone il seguente allenamento:");
				System.out.println("Id: " + idAllenamento + " giorno della settimana: " + giornoSettimanale + " orario di esecuzione: " + orario + " esercizi da svolgere: " + esercizi + " gruppo muscolare coinvolto: " + gruppoMUscolare );
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//OPERAZIONE 9: VISUALIZZA NUMERO DI CLIENTI ISCRITTI A UN DETERMINATO CORSO E I LORO DATI
	public void getClientiCorso() {
		System.out.println("Inserisci l'id del corso per visualizzare i clienti iscritti:");
		int idCorso = sc.nextInt();
		
		String query = "SELECT c.nome, c.cognome, c.dataNascita, c.email, c.numTelefono " +
                "FROM cliente c " +
                "JOIN partecipazione p ON c.codiceFiscale = p.idCliente " +
                "JOIN corso co ON p.idCorso = co.idCorso " +
                "WHERE co.idCorso = " + idCorso;
		
		try {
			
			Connection conn = DatabaseConnection.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				Date dataNascita = rs.getDate("dataNascita");
				String email = rs.getString("email");
				String telefono = rs.getString("numTelefono");
				
				System.out.println("Clienti iscritti al corso selezionato:");
				System.out.println(nome + " " + cognome + " " + dataNascita + " " + email + " " + telefono);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}



