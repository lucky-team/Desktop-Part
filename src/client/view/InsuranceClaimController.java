package client.view;

import client.desktop.ClientMainApp;
import model.Claim;
import model.Policy;
import test.TestCase;
import tool.HttpTool;
import tool.PolicyTool;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//client-��������ҳ
public class InsuranceClaimController 
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
	private TableView<Claim> tb_claims;
	@FXML
	private TableColumn<Claim, String> claimPlanColumn;
	@FXML
	private TableColumn<Claim, String> claimPeriodColumn;
	@FXML
	private TableColumn<Claim, String> claimDateColumn;
	@FXML
	private TableColumn<Claim, String> claimProgressColumn;
	@FXML
	private Button btn_home;
	@FXML
	private Button btn_insuranceService;
	@FXML
	private Button btn_myInsurance;
	@FXML
	private Button btn_insuranceClaim;
	
    private ObservableList<Policy> policyData;
    private ObservableList<Claim> claimData;
	private ClientMainApp mainApp;
	
	@FXML
	private void initialize() 
	{
		//------------------------------------------------------------Data Update---------------------------------------------
		policyData = PolicyTool.initPolicyList(PolicyTool.getPolicyList(HttpTool.getArray(TestCase.policy_array)));
		
		//------------------------------------------------------------GUI Update---------------------------------------------
		tb_policys.setItems(policyData);
		insurancePlanColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlanLevelProperty()));
		guaranteePeriodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDurationProperty()));
		startingEndingDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartEndTimeProperty("Ireland")));		//test
		insuranceClaimColumn.setCellFactory((col) -> 
    	{
            TableCell<Policy, String> cell = new TableCell<Policy, String>() 
            {

                @Override
                public void updateItem(String item, boolean empty) 
                {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) 
                    {
                        Button acceptBtn = new Button("Claim");
                        this.setGraphic(acceptBtn);
                        acceptBtn.setOnMouseClicked((me) -> 
                        {
                        	Policy policy = this.getTableView().getItems().get(this.getIndex());
                        	mainApp.showClaimApplicationView(policy.getId());
                        });
                    }
                }

            };
            return cell;
        });
		tb_claims.setItems(claimData);
		claimPlanColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccLocation()));	//test
		claimPeriodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccLocation()));	//test
		claimDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccLocation()));	//test
		claimProgressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
	}
	
	@FXML
	public void backToInsuranceClaim()
	{
		mainApp.showInsuranceClaimView();
	}
	    
	public void setMainApp(ClientMainApp mainApp) 
    {
        this.mainApp = mainApp;

    }
}
