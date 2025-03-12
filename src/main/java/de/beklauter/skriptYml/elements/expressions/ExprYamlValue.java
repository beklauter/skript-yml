package de.beklauter.skriptYml.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

public class ExprYamlValue extends SimpleExpression<Object> {

    static {
        Skript.registerExpression(ExprYamlValue.class, Object.class, ExpressionType.COMBINED,
                "yaml value %string% from [file] %string%");
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
    protected Object[] get(Event e) {
        String keyStr = key.getSingle(e);
        String pathStr = filePath.getSingle(e);

        if (keyStr == null || pathStr == null) return new Object[0];

        Object value = YamlManager.getValue(pathStr, keyStr);
        return value == null ? new Object[0] : new Object[] { value };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "yaml value " + key.toString(e, debug) + " from file " + filePath.toString(e, debug);
    }
}