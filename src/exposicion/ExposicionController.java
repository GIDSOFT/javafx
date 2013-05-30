/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exposicion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author jucazuse
 */
public class ExposicionController implements Initializable {

    /*Anotacion que etiqueta una clase o miembro como accesible*/
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private RadioButton rbMasculino;
    @FXML
    private RadioButton rbFemenino;
    @FXML
    private ToggleGroup grupoSexo;
    @FXML
    private ComboBox cbCiudad;
    @FXML
    private TableView jTRegistros;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtTelefonoFijo;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TableColumn<Datos, String> primeraColumna;
    @FXML
    private TableColumn<Datos, String> segundaColumna;
    @FXML
    private TableColumn<Datos, String> terceraColumna;
    @FXML
    private TableColumn<Datos, String> cuartaColumna;
    @FXML
    private String separadorArchivo = System.getProperty("file.separator");
    @FXML
    private String rutaDondeSeCreaArchivo = System.getProperty("user.dir");
    @FXML
    private File file = new File(rutaDondeSeCreaArchivo.concat(separadorArchivo.concat("Agenda.txt")));
    @FXML
    private String linea;
    @FXML
    private String[] pos;
    @FXML
    private FileReader lectorArchivo;
    @FXML
    private BufferedReader almacen;
    @FXML
    private String almacenFilaUno;
    @FXML
    private String almacenFilaDos;
    @FXML
    private String almacenFilaTres;
    @FXML
    private String almacenFilaCuatro;
    @FXML
    private File imagen;
    @FXML
    private Label lbImagen;
    @FXML
    private Stage primaryStage;
    @FXML
    private FileWriter fichero;
    @FXML
    private ImageView visor;
    @FXML
    private InputStream inputStream;
    @FXML
    private Image image;
    @FXML
    private MenuBar mbGuardar;
    @FXML
    private String subString;
    @FXML
    private int longitudCadena;
    @FXML
    private File rutaImagen;
    @FXML
    private File imagenItemGuardar;
    @FXML
    private File imagenItemReporte;
    @FXML
    private JRCsvDataSource dataSource;

    /*Metodo para llenar comboBox*/
    @FXML
    private void llenarComboBox() {
        /*con esto borramos los Items que trae por defecto los 
         choice box*/
        cbCiudad.getItems().clear();
        cbCiudad.setItems(FXCollections.observableArrayList("Alcala", "Andalucia", "Ansermanuevo",
                "Argelia", "Bolivar", "Buenaventura", "Buga", "Bugalagrande", "Caicedonia", "Cali",
                "Calima Darien", "Candelaria", "Cartago", "Cartago", "El Aguila", "El Cairo",
                "El Cerrito", "El Dovio", "Florida", "Ginebra", "Guacari", "Jamundi", "La Cumbre",
                "La Union", "La Victoria", "Obando", "Palmira", "Pradera", "Restrepo", "Riofrío",
                "Roldanillo", "San Pedro", "Sevilla", "Toro", "Trujillo", "Tulua", "Ulloa", "Versalles",
                "Vijes", "Yotoco", "Zarzal"));
    }

    /*Metodo para que solo se pueda escoger un radio boton*/
    @FXML
    private void BotonGrupo() {
        grupoSexo = new ToggleGroup();
        rbFemenino.setToggleGroup(grupoSexo);
        rbFemenino.setSelected(true);
        rbMasculino.setToggleGroup(grupoSexo);
    }

    /*Metodo para cargar Columnas*/
    @FXML
    public void columnas() {
        primeraColumna = new TableColumn<>("Nombre");
        segundaColumna = new TableColumn<>("Apellido");
        terceraColumna = new TableColumn<>("Tel Celular");
        cuartaColumna = new TableColumn<>("Correo");
        jTRegistros.getColumns().addAll(primeraColumna, segundaColumna, terceraColumna, cuartaColumna);
    }

    /*Metodo para cargar filas*/
    @FXML
    public void filas() {
        primeraColumna.setCellValueFactory(new PropertyValueFactory<Datos, String>("nombre"));
        segundaColumna.setCellValueFactory(new PropertyValueFactory<Datos, String>("apellido"));
        terceraColumna.setCellValueFactory(new PropertyValueFactory<Datos, String>("telefonoCelular"));
        cuartaColumna.setCellValueFactory(new PropertyValueFactory<Datos, String>("correo"));

    }

