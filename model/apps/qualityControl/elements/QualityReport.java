package com.model.apps.qualityControl.elements;


import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.databaseEnum.DatabaseNames;
import com.form.FormBuilder;
import com.form.FormObject;
import com.form.IFormCustomBehaviour;
import com.form.dateField.DateField;
import com.form.intField.IntField;
import com.form.stringField.StringField;
import com.model.apps.addressbook.elements.Contact;
import com.model.apps.qualityControl.QualityControlFactory;
import com.model.apps.qualityControl.QualityControlRoot;
import com.model.apps.qualityControl.ToLoad;
import com.model.sql.SQLHandler;
import com.model.user.UserInfo;
import com.observer.Listener;
import com.table3.CustomRowBehaviour;
import com.table3.DateHelper;
import com.table3.DateProperty;
import com.table3.ModelObject;
import com.table3.Table3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class QualityReport extends ModelObject{
	QualityControlRoot qualityControlRoot;
	String folderPath;
	IntegerProperty Id;
	DateProperty timeStamp;
	StringProperty productCode;
	StringProperty shipmentReference;
	StringProperty issueDescription;
	// problem serverty is a scale 1 - 10 of how bad the issue is.
	IntegerProperty problemServerity;
	// problem type is ENUMERATED so translation to chinese is easier
	StringProperty problemType;
	// need something like an array of images.
	
	
	public IntegerProperty getIdProperty() {return Id;}
	public DateProperty getTimeStampProperty(){return timeStamp;}
	public StringProperty getProductCodeProperty(){return productCode;}
	public StringProperty getShipmentReferenceProperty(){return shipmentReference;}
	public StringProperty getIssueDescriptionProperty(){return issueDescription;}
	public IntegerProperty getProblemServerityProperty(){return problemServerity;}
	public StringProperty getProblemTypeProperty(){return problemType;}
	
	@Override
	public int getId() {return Id.get();}
	public LocalDate getTimeStamp(){return timeStamp.getValue();}
	public String getProductCode(){return productCode.get();}
	public String getShipmentReference(){return shipmentReference.get();}
	public String getIssueDescription(){return issueDescription.get();}
	public int getProblemServerity(){return problemServerity.get();}
	public String getProblemType(){return problemType.get();}
	
	public void setId(int id) {this.Id.set(id);}
	public void setTimeStamp(LocalDate timeStamp){this.timeStamp.setValue(timeStamp);}
	public void setProductCode(String productCode){this.productCode.set(productCode);}
	public void setShipmentReference(String shipmentReference){this.shipmentReference.set(shipmentReference);}
	public void setIssueDescription(String issueDescription){this.issueDescription.set(issueDescription);}
	public void setProblemServerity(int problemServerity){this.problemServerity.set( problemServerity);}
	public void setProblemType(String problemType){this.problemType.set(problemType);}
	
	public QualityReport() {
		
		this.Id = new SimpleIntegerProperty();
		this.timeStamp = new DateProperty();
		this.productCode = new SimpleStringProperty();
		this.shipmentReference = new SimpleStringProperty();
		this.issueDescription = new SimpleStringProperty();
		this.problemServerity = new SimpleIntegerProperty();
		this.problemType = new SimpleStringProperty();
		addCustomRowBehaviour();
		objectName = "Quality Report";
		
	}

	public QualityReport(int id, LocalDate timestamp, String productCode, String shipmentReference, String issueDescription, String problemType, int problemServerity){
		
		this.Id = new SimpleIntegerProperty();
		this.timeStamp = new DateProperty();
		this.productCode = new SimpleStringProperty();
		this.shipmentReference = new SimpleStringProperty();
		this.issueDescription = new SimpleStringProperty();
		this.problemServerity = new SimpleIntegerProperty();
		this.problemType = new SimpleStringProperty();
		
		this.Id.set(id);
		this.timeStamp.setValue(timestamp);
		this.productCode.set(productCode);
		this.shipmentReference.set(shipmentReference);
		this.issueDescription.set(issueDescription);
		this.problemServerity.set(problemServerity);
		this.problemType.set(problemType);
		
		addCustomRowBehaviour();
		objectName = "Quality Report";
	}
	
	private boolean doesFolderExist(File[] listOfFiles, String lookingFor) {
	
		for (File f : listOfFiles) {
			String fileName = f.getName();
			if(lookingFor.equals(fileName)) {
				//then we have a match
				System.out.println("WE HAVE A LINK FOR " +  lookingFor);
				return true;
			}
		}
		return false;
	}
	
	public String getFolderPath() {
		// TODO Auto-generated method stub
		return folderPath;
	}
	private void addCustomRowBehaviour() {
		customRowBehaviour.add(new CustomRowBehaviour() {

			@Override
			public GridPane onChange(int numberOfCols, GridPane root) {
				
				ScrollPane scrollPane = new ScrollPane();
				HBox hBox = new HBox(10);
				String qualityControlImageRoot = "J:/01_Jamb Limited/09_Quality Control/ChrisQualityControl/";
				
				GridPane.setConstraints(scrollPane, numberOfCols, 0);
				root.getChildren().add(scrollPane);
				
				//populate the scrollpane with a hbox full of pictures
				File folder = new File(qualityControlImageRoot);
				//can you find your number in the folder...
				String lookingFor = QualityControlFactory.idFormatter(getId());
				boolean folderExist = doesFolderExist(folder.listFiles(), lookingFor);
				if(!folderExist) {
					new File(qualityControlImageRoot+"/"+lookingFor).mkdirs();
				}
				folder = new File(qualityControlImageRoot+"/"+lookingFor+"/");
				
				System.out.println(folder.getPath());
				folderPath = folder.getPath();
				File[] files= folder.listFiles();
				
				int i =0;
				for(File f : files) {
					System.out.println(f.getName());
					String localUrl = null;
					try {
						localUrl = folder.toURI().toURL().toString()+"/"+files[i].getName();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(localUrl);
				
				//	Image image = new Image(localUrl);
					ImageView imageView = new ImageView("/com/view/icons/loading.gif");
					ToLoad toLoad = new ToLoad(imageView, localUrl);
					qualityControlRoot.addToLoad(toLoad);
					
					//loadThis(imageView, localUrl);
					imageView.setSmooth(true);
					imageView.setPreserveRatio(true);
					imageView.setFitHeight(150);
					imageView.setFitWidth(150);
					hBox.getChildren().add(imageView);
					i++;
				}
				scrollPane.setContent(hBox);
				ColumnConstraints column = new ColumnConstraints(150);
				root.getColumnConstraints().add(column);
				
				return root;
			}});
	}
	
	void loadThis(ImageView imageView, String url) {
		imageView.setImage(new Image(url));
	}
	
	
	
	@Override
	public ArrayList<Property> display1() {
		ArrayList<Property> properties = new ArrayList<Property>();
		columnNames = new ArrayList<String>();
		properties.add(Id);
		properties.add(timeStamp);
		properties.add(productCode);
		properties.add( shipmentReference);
		properties.add( issueDescription);
		// problem serverty is a scale 1 - 10 of how bad the issue is.
		properties.add( problemServerity);
		// problem type is ENUMERATED so translation to chinese is easier
		properties.add( problemType);
		columnNames.add("ID");
		columnNames.add("Timestamp");
		columnNames.add("Product Code");
		columnNames.add("Shipment Reference");
		columnNames.add("Issue Description");
		columnNames.add("Problem Serverity");
		columnNames.add("Problem Type");
		boolean [] req = {false, true, true, true, true, true, true};
		requiredFields = req;
		return properties;
	}

	@Override
	public String getPropertyResult(int number) {
		if (number == 0) {
			return getId() + "";
		}
		if (number == 1) {
			return getTimeStamp() + "";
		}
		if (number == 2) {
			return getProductCode() + "";
		}
		if (number == 3) {
			return getShipmentReference() + "";
		}
		if (number == 4) {
			return getIssueDescription() + "";
		}
		if (number == 5) {
			return getProblemServerity() + "";
		}
		if (number == 6) {
			return getProblemType() + "";
		}
		else {
			return null;
		}
	}

	@Override
	public String getFilterString() {
		return (getProductCode() + " " + getShipmentReference()+ " " +  getProblemServerity()+ " " + getProblemType());
	}

	@Override
	public ModelObject extractFromForm(ArrayList<String> results, FormBuilder formBuilder) {
		QualityReport qualityReport = new QualityReport ();
		qualityReport.setTimeStamp(LocalDate.parse(results.get(1), DateHelper.getDateFormatter()));
		qualityReport.setProductCode(results.get(2));
		qualityReport.setShipmentReference(results.get(3));
		qualityReport.setIssueDescription(results.get(4));
		qualityReport.setProblemServerity(Integer.parseInt(results.get(5)));
		qualityReport.setProblemType(results.get(6));
		ModelObject result = (ModelObject) qualityReport;
		return result;
	}

	@Override
	public void mapModelObjectToForm(ModelObject modelObject, ArrayList<FormObject> formObjects) {
		QualityReport qualityReport = (QualityReport) modelObject;
		IntField id = (IntField) formObjects.get(0);
		id.setInput(qualityReport.getId());
		id.setDisabled();
		
		DateField timeStamp = (DateField) formObjects.get(1);
		timeStamp.setInput(qualityReport.getTimeStamp());
		
		StringField productCode = (StringField) formObjects.get(2);
		productCode.setInput(qualityReport.getProductCode());
		
		StringField shipmentReference = (StringField) formObjects.get(3);
		shipmentReference.setInput(qualityReport.getShipmentReference());
		
		StringField issueDescription = (StringField) formObjects.get(4);
		issueDescription.setInput(qualityReport.getIssueDescription());
		
		IntField problemServerity = (IntField ) formObjects.get(5);
		problemServerity.setInput(qualityReport.getProblemServerity());
		
		StringField problemType = (StringField) formObjects.get(6);
		problemType.setInput(qualityReport.getProblemType());
		
	
	}

	@Override
	public void modelObjectToSQL(ModelObject oldModelObject, Table3 table, FormBuilder formBuilder) {
		ModelObject submittedObject = formBuilder.getSubmittedObject();
		QualityReport qualityReport = (QualityReport) oldModelObject;
		QualityReport newQualityReport = (QualityReport) submittedObject;
		String timestamp = newQualityReport.getTimeStamp().format(DateHelper.getDateFormatter());
		String productCode = newQualityReport.getProductCode();
		String shipmentReference = newQualityReport.getShipmentReference();
		String issueDescription = newQualityReport.getIssueDescription();
		int problemServerity = newQualityReport.getProblemServerity();
		String problemType = newQualityReport.getProblemType();
		String[] columnNames = {"ProductCode","ShipmentReference","IssueDescription",	"Type",		"Serverity", "Timestamp"};
		String[] values = {		productCode, 	shipmentReference, issueDescription,  problemType,	problemServerity+"", timestamp};
		if(oldModelObject == null) {
			SQLHandler.insertInTable(DatabaseNames.JambProductDatabse,"QualityReports", columnNames, values);
		}else {
			int id = qualityReport.getId();
			SQLHandler.editTable(DatabaseNames.JambProductDatabse,"QualityReports", columnNames, values, "idQualityReports=" + id);
		}
		formBuilder.closeForm();
		table.refreshData();
	}

	@Override
	public IFormCustomBehaviour getCustomFormBuilderBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void Delete(int idFromSelected, Table3 table) {
		System.out.println("Delete " +objectName);
		SQLHandler.deleteRowInTable(DatabaseNames.JambProductDatabse,"QualityReports", "idQualityReports = " + idFromSelected);
		table.refreshData();

	}
	public void setQualityControlRoot(QualityControlRoot qualityControlRoot) {
		this.qualityControlRoot=qualityControlRoot;
		
	}
	

}
