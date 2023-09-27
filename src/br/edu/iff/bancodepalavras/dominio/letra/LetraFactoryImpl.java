package br.edu.iff.bancodepalavras.dominio.letra;


public abstract class LetraFactoryImpl implements LetraFactory {

    private Letra[] pool = new Letra[26];
    private Letra encoberta;

    protected LetraFactoryImpl() {
        this.encoberta = null;
    }

    @Override
    public Letra getLetra(char codigo) {
    	
    	if((codigo < 'a' || codigo > 'z'))
    	{
    		throw new IllegalArgumentException();
    	}
       Letra result = this.pool[codigo - 'a']; 
       if (result == null) {
            result = this.criarLetra(codigo);
            this.pool[codigo - 'a'] = result; //inicio da pool
       }
       return result;
    }
	
	@Override 
    public Letra getLetraEncoberta() {
        if(encoberta == null) {
            char codigo = '*';
            this.encoberta = criarLetra(codigo);
        }
        return this.encoberta;
    }

    protected abstract Letra criarLetra(char codigo);
    
}