    /*Metodos para cargar la tabla con los datos que 
     agrego el usuario*/
    @FXML
    public void cargarEnLaTabla(ActionEvent evento) {

        if (txtNombre.getText().isEmpty()) {
            Dialogs.showWarningDialog(primaryStage, "El campo nombre es necesario llenarlo", "Error", "Verificacion");
        }
        if (txtApellido.getText().isEmpty()) {
            Dialogs.showWarningDialog(primaryStage, "El campo apellido es necesario llenarlo", "Error", "Verificacion");
        }
        if (txtCelular.getText().isEmpty()) {
            Dialogs.showWarningDialog(primaryStage, "El campo celular es necesario llenarlo", "Error", "Verificacion");
        }
        if (txtCorreo.getText().isEmpty()) {
            Dialogs.showWarningDialog(primaryStage, "El campo correo es necesario llenarlo", "Error", "Verificacion");
        } else {
            filas();
            final ObservableList<Datos> contacto = FXCollections.observableArrayList(
                    new Datos(txtNombre.getText(), txtApellido.getText(), txtCelular.getText(), txtCorreo.getText()));
            jTRegistros.getItems().addAll(contacto);
            Dialogs.showInformationDialog(primaryStage, "Señor Usuario su Registro fue Agregado a la Tabla. Pero no al Archivo","Informacion","INFORMACION");
        }
       
    }


