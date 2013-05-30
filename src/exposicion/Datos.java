/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exposicion;

/**
 *
 * @author jucazuse
 */
public class Datos {
    private String nombre;

    public Datos(String nombre) {
        this.nombre = nombre;
    }
    private String apellido;
    private String sexo;
    private String ciudadResidencia;
    private String direccion;
    private String telefonoCelular;
    private String telefonoFijo;
    private String correo;

    public Datos(String nombre, String apellido, String telefonoCelular, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonoCelular = telefonoCelular;
        this.correo = correo;
    }
    
      public Datos(String nombre, String apellido, String sexo, String ciudadResidencia, String direccion, String telefonoCelular, String telefonoFijo, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.ciudadResidencia = ciudadResidencia;
        this.direccion = direccion;
        this.telefonoCelular = telefonoCelular;
        this.telefonoFijo = telefonoFijo;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

  

}
