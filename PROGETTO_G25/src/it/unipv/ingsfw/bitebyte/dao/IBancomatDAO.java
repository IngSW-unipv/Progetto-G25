package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Bancomat;
import it.unipv.ingsfw.bitebyte.models.Cliente;

public interface IBancomatDAO {
    boolean creaBancomat(Bancomat bancomat, Cliente cliente);
    Bancomat leggiBancomat(String cf);
}
