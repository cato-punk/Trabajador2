package Modelo;
public class Trabajador extends Persona {
    private String isapre;
    private String afp;

    public Trabajador(String nombre, String apellido, String rut, String isapre, String afp) {
        super(nombre, apellido, rut); // al constructor de Persona
        this.isapre = isapre;
        this.afp = afp;
    }

    public String getIsapre() {
        return isapre;
    }

    public void setIsapre(String isapre) {
        this.isapre = isapre;
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp;
    }

    @Override
    public String toString() {
        return "Trabajador{" +
                "nombre='" + nombre + '\'' + // a atributos protected de Persona
                ", apellido='" + apellido + '\'' +
                ", rut='" + rut + '\'' +
                ", isapre='" + isapre + '\'' +
                ", afp='" + afp + '\'' +
                '}';
    }
}