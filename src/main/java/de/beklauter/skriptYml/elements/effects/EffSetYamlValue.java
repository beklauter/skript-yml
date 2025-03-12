package de.beklauter.skriptYml.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.UnparsedLiteral;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.registrations.Converters;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EffSetYamlValue extends Effect {

    static {
        Skript.registerEffect(EffSetYamlValue.class,
            "set yaml value %string% in [file] %string% to %string/number/boolean/object%");
    }

    private Expression<String> key;
    private Expression<String> filePath;
    private Expression<?> value;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.key = (Expression<String>) exprs[0];
        this.filePath = (Expression<String>) exprs[1];
        this.value = exprs[2];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String keyStr = key.getSingle(e);
        String pathStr = filePath.getSingle(e);

        if (keyStr == null || pathStr == null) return;

        try {
            Object valueToStore;

            if (value.isSingle()) {
                valueToStore = value.getSingle(e);
            } else {
                Object[] values = value.getArray(e);
                List<Object> list = new ArrayList<>();

                if (values != null) {
                    for (Object obj : values) {
                        list.add(obj);
                    }
                    valueToStore = list;
                } else {
                    valueToStore = null;
                }
            }

            YamlManager.setValue(pathStr, keyStr, valueToStore);
        } catch (Exception ex) {
            Skript.error("Error setting YAML value: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "set yaml value " + key.toString(e, debug) + " in file " + filePath.toString(e, debug) +
                " to " + value.toString(e, debug);
    }
}