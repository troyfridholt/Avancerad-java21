package weather;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label localization;
    @FXML
    private Label temp;
    @FXML
    private Label datum;
    @FXML
    private Label feel;
    @FXML
    private Label sunRise;
    @FXML
    private Label sunSet;
    @FXML
    private Label windSpeed;
    @FXML
    private Label cloudsPercentage;
    @FXML
    private Label humidity;
    @FXML
    private Label pressure;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private ImageView weatherInfoImage;
    @FXML
    private ImageView accept;
    @FXML
    private ImageView cancel;
    @FXML
    private ImageView weatherCity;
    @FXML
    private ImageView refresh;
    @FXML
    private Label weatherInfo;
    @FXML
    private TextField cityInput;

    private Weather weather;
    private String newCity = "";
    private String todayDatum;
    private String actualTime;
    private double x;
    private double y;
    private boolean close = false;
    private boolean isNight = false;
    private boolean isHide = false;

    /**
     * Create a new weather object and call a method to attach a url address.
     */
    public Controller() {
        weather = new Weather(newCity);
        weather.connectToUrlAndGetStrings();
    }

    /**
     * Closes the application after clicked.
     * @param event Mouse click by user.
     */
    @FXML
    private void close(MouseEvent event) {
        close = true;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    /**
     * Minimize the application after clicked.
     * @param event Mouse click by user.
     */
    @FXML
    private void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Drag the application window after clicking.
     * @param event Mouse click by user.
     */
    @FXML
    private void dragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    /**
     * Press the application window after clicking.
     * @param event Mouse click by user.
     */
    @FXML
    private void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    /**
     * Rounds the entered number to two decimal places.
     * @param value Entered value which will be rounded.
     * @return Rounded value.
     */
    private double roundToTwoDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * Converts unix time to real date and time.
     * @param time Entered unix time.
     * @return Converted date and time.
     */
    private String unixTimeToRealTime(int time) {
        Date miliseconds = new java.util.Date(time * 1000L);  
        SimpleDateFormat date = new java.text.SimpleDateFormat("HH:mm"); 
     
        date.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
        String formattedDate = date.format(miliseconds);
        return formattedDate;
    }

    /**
     * Date to set.
     */
    private void setDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.now();
        this.todayDatum = localDate.format(dateFormat);
    }

    /**
     * Time to set.
     */
    private void setTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        this.actualTime = dateFormat.format(date);
        this.actualTime = this.todayDatum + " / " + actualTime;
        datum.setText(actualTime);
    }

    /**
     * City to set.
     */
    private void setCity() {
        localization.setText(weather.getCity());
    }

    /**
     * Temperature to set.
     */
    private void setTemperature() {
        double celsiusTemperature = weather.kelvinToCelsius(weather.getTemperature());
        celsiusTemperature = roundToTwoDecimal(celsiusTemperature);
        String value = String.valueOf(celsiusTemperature);
        temp.setText(value + "°C");
    }

    /**
     * Feel temperature to set.
     */
    private void setFeelsLike() {
        double celsiusTemperature = weather.kelvinToCelsius(weather.getFeelsLike());
        celsiusTemperature = roundToTwoDecimal(celsiusTemperature);
        String value = String.valueOf(celsiusTemperature);
        feel.setText("Feels like: " + value + "°C");

    }

    /**
     * Sunrise to set.
     */
    private void setSunrise() {
        String dateTime = unixTimeToRealTime(weather.getSunrise());
        sunRise.setText(dateTime);
    }

    /**
     * Sunset to set.
     */
    private void setSunset() {
        String dateTime = unixTimeToRealTime(weather.getSunset());
        sunSet.setText(dateTime);
    }

    /**
     * Wind speed to set.
     */
    private void setWindSpeed() {
        windSpeed.setText(String.valueOf(roundToTwoDecimal(weather.getWindSpeed() * 3.60) + "km/h"));
    }

    /**
     * Clouds percentage to set.
     */
    private void setCloudsPercentage() {
        cloudsPercentage.setText(String.valueOf(weather.getCloudsPercentage() + "%"));
    }

    /**
     * Humidity to set.
     */
    private void setHumidity() {
        humidity.setText(String.valueOf(weather.getHumidity()) + "%");
    }

    /**
     * Pressure to set.
     */
    private void setPressure() {
        pressure.setText(String.valueOf(weather.getPressure() + " hPa"));
    }

    /**
     *
     * @return Boolean whether is application closed.
     */
    public boolean isClose() {
        return close;
    }

    /**
     * Weather description to set.
     */
    public void setWeatherInfo() {
        weatherInfo.setText(String.valueOf(weather.getWeatherDescription()));
    }

    /**
     * Image of current weather to set.
     */
    public void setWeatherInfoImage() {
        Image image;
        if (weather.getWeather().equals("Clouds") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/cloudy.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Clouds") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/night-cloudy.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Clear") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/sunny.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Clear") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/night-clear-sky.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Mist") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/mist.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Mist") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/foggy-night.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Fog") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/fog.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Fog") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/foggy-night.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Rain") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/rain.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Rain") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/night-rain.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Snow") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/snowing.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Snow") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/night-snowing.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Storm") && isNight == false) {
            image = new Image(getClass().getResourceAsStream("/images/storm.png"));
            weatherInfoImage.setImage(image);
        } else if (weather.getWeather().equals("Storm") && isNight == true) {
            image = new Image(getClass().getResourceAsStream("/images/night-storm.png"));
            weatherInfoImage.setImage(image);
        }
    }

    /**
     * Background image to set.
     */
    public void setBackgroundImage() {
        Date date = new Date();
        int hour = 0;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 18) {
            Image image = new Image(getClass().getResourceAsStream("/images/backgroundDayResized.png"));
            backgroundImage.setImage(image);
        } else {
            Image image = new Image(getClass().getResourceAsStream("/images/backgroundNightResized.png"));
            backgroundImage.setImage(image);
            isNight = true;
        }
    }

   
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackgroundImage();
        setCity();
        hideElements();
        setTemperature();
        setDate();
        setTime();
        setFeelsLike();
        setSunrise();
        setSunset();
        setWindSpeed();
        setCloudsPercentage();
        setHumidity();
        setPressure();
        setWeatherInfo();
        setWeatherInfoImage();
        setCityLocalization();
        refresh();
    }

    /**
     * Called to actualize current time.
     */
    public void actualizeTime() {
        setTime();
    }

    /**
     * Called to actualize all necessary values.
     */
    public void actualize() {
        weather = new Weather(newCity);
        weather.connectToUrlAndGetStrings();
        setBackgroundImage();
        setTemperature();
        setDate();
        setTime();
        setFeelsLike();
        setSunrise();
        setWeatherInfo();
        setWeatherInfoImage();
        setSunset();
        setWindSpeed();
        setCloudsPercentage();
        setHumidity();
        setPressure();
        setCity();
    }

    /**
     * Called after is added new city localization and set new city localization.
     */
    public void setCityLocalization() {
        weatherCity.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isHide = true) {
                    showElements();
                    cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            hideElements();
                        }
                    });
                    accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (cityInput.getText().equals("")) {
                                Alert a = new Alert(Alert.AlertType.INFORMATION);
                                a.setContentText("Input can not be empty");
                                a.show();
                            } else {
                                newCity = cityInput.getText();
                                hideElements();
                                actualize();
                                cityInput.setText("");
                            }
                        }
                    });
                } else {
                    hideElements();
                }
            }
        });
    }

    /**
     * Hides elements that should not be visible all time.
     */
    public void hideElements() {
        cityInput.setVisible(false);
        accept.setVisible(false);
        cancel.setVisible(false);
        isHide = true;
    }

    /**
     * Show elements that should not be visible all time.
     */
    public void showElements() {
        cityInput.setVisible(true);
        accept.setVisible(true);
        cancel.setVisible(true);
        isHide = false;
    }

    /**
     * Refresh button
     */
    public void refresh() {
        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actualize();
            }
        });
    }
}