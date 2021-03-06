package application;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable {
	
	@FXML
	private Slider seekslider;
	@FXML
	private Slider slider;
	@FXML
	private MediaView mv;
	private Media me;
	
	
	private MediaPlayer mp;
	private String filepath;
	
	@FXML
	private void handleButtonAction(ActionEvent event)
	{
		FileChooser filechooser=new FileChooser();
		FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("Select a file", "*.mp3");
		filechooser.getExtensionFilters().add(filter);
		File file=filechooser.showOpenDialog(null);
		filepath=file.toURI().toString();
		if(filepath!=null){
		Media me=new Media(filepath);
		mp=new MediaPlayer(me );
		mv.setMediaPlayer(mp);
		
		slider.setValue(mp.getVolume()*100);
		slider.valueProperty().addListener(new InvalidationListener(){

			@Override
			public void invalidated(javafx.beans.Observable observable) {
				mp.setVolume(slider.getValue()/100);				
			}
			
		});
		
		mp.currentTimeProperty().addListener(new ChangeListener<Duration>(){
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue){
				seekslider.setValue(newValue.toSeconds());
			}
			
			
		});
		
		seekslider.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			@Override
			public void handle(MouseEvent event){
				mp.seek(Duration.seconds(seekslider.getValue()));
				
			}
			
		});
		
		mp.play();
		
		}

		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	
		
		
		


	}
	
	
	public void play(ActionEvent event)
	{
		mp.play();
		mp.setRate(1);

	}
	public void pause(ActionEvent event)
	{
		mp.pause();
	}
	public void stop(ActionEvent event)
	{
		mp.stop();

	}
	public void fast(ActionEvent event)
	{
		mp.setRate(2);
	}
	public void slow(ActionEvent event)
	{
		mp.setRate(0.5);
	}
	public void reload(ActionEvent event)
	{
		mp.seek(mp.getStartTime());
		mp.play();
	}
	
		
	

}
