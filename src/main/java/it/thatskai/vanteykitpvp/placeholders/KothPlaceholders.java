package it.thatskai.vanteykitpvp.placeholders;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.listeners.KothListener;
import it.thatskai.vanteykitpvp.manager.KothManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KothPlaceholders extends PlaceholderExpansion {
    private KothManager koth = new KothManager();
    @Override
    public @NotNull String getIdentifier() {
        return "koth";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ThatsKai";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(params.equals("state")){
            if(koth.getState()){
                return String.valueOf(KothListener.secondsRemaining);
            }else{
                return VanteyKitPvP.getInstance().getConfig().getString("non-attivo-placeholder");
            }
        }
        if(params.equals("player")){
            if(!koth.getState()){
                return "Non attivo2";
            }
            if(KothListener.currentPlayer == null){
                return "Nessuno";
            }else{
                return KothListener.currentPlayer.getName();
            }
        }
        return null;
    }
}
