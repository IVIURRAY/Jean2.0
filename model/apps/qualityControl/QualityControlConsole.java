package com.model.apps.qualityControl;



import java.io.IOException;

import com.model.apps.qualityControl.elements.QualityReport;
import com.table3.DateHelper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class QualityControlConsole {
	GridPane root = new GridPane();
	RowConstraints row1 = new RowConstraints();
	RowConstraints row2 = new RowConstraints();
	RowConstraints row3 = new RowConstraints();
	int consoleHeight;
	int consoleWidth;
	QualityReport qualityReport;
	ScrollPane scrollPane = new ScrollPane();
	VBox vBox = new VBox(5);
	Label idLabel = new Label();
	Label timeStamp = new Label();
	Label productCode = new Label();
	Label shipmentReference = new Label();
	Label issueDescription = new Label();
	Label problemServerity = new Label();
	Label problemType = new Label();
	Button exportButton = new Button("Export");
	Button openInExplorerButton = new Button("Open In Explorer");

	public QualityControlConsole() {
		vBox.getChildren().add(idLabel);
		vBox.getChildren().add(timeStamp);
		vBox.getChildren().add(productCode);
		vBox.getChildren().add(shipmentReference);
		vBox.getChildren().add(issueDescription);
		issueDescription.setWrapText(true);
		vBox.getChildren().add(problemServerity);
		vBox.getChildren().add(problemType);
		vBox.getChildren().add(exportButton);
		vBox.getChildren().add(openInExplorerButton);
		
		exportButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Export selection");
			}
			
		});
		openInExplorerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Opening explorer");
				String path = qualityReport.getFolderPath();
				System.out.println(path);
				try {
					Runtime.getRuntime().exec("explorer.exe /select," + path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		root.add(vBox, 0, 1);
		root.add(scrollPane, 0, 0);
		root.add(getProblemTypeLookUp(), 0, 2);
		root.getRowConstraints().add(row1);
		root.getRowConstraints().add(row2);
		root.getRowConstraints().add(row3);
	}

	public void setQualityReport(QualityReport qualityReport) {
		this.qualityReport = qualityReport;
		updateLabels();
	}

	public GridPane getDisplay() {
		if (root == null) {
			root.toString();}
		root.setPadding(new Insets(5,5,5,5));;
		return root;
	};

	public void updateLabels() {
		idLabel.setText("ID: " + qualityReport.getId() + "");
		timeStamp.setText("Time Stamp: " + qualityReport.getTimeStamp().format(DateHelper.getDateFormatter()));
		productCode.setText("Product Code: " + qualityReport.getProductCode());
		shipmentReference.setText("Shipment Reference: " + qualityReport.getShipmentReference());
		issueDescription.setText("Issue Description: " + qualityReport.getIssueDescription() + "");
		problemServerity.setText("Problem Serverity: " + qualityReport.getProblemServerity() + "");
		problemType.setText("Problem Type: " + qualityReport.getProblemType());
	}

	public void setHeight(int height) {
		root.setMaxHeight(height);
		root.setMinHeight(height);
		root.setPrefHeight(height);
		row1.setMaxHeight(height * 0.5);
		row1.setMinHeight(height * 0.5);
		row1.setPrefHeight(height * 0.5);
		row2.setMaxHeight(height * 0.3);
		row2.setMinHeight(height * 0.3);
		row2.setPrefHeight(height * 0.3);
		row3.setMaxHeight(height * 0.2);
		row3.setMinHeight(height * 0.2);
		row3.setPrefHeight(height * 0.2);
	}

	public void setWidth(int width) {
		root.setMaxWidth(width);
		root.setMinWidth(width);
		root.setPrefWidth(width);
		scrollPane.setMaxWidth(width - 20);
		scrollPane.setMinWidth(width - 20);
		scrollPane.setPrefWidth(width - 20);
	}

	HBox getProblemTypeLookUp() {

		String[] problemTypes = { "1: Casting - 计算", "2: Fabrication - 组建", "3: Finish - 完成", "4: Glass - 玻璃",
				"5: Packaging - 包装", "6: Square - 正方形", "7: Mechanical - 机械", "8: Screws - 螺丝", "9: Welding - 焊接 " };
		int i = 0;
		VBox vBox1 = new VBox(5);
		VBox vBox2 = new VBox(5);
		Label header = new Label ("Problem Types");
		vBox1.getChildren().add(header);
		for (String s : problemTypes) {
			Label label = new Label(s);
			if(i<4) {vBox1.getChildren().add(label);}
			else {vBox2.getChildren().add(label);}
			i++;
		}
		
		HBox hBox = new HBox(10);
		hBox.getChildren().add(vBox1);
		hBox.getChildren().add(vBox2);
		return hBox;
	}

	public void setScrollPane(Node content) {
		HBox contentFromGrid = (HBox)content;
		System.out.println("Number of images: " + contentFromGrid.getChildren().size());
		HBox newContent = new HBox(10);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		for(int i = 0 ; i<contentFromGrid.getChildren().size(); i++) {
			ImageView iv = new ImageView();
			ImageView gridImageView = (ImageView)contentFromGrid.getChildren().get(i);
			Image img = gridImageView.getImage();
			iv.setImage(img);
			iv.setFitHeight(scrollPane.getHeight());
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			//iv.setFitWidth(scrollPane.getWidth());
			newContent.getChildren().add(iv);
		}
		scrollPane.setContent(newContent);
		
	}

}
