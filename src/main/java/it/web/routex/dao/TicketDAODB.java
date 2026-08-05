package it.web.routex.dao;
import it.web.routex.model.Ticket;
import it.web.routex.utility.factory.ConnectionFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.web.routex.exception.PathNotFoundExceptionRemoli;
import it.web.routex.exception.DAOExceptionBrondi;
import it.web.routex.utility.singleton.Credentials;
import it.web.routex.exception.CredentialsExceptionBrondi;

public class TicketDAODB extends TicketDAOLayer {

    @Override
    public List<Ticket> getTicketByCF(String cf)
            throws DAOExceptionBrondi, PathNotFoundExceptionRemoli {

        List<Ticket> lista = new ArrayList<>();
        String sP = "{ CALL RouteX_Update.getTicketByCF(?) }";

        try (Connection conn = ConnectionFactory.getConnection();
             CallableStatement cs = conn.prepareCall(sP)) {

            cs.setString(1, cf);

            try (ResultSet rs = cs.executeQuery()) {

                if (!rs.next()) {
                    throw new PathNotFoundExceptionRemoli(
                            "Nessun biglietto trovato",
                            cf,
                            404,
                            "TicketDAODB.getTicketByCF"
                    );
                }

                do {
                    Ticket t = new Ticket(
                            rs.getString("codice_biglietto"),
                            rs.getString("citta"),
                            rs.getTimestamp("data_pagamento").toLocalDateTime()
                    );
                    lista.add(t);
                } while (rs.next());
            }

            return lista;

        } catch (SQLException ex) {
            throw new DAOExceptionBrondi("Errore DB", ex);
        }
    }


    @Override
    public void salvataggio(Credentials cred, List<String> codiciBiglietti, String metodopayment, String city) throws CredentialsExceptionBrondi {
        try {
            Connection conn = ConnectionFactory.getConnection();
            String bigliettiConcatenati = String.join(",", codiciBiglietti);
            CallableStatement cs = conn.prepareCall("{ CALL RouteX_Update.SavePayment(?,?,?,?,?,?,?) }");

            cs.setString(1, cred.getCodiceFiscale());
            cs.setString(2, cred.getNome());
            cs.setString(3, cred.getCognome());
            cs.setString(4, String.valueOf(cred.getDisabile()));
            cs.setString(5, metodopayment);
            cs.setString(6, bigliettiConcatenati);
            cs.setString(7, city);
            cs.executeQuery();

        } catch (SQLException e) {
            throw new CredentialsExceptionBrondi("Nessun salvataggio del percorso nel livello di persistenza " + e.getMessage(), "Errore in SalvaPagamentoDAO.java");
        }
    }

}
