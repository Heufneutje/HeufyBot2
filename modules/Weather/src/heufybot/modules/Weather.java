package heufybot.modules;

import heufybot.utils.FileUtils;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.List;

public class Weather extends Module
{
    private String locationsPath;

    public Weather(String server)
    {
        super(server);

        this.authType = AuthType.Anyone;
        this.apiVersion = 60;
        this.triggerTypes = new TriggerType[] { TriggerType.Message };
        this.trigger = "^" + this.commandPrefix + "(weather|forecast)($| .*)";

        this.locationsPath = "data/" + server + "/userlocations.txt";
    }

    @Override
    public void processEvent(String source, String message, String triggerUser, List<String> params)
    {
        if (params.size() == 1)
        {
            if (!this.readLocations().containsKey(triggerUser.toLowerCase()))
            {
                if (this.bot.getServer(this.server).getModuleInterface()
                        .isModuleLoaded("UserLocation"))
                {
                    this.bot.getServer(this.server).cmdPRIVMSG(
                            source,
                            "You are not registered. Use \"" + this.commandPrefix
                                    + "registerloc <location>\" to register your location.");
                }
                else
                {
                    this.bot.getServer(this.server)
                            .cmdPRIVMSG(
                                    source,
                                    "You are not registered. The module \"UserLocation\" is required for " +
                                            "registration, but is currently not loaded.");
                }
                return;
            }
            params.add(triggerUser);
        }

        params.remove(0);
        GeocodingInterface geo = new GeocodingInterface();

        // First try latitude and longitude. If these are not in fact lat/lon
        // this will fail before any network stuff is done
        try
        {
            float latitude = Float.parseFloat(params.get(0));
            float longitude = Float.parseFloat(params.get(1));
            try
            {
                Geolocation location = geo.getGeolocationForLatLng(latitude, longitude);
                String prefix = location.success ? "Location: " + location.locality : "City: "
                        + latitude + "," + longitude;

                if (message.toLowerCase().matches("^" + this.commandPrefix + "weather.*"))
                {
                    String weather = this.getWeatherFromGeolocation(location);
                    this.bot.getServer(this.server).cmdPRIVMSG(source,
                            String.format("%s | %s", prefix, weather));
                }
                else if (message.toLowerCase().matches("^" + this.commandPrefix + "forecast.*"))
                {
                    String forecast = this.getForecastFromGeolocation(location);
                    this.bot.getServer(this.server).cmdPRIVMSG(source,
                            String.format("%s | %s", prefix, forecast));
                }
                return;
            }
            catch (ParseException e)
            {
                this.bot.getServer(this.server).cmdPRIVMSG(source,
                        "I don't think that's even a location in this multiverse...");
                return;
            }
        }
        catch (NumberFormatException e)
        {
            // Nothing to see here, just not latitude/longitude, continuing.
        }
        catch (IndexOutOfBoundsException e)
        {
            // Either this is fuzzing or invalid input. Either way we don't
            // care, and should check the next two cases.
        }

        try
        {
            Geolocation location = null;
            if (this.readLocations().containsKey(params.get(0).toLowerCase()))
            {
                location = geo.getGeolocationForPlace(this.readLocations().get(
                        params.get(0).toLowerCase()));
            }
            else
            {
                location = geo.getGeolocationForPlace(message.substring(message.indexOf(' ') + 1));
            }

            if (location != null)
            {
                String loc = location.locality;
                if (loc == null)
                {
                    loc = "Unknown";
                }

                if (message.toLowerCase().matches("^" + this.commandPrefix + "weather.*"))
                {
                    String weather = this.getWeatherFromGeolocation(location);
                    if (weather == null)
                    {
                        weather = "Weather for this location could not be retrieved.";
                    }
                    this.bot.getServer(this.server).cmdPRIVMSG(source,
                            String.format("Location: %s | %s", loc, weather));
                }
                else if (message.toLowerCase().matches("^" + this.commandPrefix + "forecast.*"))
                {
                    String forecast = this.getForecastFromGeolocation(location);
                    if (forecast == null)
                    {
                        this.bot.getServer(this.server).cmdPRIVMSG(
                                source,
                                String.format("Location: %s | %s", location.locality,
                                        "Forecast for this location could not be retrieved."));
                        return;
                    }
                    this.bot.getServer(this.server).cmdPRIVMSG(source,
                            String.format("Location: %s", loc + " | " + forecast));
                }
                return;
            }
        }
        catch (ParseException e)
        {
            this.bot.getServer(this.server).cmdPRIVMSG(source,
                    "I don't think that's even a user in this multiverse...");
            return;
        }
    }

    private String getWeatherFromGeolocation(Geolocation location) throws ParseException
    {
        WeatherInterface weatherInterface = new WeatherInterface();
        String weather = weatherInterface.getWeather(location.latitude, location.longitude);
        if (weather == null)
        {
            return "Weather for this location could not be retrieved.";
        }
        return weather;
    }

    private String getForecastFromGeolocation(Geolocation location) throws ParseException
    {
        WeatherInterface weatherInterface = new WeatherInterface();
        String forecast = weatherInterface.getForecast(location.latitude, location.longitude);
        if (forecast == null)
        {
            return "Weather for this location could not be retrieved.";
        }
        return forecast;
    }

    private HashMap<String, String> readLocations()
    {
        String[] locationArray = FileUtils.readFile(this.locationsPath).split("\n");
        HashMap<String, String> userLocations = new HashMap<String, String>();
        if (locationArray[0].length() > 0)
        {
            for (String element : locationArray)
            {
                String[] location = element.split("=");
                userLocations.put(location[0], location[1]);
            }
        }
        return userLocations;
    }

    @Override
    public String getHelp(String message)
    {
        return "Commands: "
                + this.commandPrefix
                + "weather (<place>/<latitude longitude>/<ircuser>), "
                + this.commandPrefix
                + "forecast (<place>/<latitude longitude>/<ircuser>) | Makes the bot get the current weather " +
                "conditions at the location specified or at the location of the IRC user.";
    }

    @Override
    public void onLoad()
    {
        FileUtils.touchFile("data/worldweatheronlineapikey.txt");
        FileUtils.touchFile(this.locationsPath);

        this.readLocations();
    }

    @Override
    public void onUnload()
    {
    }
}
