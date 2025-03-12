package de.beklauter.skriptYml.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.beklauter.skriptYml.utils.YamlManager;
import org.bukkit.event.Event;

import java.util.Set;

public class ExprYamlKeys extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprYamlKeys.class, String.class, ExpressionType.COMBINED,
                "yaml keys %string% from [file] %string%",
                "yaml keys from [file] %string%");
    }

    private Expression<String> section;
    private Expression<String> filePath;
    private boolean withSection;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        withSection = matchedPattern == 0;

        if (withSection) {
            this.section = (Expression<String>) exprs[0];
            this.filePath = (Expression<String>) exprs[1];
        } else {
            this.section = null;
            this.filePath = (Expression<String>) exprs[0];
        }
        return true;
    }

    @Override
    protected String[] get(Event e) {
        String pathStr = filePath.getSingle(e);
        if (pathStr == null) return new String[0];

        String sectionStr = withSection ? section.getSingle(e) : null;
        Set<String> keys = YamlManager.getKeys(pathStr, sectionStr);

        if (keys == null || keys.isEmpty()) {
            return new String[0];
        }

        return keys.toArray(new String[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        if (withSection) {
            return "yaml keys " + section.toString(e, debug) + " from file " + filePath.toString(e, debug);
        } else {
            return "yaml keys from file " + filePath.toString(e, debug);
        }
    }
}