    /*Metodo para cargar la tabla apenas arranque la apliacion*/
    @FXML
    public void cargarRegistrosALaTabla() {
        try {
            lectorArchivo = new FileReader(file);
            almacen = new BufferedReader(lectorArchivo);
            try {
                while ((linea = almacen.readLine()) != null) {
                    if (!linea.isEmpty()) {
                        pos = linea.split(";");
                        almacenFilaUno = pos[0];
                        almacenFilaDos = pos[1];
                        almacenFilaTres = pos[2];
                        almacenFilaCuatro = pos[3];
                        filas();
                        final ObservableList<Datos> contacto = FXCollections.observableArrayList(
                                new Datos(almacenFilaUno, almacenFilaDos, almacenFilaTres,
                                almacenFilaCuatro));
                        jTRegistros.getItems().addAll(contacto);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Metodo para que el usuario escoja una imagen del contacto
     agregado*/
    @FXML
    public void elegirImagen(ActionEvent elegirImagen) {
        FileChooser elegir = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Add Files(*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extensionPNG = new FileChooser.ExtensionFilter("Add Files(*.png)", "*.png");
        FileChooser.ExtensionFilter extensionJPEG = new FileChooser.ExtensionFilter("Add Files(*.jpeg)", "*.jpeg");
        elegir.getExtensionFilters().add(extension);
        elegir.getExtensionFilters().add(extensionPNG);
        elegir.getExtensionFilters().add(extensionJPEG);
        rutaImagen = elegir.showOpenDialog(primaryStage);
        try {
            if (rutaImagen == null) {
                Dialogs.showWarningDialog(primaryStage, "Usted no ha seleccionado ninguna imagen", "Error", "Verificacion");
            } else {
                inputStream = new FileInputStream(rutaImagen);
                image = new Image(inputStream);
                visor = new ImageView(image);
                visor.setFitWidth(128);
                visor.setPreserveRatio(true);
                lbImagen.setGraphic(visor);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Metodo para cargar la imagen de muestra apenas carga el
     programa*/
    @FXML
    public void imagenPrincipio() {
        try {
            imagen = new File(rutaDondeSeCreaArchivo.concat(separadorArchivo.concat("sinImagen.png")));
            inputStream = new FileInputStream(imagen);
            image = new Image(inputStream);
            visor = new ImageView(image);
            visor.setFitWidth(128);
            visor.setPreserveRatio(true);
            lbImagen.setGraphic(visor);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void validarCampos() {
        if (!txtNombre.getText().matches("[ /s a-zA-z]*")) {
            longitudCadena = txtNombre.getText().length();
            subString = txtNombre.getText().substring(0, longitudCadena - 1);
            txtNombre.setText(subString);
        }
        if (!txtApellido.getText().matches("[ /s a-zA-z]*")) {
            longitudCadena = txtApellido.getText().length();
            subString = txtApellido.getText().substring(0, longitudCadena - 1);
            txtApellido.setText(subString);
        }
        if (!txtCelular.getText().matches("[ 0-9 ]*")) {
            longitudCadena = txtCelular.getText().length();
            subString = txtCelular.getText().substring(0, longitudCadena - 1);
            txtCelular.setText(subString);
        }
        if (!txtTelefonoFijo.getText().matches("[ 0-9 ]*")) {
            longitudCadena = txtTelefonoFijo.getText().length();
            subString = txtTelefonoFijo.getText().substring(0, longitudCadena - 1);
            txtTelefonoFijo.setText(subString);
        }

    }

    @FXML
    public void cargarItemsMenuBar() {
        Menu menuArchivo = new Menu("Archivo");
        MenuItem menuGuardar = new MenuItem("Guardar");
        MenuItem menuReporte = new MenuItem("Reporte");
        imagenItemGuardar = new File(rutaDondeSeCreaArchivo.concat(separadorArchivo.concat("guardar.png")));
        imagenItemReporte = new File(rutaDondeSeCreaArchivo.concat(separadorArchivo.concat("Reporte.png")));
        try {
            inputStream = new FileInputStream(imagenItemGuardar);
            image = new Image(inputStream);
            visor = new ImageView(image);
            menuGuardar.setGraphic(visor);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            inputStream = new FileInputStream(imagenItemReporte);
            image = new Image(inputStream);
            visor = new ImageView(image);
            menuReporte.setGraphic(visor);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        menuGuardar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if (txtNombre.getText().isEmpty()) {
                        Dialogs.showWarningDialog(primaryStage, "El campo nombre es necesario llenarlo", "Error", "Verificacion");
                    }
                    if (txtApellido.getText().isEmpty()) {
                        Dialogs.showWarningDialog(primaryStage, "El campo apellido es necesario llenarlo", "Error", "Verificacion");
                    }
                    if (txtCelular.getText().isEmpty()) {
                        Dialogs.showWarningDialog(primaryStage, "El campo celular es necesario llenarlo", "Error", "Verificacion");
                    }
                    if (txtCorreo.getText().isEmpty()) {
                        Dialogs.showWarningDialog(primaryStage, "El campo correo es necesario llenarlo", "Error", "Verificacion");
                    } else {
                        fichero = new FileWriter(file, true);
                        PrintWriter pw = new PrintWriter(fichero);
                        pw.print(txtNombre.getText());
                        pw.print(";");
                        pw.print(txtApellido.getText());
                        pw.print(";");
                        pw.print(txtCelular.getText());
                        pw.print(";");
                        pw.print(txtCorreo.getText());
                        pw.println();
                        txtNombre.setText("");
                        txtApellido.setText("");
                        txtCelular.setText("");
                        txtTelefonoFijo.setText("");
                        txtCorreo.setText("");
                    }
                } catch (Exception e) {
                    System.out.println("Error en el primer catch de guardar archivo: " + e);
                } finally {
                    try {
                        if (null != fichero) {
                            fichero.close();
                        }
                    } catch (Exception e2) {
                        System.out.println("Error en el segundo catch de guardar archivo: " + e2);
                    }
                }
            }
        });
        menuReporte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                reporte();
            }
        });
        menuArchivo.getItems().add(menuGuardar);
        menuArchivo.getItems().add(menuReporte);
        mbGuardar.getMenus().add(menuArchivo);

    }

    @FXML
    public void validarExistenciaArchivo() {
        if (file.exists() == false) {
            try {
                fichero = new FileWriter(file, true);
            } catch (IOException ex) {
                Logger.getLogger(ExposicionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private JRCsvDataSource getDataSource() throws URISyntaxException, JRException {
        String[] nombreColumnas = new String[]{"nombre", "apellido", "celular", "correoElectronico"};
        File f1 = new File(rutaDondeSeCreaArchivo.concat(separadorArchivo).concat("Agenda.txt"));
        String filePath = f1.getAbsolutePath().toString();
        dataSource = new JRCsvDataSource(filePath);
        dataSource.setFieldDelimiter(';');
        dataSource.setColumnNames(nombreColumnas);
        return dataSource;
    }

    /*Este metodo genera el repote, tanto una vista previa como un archivo 
     .pdf */
    @FXML
    public void reporte() {
        try {
            File f = new File(rutaDondeSeCreaArchivo.concat(separadorArchivo).concat("Agenda.jasper"));
            String rutaAbsoluta = f.getAbsolutePath().toString();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(rutaAbsoluta);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, getDataSource());
            /*esto nos permite visualizar nuestro reporte de forma mas ligera
             sin necesidad de buscar el archivo pdf*/
            JasperViewer ver = new JasperViewer(jasperPrint, false);
            ver.setTitle("Contactos");
            ver.setVisible(true);


            /*este nos imprime el archivo y no lo guarda en la carpeta del proyecto*/
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("Contactos Agenda.pdf"));
            exporter.exportReport();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Exposicion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            System.out.println("error generando el reporte " + ex);
            Logger.getLogger(Exposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mbGuardar.getMenus().clear();
        jTRegistros.getColumns().clear();
        cargarItemsMenuBar();
        validarExistenciaArchivo();
        imagenPrincipio();
        BotonGrupo();
        llenarComboBox();
        columnas();
        cargarRegistrosALaTabla();

    }
}
