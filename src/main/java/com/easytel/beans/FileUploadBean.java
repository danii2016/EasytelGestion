/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.beans;

/**
 *
 * @author andri
 */
import com.easytel.model.LigneTableau;
import com.easytel.util.SessionUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadBean {
    private boolean lu = false;
    private String fichier = "";

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
 
    public void fileUploadListener(FileUploadEvent event) throws IOException {
        String path = FacesContext.getCurrentInstance().getExternalContext()
            .getRealPath("/");
        SimpleDateFormat fmt = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        String name = fmt.format(new java.util.Date())
                + event.getFile().getFileName().substring(
                      event.getFile().getFileName().lastIndexOf('.'));
        File file = new File(path + "../../src/main/webapp/resources/uploads/" + name);

        if(!file.exists()) {
            file.createNewFile();
        }
        OutputStream out;
        try (InputStream is = event.getFile().getInputstream()) {
            out = new FileOutputStream(file);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0)
                out.write(buf, 0, len);
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("fichier", name);
        }
        out.close();
        FacesMessage message;
        if(file.canRead()) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Succès", event.getFile().getFileName() + " a été uploadé.");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur", event.getFile().getFileName() + " n'a pas pu être uploadé.");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
    
    public String readFile() {
        HttpSession session = SessionUtils.getSession();
        String name = (String) session.getAttribute("fichier");
        this.fichier = name;
        this.lu = true;
        return "tableauExcel";
    }
    
    /**
     *
     * @return
     */
    public List<LigneTableau> getLignes(){
        String path = FacesContext.getCurrentInstance().getExternalContext()
            .getRealPath("/");
        HttpSession session = SessionUtils.getSession();
        String name = (String) session.getAttribute("fichier");
        File file = new File(path + "../../src/main/webapp/resources/uploads/" + name);
        ArrayList lignejava = convertjava(file);
        List lignes = new ArrayList();
        if(lignejava.size() > 0 ) {
            for (int i = 2; i < lignejava.size(); i++) {
                ArrayList ligne = (ArrayList) lignejava.get(i);
                LigneTableau lt = new LigneTableau();
                int j = 1;
                for (Object ligne1 : ligne) {
                    String lignestring = "";
                    if (ligne1 instanceof String) {
                      lignestring = (String) ligne1;
                    } else if(ligne1 instanceof Double) {
                        lignestring = ""+ ligne1;
                    }
                    switch(j) {
                        case 1 : lt.setVal1(lignestring); break;
                        case 2 : lt.setVal2(lignestring); break;
                        case 3 : lt.setVal3(lignestring); break;
                        case 4 : lt.setVal4(lignestring); break;
                        case 5 : lt.setVal5(lignestring); break;
                        case 6 : lt.setVal6(lignestring); break;
                        case 7 : lt.setVal7(lignestring); break;
                        case 8 : lt.setVal8(lignestring); break;
                        case 9 : lt.setVal9(lignestring); break;
                        case 10 : lt.setVal10(lignestring); break;
                        case 11 : lt.setVal11(lignestring); break;
                        case 12 : lt.setVal12(lignestring); break;
                        case 13 : lt.setVal13(lignestring); break;
                        case 14 : lt.setVal14(lignestring); break;
                        case 15 : lt.setVal15(lignestring);break;
                    }
                    j++;
                }
                
                System.out.print("j = "+j+"\n"+lt.toString());
                lignes.add(lt);
            }
        }
        return lignes;
    }
    
    public ArrayList convertjava(File f) {
        ArrayList datafichier = new ArrayList();
        try {
            FileInputStream file = new FileInputStream(f);
            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            // Get first sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);
            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                ArrayList ligne = new ArrayList();
                Row row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        ligne.add(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        ligne.add(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        ligne.add(cell.getStringCellValue());
                        break;
                    default: ligne.add(""); break;
                    }
                }
                datafichier.add(ligne);
            }
            file.close();
            FileOutputStream out = new FileOutputStream(new File(""));
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            System.out.print(e);
        } catch (IOException e) {
            System.out.println("error");
        }
        return datafichier;
    }
}
