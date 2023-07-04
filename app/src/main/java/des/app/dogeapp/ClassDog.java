package des.app.dogeapp;

public class ClassDog {
    private String nombre;
    private String criadoPara;
    private String grupoRaza;
    private String esperanzaVida;
    private String temperamento;
    private String origen;
    private String imagenUrl;

    public ClassDog() {
    }

    public ClassDog(String nombre, String criadoPara, String grupoRaza, String esperanzaVida, String temperamento, String origen, String imagenUrl) {
        this.nombre = nombre;
        this.criadoPara = criadoPara;
        this.grupoRaza = grupoRaza;
        this.esperanzaVida = esperanzaVida;
        this.temperamento = temperamento;
        this.origen = origen;
        this.imagenUrl = imagenUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCriadoPara() {
        return criadoPara;
    }

    public void setCriadoPara(String criadoPara) {
        this.criadoPara = criadoPara;
    }

    public String getGrupoRaza() {
        return grupoRaza;
    }

    public void setGrupoRaza(String grupoRaza) {
        this.grupoRaza = grupoRaza;
    }

    public String getEsperanzaVida() {
        return esperanzaVida;
    }

    public void setEsperanzaVida(String esperanzaVida) {
        this.esperanzaVida = esperanzaVida;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    @Override
    public String toString() {
        return "ClassDog{" +
                "nombre='" + nombre + '\'' +
                ", criadoPara='" + criadoPara + '\'' +
                ", grupoRaza='" + grupoRaza + '\'' +
                ", esperanzaVida='" + esperanzaVida + '\'' +
                ", temperamento='" + temperamento + '\'' +
                ", origen='" + origen + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                '}';
    }
}
