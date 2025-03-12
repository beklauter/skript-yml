package de.beklauter.skriptYml.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

public class EffDeleteYamlValue extends Effect {

    static {
        Skript.registerEffect(EffDeleteYamlValue.class,
            "delete yaml value %string% (from|in) [file] %string%");
    }

    private Expression<String> key;
    private Expression<String> filePath;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.key = (Expression<String>) exprs[0];
        this.filePath = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String keyStr = key.getSingle(e);
        String pathStr = filePath.getSingle(e);

        if (keyStr == null || pathStr == null) return;

        YamlManager.removeValue(pathStr, keyStr);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "delete yaml value " + key.toString(e, debug) + " from file " + filePath.toString(e, debug);
    }
}