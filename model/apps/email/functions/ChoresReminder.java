package com.model.apps.email.functions;

import java.time.LocalDate;

import com.model.apps.chores.ChoresFactory;
import com.model.apps.chores.elements.RepeatableTask;
import com.model.apps.email.Emailer;
import com.model.apps.email.elements.Email;
import com.model.user.User;
import com.model.user.UserInfo;
import com.table3.DateHelper;
import com.table3.ModelObject;

import javafx.collections.ObservableList;

public class ChoresReminder {

	//register this to the clock.
	// at a certain time everyday
	//gather this data and send this email..
	//this data = chore data
	
	public ChoresReminder(){}
	public static void main(String[] args) {
		new ChoresReminder().remind();
	}
	public void remind() {
		//fetch the data
			//what data needs to be fetched
			ObservableList<ModelObject> chores = ChoresFactory.getChores(new User("chris","Chris", "Donaldson",1));
			String[] lines= new String[chores.size()];
			int i=0;
			for(ModelObject mo:chores) {
				RepeatableTask chore = (RepeatableTask)mo;
				String title = chore.getName();
				String health = chore.getHealth();
				String line = String.format("<b>%s</b> is at %s.",title, health);
				lines[i] = line;
				i++;
			}
			
		//compose the email
		LocalDate now =	LocalDate.now();
		String subject = "Tasks reminder - " + now.format(DateHelper.getDateFormatter()); 
		String to = "chrisddonaldson@hotmail.com";
		
		String body = String.join("</p><p>", lines);
		body =  "<p>"+body+"</p>";
		Email email = new Email(to, subject, body);
		Emailer.send(email);
		//send the email
		
	}
	
	public static void Main(String[] args) {
		new ChoresReminder().remind();
	}
}
