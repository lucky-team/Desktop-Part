package client.view;

import model.Policy;
import net.sf.json.JSONArray;
import tool.UserTool;
import tool.Controller;
import tool.HttpTool;
import tool.PolicyTool;
import desktop.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Pair;

//client-��������ҳ
public class InsuranceController extends Controller 
{
	@FXML
	private TableView<Policy> tb_policys;
	@FXML
    private TableColumn<Policy, String> insurancePlanColumn;
	@FXML
    private TableColumn<Policy, String> guaranteePeriodColumn;
	@FXML
    private TableColumn<Policy, String> startingEndingDateColumn;
	@FXML
    private TableColumn<Policy, String> insuranceClaimColumn;
	@FXML
	private Button btn_home;
	@FXML
	private Button btn_logout;
	@FXML
	private Button btn_myClaim;
	@FXML
	private Button btn_myInsurance;
	@FXML
	private Button btn_aboutUs;
	@FXML
	private Button bt_language;
	@FXML
	private Label lb_clientService;
	@FXML
	private Label lb_purchacedInsurance;
	
    private ObservableList<Policy> policyData;
	private MainApp mainApp;
	
	@FXML
	public void changeLanguage()
	{
		UserTool.i18n.changeLanguage();
		setLanguageBtn();
	}
	
	public void setLanguageBtn()
	{
		bt_language.setText(UserTool.i18n.get("language"));
		setText();
	}
	
	public void setText()
	{
		lb_clientService.setText(UserTool.i18n.get("clientservice"));
		btn_home.setText(UserTool.i18n.get("home"));
		btn_myInsurance.setText(UserTool.i18n.get("myinsurance"));
		btn_myClaim.setText(UserTool.i18n.get("myclaim"));
		btn_aboutUs.setText(UserTool.i18n.get("aboutus"));
		btn_logout.setText(UserTool.i18n.get("logout"));
		
		lb_purchacedInsurance.setText(UserTool.i18n.get("purchacedInsurance"));
		insurancePlanColumn.setText(UserTool.i18n.get("insurancePlanCol"));
		guaranteePeriodColumn.setText(UserTool.i18n.get("guaranteePeriodCol"));
		startingEndingDateColumn.setText(UserTool.i18n.get("startingEndingDateCol"));
		insuranceClaimColumn.setText(UserTool.i18n.get("insuranceClaimCol"));
	}

	private void initData() 
	{
		//------------------------------------------------------------Data Update---------------------------------------------
		Pair<Integer, String> reply = HttpTool.getArray("/insurances", UserTool.user.getToken());
		if(reply.getKey().equals(200))
		{
			JSONArray jarray = JSONArray.fromObject(reply.getValue());
			policyData = PolicyTool.initPolicyList(PolicyTool.getPolicyList(jarray));
		}		
		//------------------------------------------------------------GUI Update---------------------------------------------
		tb_policys.setItems(policyData);
		insurancePlanColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlanLevelProperty()));
		guaranteePeriodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDurationProperty()));
		startingEndingDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartEndTimeProperty("Ireland")));		//test
		insuranceClaimColumn.setCellFactory(col -> 
    	{
            TableCell<Policy, String> cell = new TableCell<Policy, String>() 
            {
                @Override
                public void updateItem(String item, boolean empty) 
                {
                    super.updateItem(item, empty);
                    if (!empty) 
                    {
                        Policy policy = this.getTableView().getItems().get(this.getIndex());
                        if(!policy.getClaiming())
                        {
                            Button acceptBtn = new Button(UserTool.i18n.get("btn_Claim"));
                            this.setGraphic(acceptBtn);
                            acceptBtn.setOnMouseClicked((me) -> mainApp.showClaimApplicationView(policy.getId()));
                        }
                    }
                }

            };
            return cell;
        });
		tb_policys.setRowFactory(tableview -> 
		{
			TableRow<Policy> row = new TableRow<Policy>();
			row.setOnMouseClicked(event -> 
			{
				Policy policy = row.getItem();
				mainApp.showInsuranceInformationView(policy);
			});
			return row ;
		});
	}
	
	@FXML
	public void toHome()
	{
		mainApp.showHomeView();
	}
	
	@FXML
	public void toClaimView()
	{
		mainApp.showClaimView();
	}
	
	@FXML
	public void toProfile()
	{
		mainApp.showClientProfileView();
	}
	
	@FXML
	public void backToInsurance()
	{
		mainApp.showInsuranceView();
	}
	
	@FXML
	public void toAboutUs()
	{
		mainApp.showAboutUsView();
	}

	@FXML
	public void logout()
	{
		mainApp.showLogInView();
	}
	    
	public void setMainApp(MainApp mainApp) 
    {
        this.mainApp = mainApp;
        setLanguageBtn();
        initData();
    }
}
