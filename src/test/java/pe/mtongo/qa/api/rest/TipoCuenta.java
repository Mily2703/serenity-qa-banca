package pe.mtongo.qa.api.rest;

/** Tipos de cuenta de ParaBank y el código numérico que esperan sus servicios. */
public enum TipoCuenta {
    CHECKING(0), SAVINGS(1);

    private final int codigo;

    TipoCuenta(int codigo) {
        this.codigo = codigo;
    }

    public int codigo() {
        return codigo;
    }
}
