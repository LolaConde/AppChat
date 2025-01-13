package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensajeTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorContactoDAO getContactoDAO() {
		return AdaptadorContactoTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorDescuentoDAO getDescuentoDAO() {
		return AdaptadorDescuentoTDS.getUnicaInstancia();
	}
}
