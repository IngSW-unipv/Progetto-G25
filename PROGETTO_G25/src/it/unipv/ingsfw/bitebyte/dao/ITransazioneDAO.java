package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Transazione;

public interface ITransazioneDAO {
    boolean inserisciTransazione(Transazione transazione, String idOrd, int idPort);
}
