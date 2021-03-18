package etf.ip.projektni.admin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.catalina.manager.util.SessionUtils;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import etf.ip.projektni.admin.dao.AdminDAO;
import etf.ip.projektni.admin.dao.UserDAO;
import etf.ip.projektni.admin.dto.Admin;

@ManagedBean (name ="adminBean")
@SessionScoped
public class AdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Admin admin;
	private String username;
	private String password;
	private int ukupnoRegistrovanih;
	private int trenutnoPrijavljeni;
	private BarChartModel chart;

	

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public AdminBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void refreshRegistrovani() {
		//System.out.println("refresh reg " + new Date().toString());
		ukupnoRegistrovanih = UserDAO.getRegisteredUsersCount();
		trenutnoPrijavljeni = UserDAO.getTrenutnoPrijavljeni();
	}
	
	public String login() {
		admin = AdminDAO.login(username, password);
		if (admin != null) {
			HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			sesija.setAttribute("admin_user", admin);
			ukupnoRegistrovanih = UserDAO.getRegisteredUsersCount();
			trenutnoPrijavljeni = UserDAO.getTrenutnoPrijavljeni();
			createChart();
			return "main.xhtml?faces-redirect=true";
		}
		else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("loginForma:login", new FacesMessage("Pogresni parametri"));
			return "";
		}
	}
	
	public String logout() {
		HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		sesija.removeAttribute("admin_user");
		return "index.xhtml?faces-redirect=true";
	}
	
	public void refreshChart() {
		//System.out.println("asdsss" + new Date());
		createChart();
	}
	
	public void createChart() {
		chart = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Ukupno prijavljenih korisnika danas");
        barDataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet.setBorderColor("rgb(255, 99, 132)");
        barDataSet.setBorderWidth(1);        
        
        
        List<Number> values = UserDAO.logovaniKorisniciUDanu();
        barDataSet.setData(values);
 
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();        
        labels.add("00");
        labels.add("01");
        labels.add("02");
        labels.add("03");
        labels.add("04");
        labels.add("05");
        labels.add("06");
        labels.add("07");
        labels.add("08");
        labels.add("09");
        labels.add("10");
        labels.add("11");
        labels.add("12");
        labels.add("13");
        labels.add("14");
        labels.add("15");
        labels.add("16");
        labels.add("17");
        labels.add("18");
        labels.add("19");
        labels.add("20");
        labels.add("21");
        labels.add("22");
        labels.add("23");
        
        data.setLabels(labels);
        chart.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Bar Chart");
//        options.setTitle(title);
         
        chart.setOptions(options);
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUkupnoRegistrovanih() {
		return ukupnoRegistrovanih;
	}

	public void setUkupnoRegistrovanih(int ukupnoRegistrovanih) {
		this.ukupnoRegistrovanih = ukupnoRegistrovanih;
	}

	public int getTrenutnoPrijavljeni() {
		return trenutnoPrijavljeni;
	}

	public void setTrenutnoPrijavljeni(int trenutnoPrijavljeni) {
		this.trenutnoPrijavljeni = trenutnoPrijavljeni;
	}
	public BarChartModel getChart() {
		return chart;
	}

	public void setChart(BarChartModel chart) {
		this.chart = chart;
	}

}
