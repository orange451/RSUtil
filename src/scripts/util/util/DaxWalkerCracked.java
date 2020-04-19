package scripts.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import scripts.autocow.TradeServer;
import scripts.dax_api.api_lib.json.Json;
import scripts.dax_api.api_lib.json.JsonValue;
import scripts.dax_api.api_lib.json.ParseException;
import scripts.dax_api.api_lib.models.PathResult;
import scripts.dax_api.api_lib.models.PathStatus;
import scripts.dax_api.api_lib.models.PlayerDetails;
import scripts.dax_api.api_lib.models.Point3D;
import scripts.dax_api.api_lib.models.ServerResponse;
import scripts.dax_api.api_lib.utils.IOHelper;

public class DaxWalkerCracked {

	private static Socket serverConnection;
    private static Map<String, String> cache = new HashMap<>();
    
    public static PathResult getPath(Positionable end) {
    	return getPath(Player.getPosition(), end);
    }
    
    public static PathResult getPath(Positionable start, Positionable end) {
    	return getPath(start, end, PlayerDetails.generate());
    }
    
    public static PathResult getPath(Positionable start, Positionable end, PlayerDetails playerDetails) {
        com.google.gson.JsonObject pathRequest = new com.google.gson.JsonObject();
        pathRequest.add("start", Point3D.fromPositionable(start).toJson());
        pathRequest.add("end", Point3D.fromPositionable(end).toJson());

        if (playerDetails != null) {
            pathRequest.add("player", playerDetails.toJson());
        }

        try {
            return parseResult(post(pathRequest, "https://osrs-map.herokuapp.com/getPath"));
        } catch (IOException e) {
            log("Is server down? Spam dax.");
            return new PathResult(PathStatus.NO_RESPONSE_FROM_SERVER);
        }

    }
    
    private static PathResult parseResult(ServerResponse serverResponse) {
        if (!serverResponse.isSuccess()) {
        	
        	log("[Error] Could not generate path");
        	log(serverResponse.getContents());

            switch (serverResponse.getCode()) {
                case 429:
                    return new PathResult(PathStatus.RATE_LIMIT_EXCEEDED);
                case 400:
                case 401:
                case 404:
                    return new PathResult(PathStatus.INVALID_CREDENTIALS);
            }
            
            return new PathResult(PathStatus.UNKNOWN);
        } else {
	        PathResult pathResult;
	        JsonElement jsonObject;
	        try {
	            jsonObject = new JsonParser().parse(serverResponse.getContents());
	        } catch (ParseException e) {
	            pathResult = new PathResult(PathStatus.UNKNOWN);
	            log("Error: " + pathResult.getPathStatus());
	            return pathResult;
	        }
	
	        pathResult = PathResult.fromJson(jsonObject);
	        log("Response: " + pathResult.getPathStatus() + " Cost: " + pathResult.getCost());
	        return pathResult;
        }
    }
    
    private static ServerResponse post(com.google.gson.JsonObject body, String endpoint) throws IOException {
    	// Read cache first
    	log("Generating path: " + body.toString());
        if (cache.containsKey(body.toString())) {
            return new ServerResponse(true, HttpURLConnection.HTTP_OK, cache.get(body.toString()));
        }
        
        // Make sure we're connected to crack server
		if ( serverConnection == null || serverConnection.isClosed() ) {
			serverConnection = new Socket();
			try {
				serverConnection.connect(new InetSocketAddress("localhost", DaxWalkerCrackedServer.PORT), 1000);
				serverConnection.setKeepAlive(true);
				//serverConnection.setTcpNoDelay(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        // Make request to server
        IOHelper.writeOutputStreamWithoutClosing(serverConnection.getOutputStream(), body.toString() + "\n");
        
        // Read result
        String contents = IOHelper.readInputStream(serverConnection.getInputStream());
		
		// Send back
        cache.put(body.toString(), contents);
        return new ServerResponse(true, HttpURLConnection.HTTP_OK, contents);
    }
    
    private static void log(Object object) {
    	try {
    		General.println(object);
    	} catch(Exception e) {
    		System.out.println(object);
    	}
    }
    
    public static void main(String[] args) {
    	PathResult result = getPath(new RSTile(2959, 3204, 0), new RSTile(3300, 3211, 0), null);
    	System.out.println(result);
    	System.out.println(result.getPath());
    }
}
