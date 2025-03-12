package de.beklauter.skriptYml.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

public class ExprYamlFileExists extends SimpleExpression<Boolean> {

    static {
        Skript.registerExpression(ExprYamlFileExists.class, Boolean.class, ExpressionType.COMBINED,
                "yaml file %string% exists");
    }

    private Expression<String> filePath;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.filePath = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected Boolean[] get(Event e) {
        String pathStr = filePath.getSingle(e);
        if (pathStr == null) return new Boolean[0];

        return new Boolean[] { YamlManager.fileExists(pathStr) };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "yaml file " + filePath.toString(e, debug) + " exists";
    }
}