package terminal_heat_sink.asusrogphone2rgb;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class QuicktileSecondLed extends TileService {

    private String use_second_led_on_shared_preference_key = "terminal_heat_sink.asusrogphone2rgb.use_second_led";


    @Override
    public void onClick() {
        super.onClick();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
        boolean enabled = prefs.getBoolean(use_second_led_on_shared_preference_key,false);

        Tile tile = getQsTile();

        if(enabled){
            SystemWriter.turn_on_second_led(false,getApplicationContext());
            tile.setState(Tile.STATE_INACTIVE);
            tile.setLabel("Turn ON Second LED");
            enabled = false;
        }else{
            SystemWriter.turn_on_second_led(true,getApplicationContext());
            tile.setState(Tile.STATE_ACTIVE);
            tile.setLabel("Turn Off Second LED");
            enabled = true;
        }
        prefs.edit().putBoolean(use_second_led_on_shared_preference_key, enabled).apply();
        tile.updateTile();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        update_tile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();

        update_tile();

    }

    private void update_tile(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "terminal_heat_sink.asusrogphone2rgb", Context.MODE_PRIVATE);
        boolean enabled = prefs.getBoolean(use_second_led_on_shared_preference_key,false);

        Tile tile = getQsTile();

        if(enabled){
            tile.setState(Tile.STATE_ACTIVE);
            tile.setLabel("Turn Off Second LED");
        }else{
            tile.setState(Tile.STATE_INACTIVE);
            tile.setLabel("Turn On Second LED");
        }
        tile.updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }
}
