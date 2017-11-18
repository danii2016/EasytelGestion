package com.easytel.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andri
 */
import com.easytel.dao.SuiviJourDAO;
import com.easytel.model.Agent;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
 
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;
 
@ManagedBean
public class ChartView implements Serializable {
 
    private double montantmax = 0;
    private boolean loadBar = true;
    private String erreurload = "";
    private Date dateJour;
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;

    public boolean isLoadBar() {
        return loadBar;
    }

    public void setLoadBar(boolean loadBar) {
        this.loadBar = loadBar;
    }

    public String getErreurload() {
        return erreurload;
    }

    public void setErreurload(String erreurload) {
        this.erreurload = erreurload;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }
 
    @PostConstruct
    public void init() {
        dateJour = new Date();
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
    
    public void chargeBar() {
        createBarModel();
    }
 
    private BarChartModel initBarModel() {
        List<Agent> agents = SuiviJourDAO.getAgentWithStats(dateJour);
        if(agents.isEmpty() || agents == null) {
            erreurload = "Les agents n'ont pas été récupéré. Ajoutez des agents ou vérifiez votre connexion";
            loadBar = false;
            return null;
        } else {
            BarChartModel model = new BarChartModel();
            ChartSeries caisse = new ChartSeries();
            ChartSeries uv = new ChartSeries();
            ChartSeries commission = new ChartSeries();
            caisse.setLabel("Caisse");
            uv.setLabel("U.V");
            commission.setLabel("Commission");
            for(Agent ag : agents) {
                if(montantmax < ag.getAg_caisse()) {
                    montantmax = ag.getAg_caisse();
                }
                if(montantmax < ag.getAg_uv()) {
                    montantmax = ag.getAg_uv();
                }
                if(montantmax < ag.getAg_commission()) {
                    montantmax = ag.getAg_commission();
                }
                caisse.set(ag.getAg_nom(), ag.getAg_caisse());
                uv.set(ag.getAg_nom(), ag.getAg_uv());
                commission.set(ag.getAg_nom(), ag.getAg_commission());
            }

            model.addSeries(caisse);
            model.addSeries(uv);
            model.addSeries(commission);
            loadBar = true;
            return model;
        }
    }
     
    private void createBarModels() {
        createBarModel();
    }
     
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Statistiques du jour");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Agents");
         
        if(montantmax == 0) montantmax = 1000000;
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Valeur (Ariary)");
        yAxis.setMin(0);
        yAxis.setMax(montantmax);
    }
 
}
