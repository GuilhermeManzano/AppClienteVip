package br.com.exemplo.appclientevip.model;

public class ClientePJ extends  Cliente {

    private String cnpj;
    private String razaoSocial;
    private String dataAbertura;
    private boolean simplesNacional;
    private boolean mei;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public boolean isSimplesNacional() {
        return simplesNacional;
    }

    public void setSimplesNacional(boolean simplesNacional) {
        this.simplesNacional = simplesNacional;
    }

    public boolean isMei() {
        return mei;
    }

    public void setMei(boolean mei) {
        this.mei = mei;
    }
}
