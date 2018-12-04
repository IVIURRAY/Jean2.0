package com.model.apps.fireplaceArray;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


//  w  w  w.j av a2s  . c  o m
public class FireplaceArray extends Application {
  @Override
  public void start(final Stage stage) {
    stage.setWidth(400);
    stage.setHeight(500);
    Scene scene = new Scene(new Group());

    WebView webView = new WebView();
    WebEngine engine = webView.getEngine();
    //Change the path according to yours.
    String url = getClass().getResource("/com/pdfjs/web/viewer.html").toExternalForm();
    //We add our stylesheet.
    engine.setUserStyleSheetLocation(getClass().getResource("/com/pdfjs/web/viewer.css").toExternalForm());
    engine.setJavaScriptEnabled(true);
    engine.load(url);
    

    scene.setRoot(webView);
    URL myUrl = null;
 // readFileToByteArray() comes from commons-io library
   
	try {
	
		myUrl = new URL("file:///J:/01_Jamb Limited/01_Products/01_Fireplaces/01_Marble Chimneypieces/Abercorn_CP119/Abercorn_Jamb_CP119.pdf");
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    InputStream stream = null;
    try {
           stream = myUrl.openStream();
           //I use IOUtils from org.​apache.​commons.​io
           byte[] data = FileUtils.readFileToByteArray(new File("J:/01_Jamb Limited/01_Products/01_Fireplaces/01_Marble Chimneypieces/Abercorn_CP119/Abercorn_Jamb_CP119.pdf"));
           //Base64 from java.util
           String base64 = Base64.getEncoder().encodeToString(data);
           //This must be ran on FXApplicationThread
           webView.getEngine().executeScript("openFileFromBase64('" + base64 + "')");
           /***
           Platform.runLater(new Runnable() {
			@Override
			public void run() {
				webView.getEngine().executeScript("openFileFromBase64('" + base64 + "')");
			} 
           });
             ***/                
           } catch (Exception ex) {
                ex.printStackTrace();
           }finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                    	ex.printStackTrace();
                    }
                }}
                
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}