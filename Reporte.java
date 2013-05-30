/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import exposicion.Exposicion;
import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Reporte extends Thread {

    JRCsvDataSource agenda;
    String separador = System.getProperty("file.separator");

    private JRCsvDataSource getDataSource() throws URISyntaxException, JRException {
        String[] nombreColumnas = new String[]{"Nombre", "Apellido", "Celular", "Correo Electronico"};
        File f1 = new File(System.getProperty("user.dir").concat(separador).concat("Agenda.txt"));
        String filePath = f1.getAbsolutePath().toString();
        agenda = new JRCsvDataSource(filePath);
        agenda.setFieldDelimiter(';');
        agenda.setColumnNames(nombreColumnas);
        return agenda;
    }

    public void reporte() {
        try {
            File f = new File(System.getProperty("user.dir").concat(separador).concat("agendaElectronica.jasper"));
            String rutaAbsoluta = f.getAbsolutePath().toString();
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(rutaAbsoluta);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, getDataSource());

            /*esto nos permite visualizar nuestro reporte de forma mas ligera
             sin necesidad de buscar el archivo pdf*/
            JasperViewer ver = new JasperViewer(jasperPrint);
            ver.setTitle("Contactos");
            ver.setVisible(true);

            /*este nos imprime el archivo y no lo guarda en la carpeta del proyecto*/
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("Contactos.pdf"));
            exporter.exportReport();

        } catch (URISyntaxException ex) {
            Logger.getLogger(Exposicion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            System.out.println("error generando el reporte " + ex);
            Logger.getLogger(Exposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        reporte();
    }
}
