package de.beklauter.skriptYml.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

public class EffSaveYamlFile extends Effect {

    static {
        Skript.registerEffect(EffSaveYamlFile.class,
            "save yaml file %string%");
    }

    private Expression<String> filePath;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.filePath = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        String pathStr = filePath.getSingle(e);
        if (pathStr == null) return;

        YamlManager.saveFile(pathStr);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "save yaml file " + filePath.toString(e, debug);
    }
}