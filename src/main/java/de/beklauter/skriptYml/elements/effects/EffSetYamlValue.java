package de.beklauter.skriptYml.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

public class EffSetYamlValue extends Effect {

    static {
        Skript.registerEffect(EffSetYamlValue.class,
            "set yaml value %string% in [file] %string% to %object%");
    }

    private Expression<String> key;
    private Expression<String> filePath;
    private Expression<Object> value;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.key = (Expression<String>) exprs[0];
        this.filePath = (Expression<String>) exprs[1];
        this.value = (Expression<Object>) exprs[2];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String keyStr = key.getSingle(e);
        String pathStr = filePath.getSingle(e);
        Object valueObj = value.getSingle(e);

        if (keyStr == null || pathStr == null) return;

        YamlManager.setValue(pathStr, keyStr, valueObj);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "set yaml value " + key.toString(e, debug) + " in file " + filePath.toString(e, debug) +
                " to " + value.toString(e, debug);
    }
}